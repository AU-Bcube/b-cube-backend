package com.b_cube.website.domain.etc.api;

import com.b_cube.website.domain.etc.dto.EtcDTO;
import com.b_cube.website.domain.etc.service.EtcService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/etc")
@RequiredArgsConstructor
public class EtcController {

    private final EtcService etcService;

    @GetMapping
    public ResponseEntity<List<EtcDTO>> getEtc() {
        List<EtcDTO> etcs = etcService.getEtc();
        return ResponseEntity.ok(etcs);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addEtc(
            @RequestParam("date") LocalDate date,
            @RequestParam("title") String title,
            @RequestParam("imagePath") MultipartFile imagePath,
            @RequestParam("pdfPath")  MultipartFile pdfPath

    ) {
        BaseResponse baseResponse = etcService.addEtc(date, title, imagePath, pdfPath);
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<EtcDTO> updateEtc(
            @PathVariable Long id,
            @RequestParam("date") LocalDate date,
            @RequestParam("title") String title,
            @RequestParam("imagePath") MultipartFile imagePath,
            @RequestParam("pdfPath")  MultipartFile pdfPath
    ) {
        EtcDTO etc = etcService.updateEtc(id, date, title, imagePath, pdfPath);
        return ResponseEntity.ok(etc);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteEtc(@PathVariable Long id) {
        BaseResponse baseResponse = etcService.deleteEtc(id);
        return ResponseEntity.ok(baseResponse);
    }
}
