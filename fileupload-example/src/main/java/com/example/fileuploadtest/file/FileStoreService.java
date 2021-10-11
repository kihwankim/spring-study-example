package com.example.fileuploadtest.file;

import com.example.fileuploadtest.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStoreService {

    private static final String POINT = ".";

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> saveFilesByParts(Collection<Part> parts) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (Part part : parts) {
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                uploadFiles.add(saveFileByPart(part));
            }
        }

        return uploadFiles;
    }

    public UploadFile saveFileByPart(Part part) throws IOException {
        String originalFilename = part.getSubmittedFileName();
        String fullPath = createStoreFilename(originalFilename);
        log.info("filepath-save={}", fullPath);
        part.write(fullPath);

        return new UploadFile(originalFilename, fullPath);
    }

    public List<UploadFile> saveFilesByMultipartFiles(List<MultipartFile> files) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                uploadFiles.add(saveFileByMultipartFile(file));
            }
        }

        return uploadFiles;
    }

    public UploadFile saveFileByMultipartFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();

        String saveFileName = createStoreFilename(originalFilename);
        String fullpath = fileDir + saveFileName;
        file.transferTo(new File(fullpath));

        return new UploadFile(originalFilename, fullpath);
    }

    private String createStoreFilename(String originalFilename) {
        String uuidStr = UUID.randomUUID().toString();
        String ext = POINT + extractExt(originalFilename);
        return uuidStr + ext;
    }

    private String extractExt(String originalFileName) {
        if (originalFileName == null) {
            throw new IllegalComponentStateException();
        }
        int positionOfLastExt = originalFileName.lastIndexOf(POINT);
        return originalFileName.substring(positionOfLastExt + 1);
    }
}
