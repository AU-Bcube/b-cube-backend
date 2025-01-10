package com.b_cube.website.domain.designton.api;

import com.b_cube.website.domain.designton.dto.DesigntonDTO;
import com.b_cube.website.domain.designton.service.DesigntonService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/designton")
@RequiredArgsConstructor
public class DesigntonController {

    private final DesigntonService designtonService;

    @GetMapping
    public ResponseEntity<List<DesigntonDTO>> getDesignton() {
        List<DesigntonDTO> designtons = designtonService.getDesignton();
        return ResponseEntity.ok(designtons);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addDesignton(
            @RequestParam("projectName") String projectName,
            @RequestParam("year") String year,
            @RequestParam("participant") String participant,
            @RequestParam("imagePath") MultipartFile imagePath,
            @RequestParam("pdfPath")  MultipartFile pdfPath

    ) {
        BaseResponse baseResponse = designtonService.addDesignton(projectName, year, participant, imagePath, pdfPath);
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<DesigntonDTO> updateDesignton(
            @PathVariable Long id,
            @RequestParam("projectName") String projectName,
            @RequestParam("year") String year,
            @RequestParam("participant") String participant,
            @RequestParam("imagePath") MultipartFile imagePath,
            @RequestParam("pdfPath")  MultipartFile pdfPath
    ) {
        DesigntonDTO designton = designtonService.updateDesignton(id, projectName, year, participant, imagePath, pdfPath);
        return ResponseEntity.ok(designton);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteDesignton(@PathVariable Long id) {
        BaseResponse baseResponse = designtonService.deleteDesignton(id);
        return ResponseEntity.ok(baseResponse);
    }


}
