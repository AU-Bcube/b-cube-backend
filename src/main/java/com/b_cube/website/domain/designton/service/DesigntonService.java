package com.b_cube.website.domain.designton.service;

import com.b_cube.website.domain.designton.dto.DesigntonDTO;
import com.b_cube.website.domain.designton.entity.Designton;
import com.b_cube.website.domain.designton.exception.DesigntonNotFoundException;
import com.b_cube.website.domain.designton.repository.DesigntonRepository;
import com.b_cube.website.domain.study.entity.Study;
import com.b_cube.website.domain.study.exception.StudyNotFoundException;
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
public class DesigntonService {

    private final ImageHandler imageHandler;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final DesigntonRepository designtonRepository;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_DESIGNTON_UPLOAD = "디자인톤 업로드가 완료되었습니다.";
    private final String SUCCESS_DESIGNTON_DELETE = "디자인톤 삭제가 완료되었습니다.";


    public List<DesigntonDTO> getDesignton() {
        List<Designton> designtons = designtonRepository.findAll();

        // 디자인톤 활동 최신 버전이 먼저 나오도록 내림차순 정렬
        designtons.sort((s1,s2) -> s2.getYear().compareTo(s1.getYear()));

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

    public BaseResponse addDesignton(String title, String year, String participant, MultipartFile imagePath, MultipartFile pdfPath) throws IOException {
        String fileImgUrl = imageHandler.saveImage(imagePath);
        String filePdfUrl = imageHandler.savePDF(pdfPath);

        // DB에 저장
        Designton designton = Designton.builder()
                .title(title)
                .year(year)
                .participant(participant)
                .imagePath(fileImgUrl)
                .pdfPath(filePdfUrl)
                .build();
        designtonRepository.save(designton);

        return BaseResponse.builder()
                .message(SUCCESS_DESIGNTON_UPLOAD)
                .build();
    }

    public DesigntonDTO updateDesignton(Long id, String title, String year, String participant, MultipartFile imagePath, MultipartFile pdfPath) throws IOException {
        // 해당 디자인톤 가져옴
        Designton designton = designtonRepository.findById(id)
                .orElseThrow(() -> new DesigntonNotFoundException("해당 디자인톤은 존재하지 않습니다."));

        imageHandler.deleteImage(designton.getImagePath());
        imageHandler.deletePdf(designton.getPdfPath());
        String fileImgUrl = imageHandler.saveImage(imagePath);
        String filePdfUrl = imageHandler.savePDF(pdfPath);

        // 업데이트 할 디자인톤 새로 구성
        Designton updateDesignton = Designton.builder()
                .id(designton.getId())
                .title(title)
                .year(year)
                .participant(participant)
                .imagePath(fileImgUrl)
                .pdfPath(filePdfUrl)
                .build();

        // DB에 저장
        designtonRepository.save(updateDesignton);

        // DTO 반환
        return convertToDesigntonDTO(updateDesignton);
    }

    public BaseResponse deleteDesignton(Long id) {
        Designton designton = designtonRepository.findById(id)
                .orElseThrow(() -> new DesigntonNotFoundException("해당 디자인톤은 존재하지 않습니다."));
        imageHandler.deleteImage(designton.getImagePath());
        imageHandler.deletePdf(designton.getPdfPath());

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
