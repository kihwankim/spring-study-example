package com.example.fileuploadtest.controller;

import com.example.fileuploadtest.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final FileService fileService;

    @PostMapping("/v1/file")
    public String saveFile(HttpServletRequest request) throws ServletException, IOException {
        String itemName = request.getParameter("name");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            // file 저장 service 호출
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                fileService.saveFileV1(part);
            }
        }

        return "";
    }

    @PostMapping("/v2/file")
    public String saveFileV2(@RequestParam String name,
                             @RequestParam MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            fileService.saveFileV2(file);
        }
        return "";
    }
}
