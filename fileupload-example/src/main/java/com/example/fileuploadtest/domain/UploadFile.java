package com.example.fileuploadtest.domain;

import lombok.Data;

@Data
public class UploadFile {
    private Long id;

    private String originalFilename;

    private String storeFileName;

    public UploadFile(String originalFilename, String storeFileName) {
        this.originalFilename = originalFilename;
        this.storeFileName = storeFileName;
    }
}
