package com.b_cube.website.domain.photo.api;

import com.b_cube.website.domain.photo.dto.PhotoDTO;
import com.b_cube.website.domain.photo.service.PhotoService;
import com.b_cube.website.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "PhotoController", description = "활동 사진 컨트롤러")
@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @Operation(summary = "활동 사진 목록 조회")
    @GetMapping
    public ResponseEntity<List<PhotoDTO>> getPhoto() {
        List<PhotoDTO> photos = photoService.getPhoto();
        return ResponseEntity.ok(photos);
    }

    @Operation(summary = "활동 사진 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping
    public ResponseEntity<BaseResponse> addPhoto(
            @Parameter(description = "활동 사진 설명(문자열)")
            @RequestParam("description") String description,
            @Parameter(description = "연도-월-일(문자열)")
            @RequestParam(value = "date", required = false) LocalDate date,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath

    ) throws IOException {
        BaseResponse baseResponse = photoService.addPhoto(description, date, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "활동 사진 목록 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deletePhoto(
            @Parameter(description = "활동 사진 id 값")
            @PathVariable Long id
    ) {
        BaseResponse baseResponse = photoService.deletePhoto(id);
        return ResponseEntity.ok(baseResponse);
    }
}
