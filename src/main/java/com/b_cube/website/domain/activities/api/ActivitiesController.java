package com.b_cube.website.domain.activities.api;

import com.b_cube.website.domain.activities.service.ActivitiesService;
import com.b_cube.website.domain.activities.dto.ActivitiesDTO;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivitiesController {

    private final ActivitiesService activitiesService;

    @GetMapping
    public ResponseEntity<List<ActivitiesDTO>> getActivities() {
        List<ActivitiesDTO> activities = activitiesService.getActivities();
        // 주석주석
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addActivities(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("imagePath") MultipartFile imagePath,
            @RequestParam("pdfPath") MultipartFile pdfPath
    ) {
        BaseResponse baseResponse = activitiesService.addActivities(title, description, imagePath, pdfPath);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteActivities(@PathVariable Long id) {
        BaseResponse baseResponse = activitiesService.deleteActivities(id);
        return ResponseEntity.ok(baseResponse);
    }
}
