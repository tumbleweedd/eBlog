package com.example.eblog.service.impl;

import antlr.StringUtils;
import com.example.eblog.dto.PhotoDTO;
import com.example.eblog.model.Photo;
import com.example.eblog.repository.PhotoRepository;
import com.example.eblog.service.PhotoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        Photo photo = Photo.builder()
                .title(org.springframework.util.StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
                .contentType(file.getContentType())
                .url(file.getBytes())
                .size(file.getSize())
                .build();

        photoRepository.save(photo);
    }

    @Override
    public Optional<Photo> getFile(Long id) {
        return photoRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> getAllFiles() {
        return new ResponseEntity<>(photoRepository.findAll()
                .stream()
                .map(f -> mapToFileResponse(f))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    private PhotoDTO mapToFileResponse(Photo fileEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/photos/")
                .path(String.valueOf(fileEntity.getId()))
                .toUriString();
        PhotoDTO fileResponse = new PhotoDTO();
        fileResponse.setTitle(fileEntity.getTitle());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }
}
