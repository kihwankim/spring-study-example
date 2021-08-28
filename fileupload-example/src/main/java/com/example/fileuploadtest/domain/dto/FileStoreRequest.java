package com.example.fileuploadtest.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileStoreRequest {
    private List<MultipartFile> files;
}
