package ioiobagiety.controller;

import ioiobagiety.response.UploadFileResponse;
import ioiobagiety.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private SaveContentsController saveContentsController;

    @CrossOrigin(maxAge = 3600)
    @PostMapping("/files")
    public UploadFileResponse uploadFile (@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                            .path("/files/")
                                                            .path(fileName)
                                                            .toUriString();

        saveContentsController.saveFileContents(file);

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/files/multiple")
    public List<UploadFileResponse> uploadMultipleFiles (@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                     .map(this::uploadFile)
                     .collect(Collectors.toList());
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile (@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(contentType))
                             .header(HttpHeaders.CONTENT_DISPOSITION,
                                     "attachment; filename=\"" + resource.getFilename() + "\"")
                             .body(resource);
    }
}
