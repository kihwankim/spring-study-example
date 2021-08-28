package com.example.fileuploadtest.service;

import com.example.fileuploadtest.FileRepository;
import com.example.fileuploadtest.domain.UploadFile;
import com.example.fileuploadtest.file.FileStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final FileStoreService fileStoreService;
    private final FileRepository fileRepository;

    public void saveFileByParts(Collection<Part> parts) throws IOException {
        List<UploadFile> uploadFiles = fileStoreService.saveFilesByParts(parts);
        uploadFiles.forEach(fileRepository::save);
    }

    public List<UploadFile> saveFilesByMultipartFile(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> uploadFiles = fileStoreService.saveFilesByMultipartFiles(multipartFiles);
        uploadFiles.forEach(fileRepository::save);

        return uploadFiles;
    }
}
