package com.example.fileuploadtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@RestController
public class FileUploadController {

    @PostMapping("/v1/file")
    public String saveFile(HttpServletRequest request) throws ServletException, IOException {
        log.info("request={}", request);
        String itemName = request.getParameter("name");

        log.info("itemName={}", itemName);
        Collection<Part> parts = request.getParts();

        log.info("parts={}", parts);

        return "";
    }
}
