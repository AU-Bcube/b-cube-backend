package com.b_cube.website.domain.sexyit.service;

import com.b_cube.website.domain.sexyit.dto.SexyItDTO;
import com.b_cube.website.domain.sexyit.entity.SexyIt;
import com.b_cube.website.domain.sexyit.exception.SexyItNotFoundException;
import com.b_cube.website.domain.sexyit.repository.SexyItRepository;
import com.b_cube.website.global.dto.BaseResponse;
import com.b_cube.website.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SexyItService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final SexyItRepository sexyItRepository;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_SEXYIT_UPLOAD = "섹시한 IT 업로드가 완료되었습니다.";
    private final String SUCCESS_SEXYIT_DELETE = "섹시한 IT 삭제가 완료되었습니다.";

    public List<SexyItDTO> getSexyIt() {
        List<SexyIt> sexyIts = sexyItRepository.findAll();

        // 섹시한 IT 최신 버전이 위로 오게끔 내림차순으로 정렬
        sexyIts.sort((s1,s2) -> s2.getDate().compareTo(s1.getDate()));

        return sexyIts.stream()
                .map(sexyIt -> SexyItDTO.builder()
                        .id(sexyIt.getId())
                        .date(sexyIt.getDate())
                        .title(sexyIt.getTitle())
                        .imagePath(sexyIt.getImagePath())
                        .url(sexyIt.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public BaseResponse addSexyIt(LocalDate date, String title, String url, MultipartFile imagePath) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);

        // DB에 저장
        SexyIt sexyIt = SexyIt.builder()
                .date(date)
                .title(title)
                .url(url)
                .imagePath(imageUrl)
                .build();
        sexyItRepository.save(sexyIt);

        return BaseResponse.builder()
                .message(SUCCESS_SEXYIT_UPLOAD)
                .build();
    }

    public SexyItDTO updateSexyIt(Long id, LocalDate date, String title, String url, MultipartFile imagePath) {
        // 해당 디자인톤 가져옴
        SexyIt sexyIt = sexyItRepository.findById(id)
                .orElseThrow(() -> new SexyItNotFoundException("해당 섹시한 IT는 존재하지 않습니다."));

        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);

        // 업데이트 할 섹시한 IT 새로 구성
        SexyIt updateSexyIt = SexyIt.builder()
                .id(sexyIt.getId())
                .date(date)
                .title(title)
                .url(url)
                .imagePath(imageUrl)
                .build();

        // DB에 저장
        sexyItRepository.save(updateSexyIt);

        // DTO로 반환
        return convertToSexyItDTO(updateSexyIt);

    }

    public BaseResponse deleteDesignton(Long id) {
        sexyItRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_SEXYIT_DELETE)
                .build();
    }

    private SexyItDTO convertToSexyItDTO(SexyIt sexyIt) {
        return SexyItDTO.builder()
                .id(sexyIt.getId())
                .date(sexyIt.getDate())
                .title(sexyIt.getTitle())
                .url(sexyIt.getUrl())
                .imagePath(sexyIt.getImagePath())
                .build();
    }
}

