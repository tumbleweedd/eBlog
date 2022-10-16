package com.example.eblog.controller;

import com.example.eblog.dto.PhotoDTO;
import com.example.eblog.model.Photo;
import com.example.eblog.service.PhotoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("photo") MultipartFile photo) {
        try {
            photoService.save(photo);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", photo.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", photo.getOriginalFilename()));
        }
    }

    @GetMapping
    public List<PhotoDTO> list() {
        return photoService.getAllFiles()
                .stream()
                .map(fileEntity -> mapToFileResponse(fileEntity))
                .collect(Collectors.toList());
    }

    private PhotoDTO mapToFileResponse(Photo fileEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .toUriString();
        PhotoDTO fileResponse = new PhotoDTO();
        fileResponse.setTitle(fileEntity.getTitle());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Optional<Photo> fileEntityOptional = photoService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        Photo fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getTitle() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getUrl());
    }


}
