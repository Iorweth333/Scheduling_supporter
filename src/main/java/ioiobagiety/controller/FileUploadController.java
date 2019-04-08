package ioiobagiety.controller;

import ioiobagiety.model.classes.Lesson;
import ioiobagiety.response.UploadFileResponse;
import ioiobagiety.service.FileStorageService;
import ioiobagiety.service.LessonService;
import ioiobagiety.util.XLSXParser;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LessonService lessonService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile (@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                            .path("/downloadFile/")
                                                            .path(fileName)
                                                            .toUriString();

        try {
            saveXLSXContents(file);
        } catch (Exception ex) {
            logger.info("Could not save xlsx. " + ex.getMessage());
        }
        return new UploadFileResponse(fileName, fileDownloadUri,
                                      file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles (@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                     .map(this::uploadFile)
                     .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
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


    private void saveXLSXContents (MultipartFile file) throws MultipartException {
        try {
            XSSFWorkbook offices = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = offices.getSheetAt(0);

            XLSXParser parser = new XLSXParser();

            ArrayList<Lesson> lessons = parser.parseSheet(worksheet);

            lessons.forEach(lessonService::create);

        } catch (Exception e) {
            throw new MultipartException("Constraints Violated");
        }
    }
}