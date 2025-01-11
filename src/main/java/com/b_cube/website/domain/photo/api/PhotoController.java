package com.b_cube.website.domain.photo.api;

import com.b_cube.website.domain.photo.dto.PhotoDTO;
import com.b_cube.website.domain.photo.service.PhotoService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping
    public ResponseEntity<List<PhotoDTO>> getPhoto() {
        List<PhotoDTO> photos = photoService.getPhoto();
        return ResponseEntity.ok(photos);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addPhoto(
            @RequestParam("description") String description,
            @RequestParam("imagePath") MultipartFile imagePath

    ) {
        BaseResponse baseResponse = photoService.addPhoto(description, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deletePhoto(@PathVariable Long id) {
        BaseResponse baseResponse = photoService.deletePhoto(id);
        return ResponseEntity.ok(baseResponse);
    }
}
