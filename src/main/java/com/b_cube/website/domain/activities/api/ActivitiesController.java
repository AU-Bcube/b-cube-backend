package com.b_cube.website.domain.activities.api;

import com.b_cube.website.domain.activities.service.ActivitiesService;
import com.b_cube.website.domain.activities.dto.ActivitiesDTO;
import com.b_cube.website.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "ActivitiesController", description = "주요활동 컨트롤러")
@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivitiesController {

    private final ActivitiesService activitiesService;

    @Operation(summary = "주요활동 목록 조회")
    @GetMapping
    public ResponseEntity<List<ActivitiesDTO>> getActivities() {
        List<ActivitiesDTO> activities = activitiesService.getActivities();
        // 주석주석
        return ResponseEntity.ok(activities);
    }

    @Operation(summary = "주요활동 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping
    public ResponseEntity<BaseResponse> addActivities(
            @Parameter(description = "주요활동 제목(문자열)")
            @RequestParam("title") String title,
            @Parameter(description = "주요활동 설명(문자열)")
            @RequestParam("description") String description,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath,
            @Parameter(description = "MultipartFile pdf 삽입")
            @RequestParam("pdfPath") MultipartFile pdfPath
    ) {
        BaseResponse baseResponse = activitiesService.addActivities(title, description, imagePath, pdfPath);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "주요활동 목록 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteActivities(
            @Parameter(description = "주요활동 id 값")
            @PathVariable Long id
    ) {
        BaseResponse baseResponse = activitiesService.deleteActivities(id);
        return ResponseEntity.ok(baseResponse);
    }
}
