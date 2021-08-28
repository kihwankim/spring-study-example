package com.example.fileuploadtest.controller;

import com.example.fileuploadtest.domain.UploadFile;
import com.example.fileuploadtest.domain.dto.FileStoreRequest;
import com.example.fileuploadtest.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final UploadFileService uploadFileService;

    @PostMapping("/v1/file")
    public String saveFile(HttpServletRequest request) throws ServletException, IOException {
        String itemName = request.getParameter("name");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        uploadFileService.saveFileByParts(parts);

        return "";
    }

    @PostMapping("/v2/file")
    public List<UploadFile> saveFileV2(FileStoreRequest fileStoreRequest) throws IOException {
        List<MultipartFile> files = fileStoreRequest.getFiles();

        return uploadFileService.saveFilesByMultipartFile(files);
    }
}
