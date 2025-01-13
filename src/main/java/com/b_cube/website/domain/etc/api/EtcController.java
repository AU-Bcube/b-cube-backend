package com.b_cube.website.domain.etc.api;

import com.b_cube.website.domain.etc.dto.EtcDTO;
import com.b_cube.website.domain.etc.service.EtcService;
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

@Tag(name = "EtcController", description = "기타활동 컨트롤러")
@RestController
@RequestMapping("/etc")
@RequiredArgsConstructor
public class EtcController {

    private final EtcService etcService;

    @Operation(summary = "기타활동 목록 조회")
    @GetMapping
    public ResponseEntity<List<EtcDTO>> getEtc() {
        List<EtcDTO> etcs = etcService.getEtc();
        return ResponseEntity.ok(etcs);
    }

    @Operation(summary = "기타활동 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addEtc(
            @Parameter(description = "날짜 형식(ex. 2025-01-10)")
            @RequestParam("date") LocalDate date,
            @Parameter(description = "활동 제목(문자열)")
            @RequestParam("title") String title,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath,
            @Parameter(description = "MultipartFile pdf 삽입")
            @RequestParam("pdfPath")  MultipartFile pdfPath

    ) {
        BaseResponse baseResponse = etcService.addEtc(date, title, imagePath, pdfPath);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "기타활동 목록 수정", description = "form-data 형식으로 진행해야 함")
    @PatchMapping("/update/{id}")
    public ResponseEntity<EtcDTO> updateEtc(
            @PathVariable Long id,
            @Parameter(description = "날짜 형식(ex. 2025-01-10)")
            @RequestParam("date") LocalDate date,
            @Parameter(description = "활동 제목(문자열)")
            @RequestParam("title") String title,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath,
            @Parameter(description = "MultipartFile pdf 삽입")
            @RequestParam("pdfPath")  MultipartFile pdfPath
    ) {
        EtcDTO etc = etcService.updateEtc(id, date, title, imagePath, pdfPath);
        return ResponseEntity.ok(etc);
    }

    @Operation(summary = "기타활동 목록 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteEtc(
            @Parameter(description = "기타활동 id 값")
            @PathVariable Long id
    ) {
        BaseResponse baseResponse = etcService.deleteEtc(id);
        return ResponseEntity.ok(baseResponse);
    }
}
