package com.b_cube.website.domain.sexyit.api;

import com.b_cube.website.domain.sexyit.dto.SexyItDTO;
import com.b_cube.website.domain.sexyit.service.SexyItService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sexyit")
@RequiredArgsConstructor
public class SexyItController {

    private final SexyItService sexyItService;

    @GetMapping
    public ResponseEntity<List<SexyItDTO>> getSexyIt() {
        List<SexyItDTO> sexyIts = sexyItService.getSexyIt();
        return ResponseEntity.ok(sexyIts);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addSexyIt(
            @RequestParam("date")LocalDate date,
            @RequestParam("title") String title,
            @RequestParam("url") String url,
            @RequestParam("imagePath") MultipartFile imagePath

    ) {
        BaseResponse baseResponse = sexyItService.addSexyIt(date, title, url, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<SexyItDTO> updateSexyIt(
            @PathVariable Long id,
            @RequestParam("date") LocalDate date,
            @RequestParam("title") String title,
            @RequestParam("url") String url,
            @RequestParam("imagePath") MultipartFile imagePath
    ) {
        SexyItDTO sexyIt = sexyItService.updateSexyIt(id, date, title, url, imagePath);
        return ResponseEntity.ok(sexyIt);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteSexyIt(@PathVariable Long id) {
        BaseResponse baseResponse = sexyItService.deleteDesignton(id);
        return ResponseEntity.ok(baseResponse);
    }
}
