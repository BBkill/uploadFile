package com.example.uploadfile.controller;

import com.example.uploadfile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/upload-file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public int uploadFile(@RequestParam("file") MultipartFile file ) {
        try {
            return fileService.upLoadLargeFile(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
