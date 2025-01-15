package com.b_cube.website.domain.executives.service;

import com.b_cube.website.domain.executives.dto.ExecutivesDTO;
import com.b_cube.website.domain.executives.entity.Executives;
import com.b_cube.website.domain.executives.exception.ExecutivesNotFoundException;
import com.b_cube.website.domain.executives.repository.ExecutivesRepository;
import com.b_cube.website.global.dto.BaseResponse;
import com.b_cube.website.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExecutivesService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final ExecutivesRepository executivesRepository;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_EXECUTIVE_UPLOAD = "회장단 업로드가 완료되었습니다.";

    public List<ExecutivesDTO> getExecutives() {
        // 회장단 리스트 가져옴
        List<Executives> executives = executivesRepository.findAll();

        // DTO 전환
        return executives.stream()
                .map(executive -> ExecutivesDTO.builder()
                        .id(executive.getId())
                        .role(executive.getRole())
                        .department(executive.getDepartment())
                        .year(executive.getYear())
                        .studentId(executive.getStudentId())
                        .imagePath(executive.getImagePath())
                        .build())
                .collect(Collectors.toList());
    }

    public BaseResponse addExecutives(String name, String year, String role, String department, String studentId, MultipartFile imagePath) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);

        // DB에 저장
        Executives executives = Executives.builder()
                .name(name)
                .year(year)
                .role(role)
                .department(department)
                .studentId(studentId)
                .imagePath(imageUrl)
                .build();
        executivesRepository.save(executives);

        return BaseResponse.builder()
                .message(SUCCESS_EXECUTIVE_UPLOAD)
                .build();
    }

    public ExecutivesDTO updateExecutives(Long id, String name, String year, String role, String department, String studentId, MultipartFile imagePath) {
        // 해당 회장단 가져옴
        Executives executive = executivesRepository.findById(id)
                .orElseThrow(() -> new ExecutivesNotFoundException("해당 회장단은 존재하지 않습니다."));

        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);

        // 업데이트 할 회장단 새로 구성
        Executives updateExecutive = Executives.builder()
                .id(executive.getId())
                .name(name)
                .year(year)
                .role(role)
                .department(department)
                .studentId(studentId)
                .imagePath(imageUrl)
                .build();

        // DB에 저장
        executivesRepository.save(updateExecutive);

        // DTO로 반환
        return convertToExecutivesDTO(updateExecutive);
    }

    private ExecutivesDTO convertToExecutivesDTO(Executives executives) {
        return ExecutivesDTO.builder()
                    .id(executives.getId())
                    .name(executives.getName())
                    .year(executives.getYear())
                    .role(executives.getRole())
                    .department(executives.getDepartment())
                    .studentId(executives.getStudentId())
                    .imagePath(executives.getImagePath())
                    .build();
    }
}


