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
            return new ResponseEntity<>(
                    String.format("Изображение успешно загружено: %s", photo.getOriginalFilename()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    String.format("Не удалось загрузить изображение: %s!", photo.getOriginalFilename()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> photoList() {
        return photoService.getAllFiles();
    }


    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Optional<Photo> photoOptional = photoService.getFile(id);

        if (photoOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Photo photo = photoOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getTitle() + "\"")
                .contentType(MediaType.valueOf(photo.getContentType()))
                .body(photo.getUrl());
    }


}
