package com.example.uploadfile.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public interface FileService {

    int upLoadLargeFile(MultipartFile file) throws IOException;
}
