package com.b_cube.website.domain.designton.service;

import com.b_cube.website.domain.designton.dto.DesigntonDTO;
import com.b_cube.website.domain.designton.entity.Designton;
import com.b_cube.website.domain.designton.exception.DesigntonNotFoundException;
import com.b_cube.website.domain.designton.repository.DesigntonRepository;
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
public class DesigntonService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final DesigntonRepository designtonRepository;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_DESIGNTON_UPLOAD = "디자인톤 업로드가 완료되었습니다.";
    private final String SUCCESS_DESIGNTON_DELETE = "디자인톤 삭제가 완료되었습니다.";


    public List<DesigntonDTO> getDesignton() {
        List<Designton> designtons = designtonRepository.findAll();

        return designtons.stream()
                .map(designton -> DesigntonDTO.builder()
                        .id(designton.getId())
                        .title(designton.getTitle())
                        .year(designton.getYear())
                        .participant(designton.getParticipant())
                        .imagePath(designton.getImagePath())
                        .pdfPath(designton.getPdfPath())
                        .build())
                .collect(Collectors.toList());
    }

    public BaseResponse addDesignton(String title, String year, String participant, MultipartFile imagePath, MultipartFile pdfPath) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);
        String pdfUrl = s3Uploader.uploadImage(pdfPath, bucketName);

        // DB에 저장
        Designton designton = Designton.builder()
                .title(title)
                .year(year)
                .participant(participant)
                .imagePath(imageUrl)
                .pdfPath(pdfUrl)
                .build();
        designtonRepository.save(designton);

        return BaseResponse.builder()
                .message(SUCCESS_DESIGNTON_UPLOAD)
                .build();
    }

    public DesigntonDTO updateDesignton(Long id, String title, String year, String participant, MultipartFile imagePath, MultipartFile pdfPath) {
        // 해당 디자인톤 가져옴
        Designton designton = designtonRepository.findById(id)
                .orElseThrow(() -> new DesigntonNotFoundException("해당 디자인톤은 존재하지 않습니다."));

        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);
        String pdfUrl = s3Uploader.uploadImage(pdfPath, bucketName);

        // 업데이트 할 디자인톤 새로 구성
        Designton updateDesignton = Designton.builder()
                .id(designton.getId())
                .title(title)
                .year(year)
                .participant(participant)
                .imagePath(imageUrl)
                .pdfPath(pdfUrl)
                .build();

        // DB에 저장
        designtonRepository.save(updateDesignton);

        // DTO 반환
        return convertToDesigntonDTO(updateDesignton);
    }

    public BaseResponse deleteDesignton(Long id) {
        designtonRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_DESIGNTON_DELETE)
                .build();
    }

    private DesigntonDTO convertToDesigntonDTO(Designton designton) {
        return DesigntonDTO.builder()
                .id(designton.getId())
                .title(designton.getTitle())
                .year(designton.getYear())
                .participant(designton.getParticipant())
                .imagePath(designton.getImagePath())
                .pdfPath(designton.getPdfPath())
                .build();
    }
}
