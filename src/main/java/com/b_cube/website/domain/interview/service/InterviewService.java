package com.b_cube.website.domain.interview.service;

import com.b_cube.website.domain.designton.entity.Designton;
import com.b_cube.website.domain.designton.exception.DesigntonNotFoundException;
import com.b_cube.website.domain.interview.dto.InterviewDTO;
import com.b_cube.website.domain.interview.entity.Interview;
import com.b_cube.website.domain.interview.exception.InterviewNotFoundException;
import com.b_cube.website.domain.interview.repository.InterviewRepository;
import com.b_cube.website.global.dto.BaseResponse;
import com.b_cube.website.global.service.ImageHandler;
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
public class InterviewService {

    private final ImageHandler imageHandler;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final InterviewRepository interviewRepository;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_INTERVIEW_UPLOAD = "인터뷰 업로드가 완료되었습니다.";
    private final String SUCCESS_INTERVIEW_DELETE = "인터뷰 삭제가 완료되었습니다.";

    public List<InterviewDTO> getInterview() {
        List<Interview> interviews = interviewRepository.findAll();

        // OB 선배의 학번이 최신 학번이 위로 오도록 내림차순 정렬
        interviews.sort((s1,s2) -> s2.getStudentId().compareTo(s1.getStudentId()));

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

    public BaseResponse addInterview(String name, String studentId, String introduction, MultipartFile imagePath) throws IOException {
        String fileImgUrl = imageHandler.saveImage(imagePath);

        // DB에 저장
        Interview interview = Interview.builder()
                .name(name)
                .studentId(studentId)
                .introduction(introduction)
                .imagePath(fileImgUrl)
                .build();
        interviewRepository.save(interview);

        return BaseResponse.builder()
                .message(SUCCESS_INTERVIEW_UPLOAD)
                .build();
    }

    public BaseResponse deleteInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new InterviewNotFoundException("해당 인터뷰는 존재하지 않습니다."));
        imageHandler.deleteImage(interview.getImagePath());

        interviewRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_INTERVIEW_DELETE)
                .build();
    }
}
