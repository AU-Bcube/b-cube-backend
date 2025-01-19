package com.b_cube.website.domain.executives.api;

import com.b_cube.website.domain.executives.dto.ExecutivesDTO;
import com.b_cube.website.domain.executives.service.ExecutivesService;
import com.b_cube.website.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "ExecutivesController", description = "회장단 컨트롤러")
@RestController
@RequestMapping("/api/executives")
@RequiredArgsConstructor
public class ExecutivesController {

    private final ExecutivesService executivesService;

    @Operation(summary = "회장단 목록 조회")
    @GetMapping
    public ResponseEntity<List<ExecutivesDTO>> getExecutives() {
        List<ExecutivesDTO> executives = executivesService.getExecutives();
        return ResponseEntity.ok(executives);
    }

    @Operation(summary = "회장단 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping
    public ResponseEntity<BaseResponse> addExecutives(
            @Parameter(description = "회장단 이름(문자열)")
            @RequestParam("name") String name,
            @Parameter(description = "연도(문자열)")
            @RequestParam("year") String year,
            @Parameter(description = "회장단 역할(문자열)")
            @RequestParam("role") String role,
            @Parameter(description = "회장단 학과(문자열)")
            @RequestParam("department") String department,
            @Parameter(description = "회장단 학번(문자열)")
            @RequestParam("studentId") String studentId,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath
    ) {
        BaseResponse baseResponse = executivesService.addExecutives(name, year, role, department, studentId, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "회장단 목록 수정", description = "form-data 형식으로 진행해야 함")
    @PatchMapping("/{id}")
    public ResponseEntity<ExecutivesDTO> updateExecutives(
            @PathVariable Long id,
            @Parameter(description = "회장단 이름(문자열)")
            @RequestParam("name") String name,
            @Parameter(description = "연도(문자열)")
            @RequestParam("year") String year,
            @Parameter(description = "회장단 역할(문자열)")
            @RequestParam("role") String role,
            @Parameter(description = "회장단 학과(문자열)")
            @RequestParam("department") String department,
            @Parameter(description = "회장단 학번(문자열)")
            @RequestParam("studentId") String studentId,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath
    ) {
        ExecutivesDTO executive = executivesService.updateExecutives(id, name, year, role, department, studentId, imagePath);
        return ResponseEntity.ok(executive);
    }
}
