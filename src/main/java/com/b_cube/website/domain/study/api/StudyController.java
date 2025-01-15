package com.b_cube.website.domain.study.api;

import com.b_cube.website.domain.study.dto.StudyDTO;
import com.b_cube.website.domain.study.service.StudyService;
import com.b_cube.website.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "StudyController", description = "스터디 컨트롤러")
@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @Operation(summary = "스터디 목록 조회")
    @GetMapping
    public ResponseEntity<List<StudyDTO>> getStudy() {
        List<StudyDTO> studies = studyService.getStudy();
        return ResponseEntity.ok(studies);
    }

    @Operation(summary = "스터디 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping
    public ResponseEntity<BaseResponse> addStudy(
            @Parameter(description = "연도(문자열)")
            @RequestParam("year") String year,
            @Parameter(description = "스터디 제목(문자열)")
            @RequestParam("title") String title,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath

    ) {
        BaseResponse baseResponse = studyService.addStudy(year, title, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "스터디 목록 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteStudy(
            @Parameter(description = "스터디 id 값")
            @PathVariable Long id
    ) {
        BaseResponse baseResponse = studyService.deleteStudy(id);
        return ResponseEntity.ok(baseResponse);
    }
}
