package com.b_cube.website.domain.interview.service;

import com.b_cube.website.domain.interview.dto.InterviewDTO;
import com.b_cube.website.domain.interview.entity.Interview;
import com.b_cube.website.domain.interview.repository.InterviewRepository;
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
public class InterviewService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final InterviewRepository interviewRepository;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_INTERVIEW_UPLOAD = "인터뷰 업로드가 완료되었습니다.";
    private final String SUCCESS_INTERVIEW_DELETE = "인터뷰 삭제가 완료되었습니다.";

    public List<InterviewDTO> getInterview() {
        List<Interview> interviews = interviewRepository.findAll();

        return interviews.stream()
                .map(interview -> InterviewDTO.builder()
                        .id(interview.getId())
                        .name(interview.getName())
                        .studentId(interview.getStudentId())
                        .introduction(interview.getIntroduction())
                        .imagePath(interview.getImagePath())
                        .build())
                .collect(Collectors.toList());
    }

    public BaseResponse addInterview(String name, String studentId, String introduction, MultipartFile imagePath) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);

        // DB에 저장
        Interview interview = Interview.builder()
                .name(name)
                .studentId(studentId)
                .introduction(introduction)
                .imagePath(imageUrl)
                .build();
        interviewRepository.save(interview);

        return BaseResponse.builder()
                .message(SUCCESS_INTERVIEW_UPLOAD)
                .build();
    }

    public BaseResponse deleteInterview(Long id) {
        interviewRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_INTERVIEW_DELETE)
                .build();
    }
}
