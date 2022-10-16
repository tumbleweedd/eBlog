package com.example.eblog.service;

import com.example.eblog.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PhotoService {
    void save(MultipartFile file) throws IOException;
    Optional<Photo> getFile(Long id);
    List<Photo> getAllFiles();
}
