package com.b_cube.website.domain.executives.api;

import com.b_cube.website.domain.activities.service.ActivitiesService;
import com.b_cube.website.domain.executives.dto.ExecutivesDTO;
import com.b_cube.website.domain.executives.service.ExecutivesService;
import com.b_cube.website.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/executives")
@RequiredArgsConstructor
public class ExecutivesController {

    private final ExecutivesService executivesService;

    @GetMapping
    public ResponseEntity<List<ExecutivesDTO>> getExecutives() {
        List<ExecutivesDTO> executives = executivesService.getExecutives();
        return ResponseEntity.ok(executives);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addExecutives(
            @RequestParam("name") String name,
            @RequestParam("role") String role,
            @RequestParam("department") String department,
            @RequestParam("studentId") String studentId,
            @RequestParam("imagePath") MultipartFile imagePath
    ) {
        BaseResponse baseResponse = executivesService.addExecutives(name, role, department, studentId, imagePath);
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ExecutivesDTO> updateExecutives(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("role") String role,
            @RequestParam("department") String department,
            @RequestParam("studentId") String studentId,
            @RequestParam("imagePath") MultipartFile imagePath
    ) {
        ExecutivesDTO executive = executivesService.updateExecutives(id, name, role, department, studentId, imagePath);
        return ResponseEntity.ok(executive);
    }
}
