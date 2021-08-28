package com.example.fileuploadtest;

import com.example.fileuploadtest.domain.UploadFile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileRepository {
    private final Map<Long, UploadFile> fileMap = new ConcurrentHashMap<>();
    private long sequence = 0L;

    public Long save(UploadFile uploadFile) {
        uploadFile.setId(sequence);
        sequence++;
        fileMap.put(uploadFile.getId(), uploadFile);

        return uploadFile.getId();
    }

    public UploadFile findById(Long id) {
        return fileMap.get(id);
    }
}
