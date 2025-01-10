package com.b_cube.website.domain.activities.service;

import com.b_cube.website.domain.activities.dto.ActivitiesDTO;
import com.b_cube.website.domain.activities.entity.Activities;
import com.b_cube.website.domain.activities.repository.ActivitiesRepository;
import com.b_cube.website.global.dto.BaseResponse;
import com.b_cube.website.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivitiesService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final ActivitiesRepository activitiesRepository;
    private final S3Uploader s3Uploader;

    private final String SUCCESS_ACTIVITY_UPLOAD = "활동 업로드가 완료되었습니다.";
    private final String SUCCESS_ACTIVITY_DELETE = "활동 삭제가 완료되었습니다.";


    public List<ActivitiesDTO> getActivities() {
        // 활동 리스트 가져옴
        List<Activities> activities = activitiesRepository.findAll();

        // DTO로 변환
        return activities.stream()
                .map(activity -> ActivitiesDTO.builder()
                        .id(activity.getId())
                        .title(activity.getTitle())
                        .description(activity.getDescription())
                        .imagePath(activity.getImagePath())
                        .pdfPath(activity.getPdfPath())
                        .build())
                .collect(Collectors.toList());
    }

    public BaseResponse addActivities(
            String title,
            String description,
            MultipartFile imagePath,
            MultipartFile pdfPath
    ) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);
        String pdfUrl = s3Uploader.uploadImage(pdfPath, bucketName);

        // DB에 저장
        Activities activities = Activities.builder()
                .title(title)
                .description(description)
                .imagePath(imageUrl)
                .pdfPath(pdfUrl)
                .build();
        activitiesRepository.save(activities);

        return BaseResponse.builder()
                .message(SUCCESS_ACTIVITY_UPLOAD)
                .build();
    }

    public BaseResponse deleteActivities(Long id) {
        activitiesRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_ACTIVITY_DELETE)
                .build();
    }
}
