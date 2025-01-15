package com.b_cube.website.domain.interview.api;

import com.b_cube.website.domain.interview.dto.InterviewDTO;
import com.b_cube.website.domain.interview.service.InterviewService;
import com.b_cube.website.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "InterviewController", description = "인터뷰 컨트롤러")
@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @Operation(summary = "인터뷰 목록 조회")
    @GetMapping
    public ResponseEntity<List<InterviewDTO>> getInterview() {
        List<InterviewDTO> interviews = interviewService.getInterview();
        return ResponseEntity.ok(interviews);
    }

    @Operation(summary = "인터뷰 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping
    public ResponseEntity<BaseResponse> addInterview(
            @Parameter(description = "인터뷰 성함(문자열)")
            @RequestParam("name") String name,
            @Parameter(description = "인터뷰 학번(문자열)")
            @RequestParam("studentId") String studentId,
            @Parameter(description = "인터뷰 소개 글(문자열)")
            @RequestParam("introduction") String introduction,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath

    ) {
        BaseResponse baseResponse = interviewService.addInterview(name, studentId, introduction, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "인터뷰 목록 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteInterview(
            @Parameter(description = "인터뷰 id 값")
            @PathVariable Long id
    ) {
        BaseResponse baseResponse = interviewService.deleteInterview(id);
        return ResponseEntity.ok(baseResponse);
    }
}
