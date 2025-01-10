package com.b_cube.website.domain.study.api;

import com.b_cube.website.domain.study.dto.StudyDTO;
import com.b_cube.website.domain.study.service.StudyService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping
    public ResponseEntity<List<StudyDTO>> getStudy() {
        List<StudyDTO> studies = studyService.getStudy();
        return ResponseEntity.ok(studies);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addStudy(
            @RequestParam("date") LocalDate date,
            @RequestParam("title") String title,
            @RequestParam("imagePath") MultipartFile imagePath

    ) {
        BaseResponse baseResponse = studyService.addStudy(date, title, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteStudy(@PathVariable Long id) {
        BaseResponse baseResponse = studyService.deleteStudy(id);
        return ResponseEntity.ok(baseResponse);
    }
}
