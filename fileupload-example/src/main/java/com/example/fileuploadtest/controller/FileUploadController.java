package com.example.fileuploadtest.controller;

import com.example.fileuploadtest.domain.UploadFile;
import com.example.fileuploadtest.domain.dto.FileStoreRequest;
import com.example.fileuploadtest.file.FileStoreService;
import com.example.fileuploadtest.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final UploadFileService uploadFileService;
    private final FileStoreService fileStoreService;

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

    @GetMapping("/v2/file/{filename}")
    public ResponseEntity<Resource> downloadImg(@PathVariable("filename") String filename) throws MalformedURLException {
        return ResponseEntity.ok(new UrlResource("file:" + fileStoreService.getFullPath(filename)));
    }

    @GetMapping("/v2/file/attach/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws MalformedURLException {
        UrlResource urlResource = new UrlResource("file:" + fileStoreService.getFullPath(filename));
        String contentDisposition = "attachment; filename=\"" + filename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
