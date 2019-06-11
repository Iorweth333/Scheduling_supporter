package ioiobagiety.controller;

import ioiobagiety.exception.XLSParseException;
import ioiobagiety.response.UploadFileResponse;
import ioiobagiety.service.FileStorageService;
import ioiobagiety.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/schedule")
public class XLSFileImportController {

    private static final Logger logger = LoggerFactory.getLogger(XLSFileImportController.class);

    @Autowired
    private LessonService lessonService;

    @Autowired
    private FileStorageService fileStorageService;

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/schedule/file/")
                .path(fileName)
                .toUriString();

        this.XLSFileImport(file);

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    public void XLSFileImport(MultipartFile file) {
        try {
            XLSFileImportProvider parser = new XLSFileImportProvider(file);
            parser.getLessons().forEach(lessonService::createLesson);
        } catch (XLSParseException e) {
            logger.info(e.getMessage());
        }
    }
}
