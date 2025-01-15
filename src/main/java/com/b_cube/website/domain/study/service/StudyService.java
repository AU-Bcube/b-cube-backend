package com.b_cube.website.domain.study.service;

import com.b_cube.website.domain.study.dto.StudyDTO;
import com.b_cube.website.domain.study.entity.Study;
import com.b_cube.website.domain.study.repository.StudyRepository;
import com.b_cube.website.global.dto.BaseResponse;
import com.b_cube.website.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final S3Uploader s3Uploader;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final String SUCCESS_STUDY_UPLOAD = "스터디 업로드가 완료되었습니다.";
    private final String SUCCESS_STUDY_DELETE = "스터디 삭제가 완료되었습니다.";

    public List<StudyDTO> getStudy() {
        List<Study> studies = studyRepository.findAll();

        return studies.stream()
                .map(study -> StudyDTO.builder()
                        .id(study.getId())
                        .year(study.getYear())
                        .title(study.getTitle())
                        .imagePath(study.getImagePath())
                        .build())
                .collect(Collectors.toList());

    }

    public BaseResponse addStudy(String year, String title, MultipartFile imagePath) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);

        // DB에 저장
        Study study = Study.builder()
                .year(year)
                .title(title)
                .imagePath(imageUrl)
                .build();
        studyRepository.save(study);

        return BaseResponse.builder()
                .message(SUCCESS_STUDY_UPLOAD)
                .build();
    }

    public BaseResponse deleteStudy(Long id) {
        studyRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_STUDY_DELETE)
                .build();
    }
}
