package com.example.fileuploadtest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    public void saveFileV1(Part part) throws IOException {
        String fullPath = fileDir + part.getSubmittedFileName();
        log.info("filepath-save={}", fullPath);
        part.write(fullPath);
    }

    public void saveFileV2(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String fullpath = fileDir + filename;
        file.transferTo(new File(fullpath));
    }
}
