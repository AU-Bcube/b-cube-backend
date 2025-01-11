package com.b_cube.website.domain.interview.api;

import com.b_cube.website.domain.interview.dto.InterviewDTO;
import com.b_cube.website.domain.interview.service.InterviewService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @GetMapping
    public ResponseEntity<List<InterviewDTO>> getInterview() {
        List<InterviewDTO> interviews = interviewService.getInterview();
        return ResponseEntity.ok(interviews);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addInterview(
            @RequestParam("name") String name,
            @RequestParam("studentId") String studentId,
            @RequestParam("introduction") String introduction,
            @RequestParam("imagePath") MultipartFile imagePath,
            @RequestParam("email")  String  email

    ) {
        BaseResponse baseResponse = interviewService.addInterview(name, studentId, introduction, imagePath, email);
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteInterview(@PathVariable Long id) {
        BaseResponse baseResponse = interviewService.deleteInterview(id);
        return ResponseEntity.ok(baseResponse);
    }
}
