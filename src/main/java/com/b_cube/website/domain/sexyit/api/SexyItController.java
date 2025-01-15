package com.b_cube.website.domain.sexyit.api;

import com.b_cube.website.domain.sexyit.dto.SexyItDTO;
import com.b_cube.website.domain.sexyit.service.SexyItService;
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

@Tag(name = "SexyItController", description = "섹시한 IT 컨트롤러")
@RestController
@RequestMapping("/sexyit")
@RequiredArgsConstructor
public class SexyItController {

    private final SexyItService sexyItService;

    @Operation(summary = "섹시한 IT 목록 조회")
    @GetMapping
    public ResponseEntity<List<SexyItDTO>> getSexyIt() {
        List<SexyItDTO> sexyIts = sexyItService.getSexyIt();
        return ResponseEntity.ok(sexyIts);
    }

    @Operation(summary = "섹시한 IT 목록 추가", description = "form-data 형식으로 진행해야 함")
    @PostMapping
    public ResponseEntity<BaseResponse> addSexyIt(
            @Parameter(description = "날짜 형식(ex. 2025-01-10)")
            @RequestParam("date")LocalDate date,
            @Parameter(description = "섹시한 IT 제목(문자열)")
            @RequestParam("title") String title,
            @Parameter(description = "섹시한 IT 인스타그램 url 주소(문자열)")
            @RequestParam("url") String url,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath

    ) {
        BaseResponse baseResponse = sexyItService.addSexyIt(date, title, url, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @Operation(summary = "섹시한 IT 목록 수정", description = "form-data 형식으로 진행해야 함")
    @PatchMapping("/{id}")
    public ResponseEntity<SexyItDTO> updateSexyIt(
            @PathVariable Long id,
            @Parameter(description = "날짜 형식(ex. 2025-01-10)")
            @RequestParam("date")LocalDate date,
            @Parameter(description = "섹시한 IT 제목(문자열)")
            @RequestParam("title") String title,
            @Parameter(description = "섹시한 IT 인스타그램 url 주소(문자열)")
            @RequestParam("url") String url,
            @Parameter(description = "MultipartFile 이미지 삽입")
            @RequestParam("imagePath") MultipartFile imagePath
    ) {
        SexyItDTO sexyIt = sexyItService.updateSexyIt(id, date, title, url, imagePath);
        return ResponseEntity.ok(sexyIt);
    }

    @Operation(summary = "섹시한 IT 목록 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteSexyIt(
            @Parameter(description = "섹시한 IT id 값")
            @PathVariable Long id
    ) {
        BaseResponse baseResponse = sexyItService.deleteDesignton(id);
        return ResponseEntity.ok(baseResponse);
    }
}
