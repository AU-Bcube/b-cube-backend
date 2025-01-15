package com.b_cube.website.domain.etc.service;

import com.b_cube.website.domain.designton.exception.DesigntonNotFoundException;
import com.b_cube.website.domain.etc.dto.EtcDTO;
import com.b_cube.website.domain.etc.entity.Etc;
import com.b_cube.website.domain.etc.repository.EtcRepository;
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
public class EtcService {

    private final EtcRepository etcRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_ETC_UPLOAD = "기타활동 업로드가 완료되었습니다.";
    private final String SUCCESS_ETC_DELETE = "기타활동 삭제가 완료되었습니다.";


    public List<EtcDTO> getEtc() {
        List<Etc> etcs = etcRepository.findAll();

        return etcs.stream()
                .map(etc -> EtcDTO.builder()
                        .id(etc.getId())
                        .year(etc.getYear())
                        .title(etc.getTitle())
                        .participant(etc.getParticipant())
                        .imagePath(etc.getImagePath())
                        .pdfPath(etc.getPdfPath())
                        .build())
                .collect(Collectors.toList());

    }

    public BaseResponse addEtc(String year, String title, String participant , MultipartFile imagePath, MultipartFile pdfPath) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);
        String pdfUrl = s3Uploader.uploadImage(pdfPath, bucketName);

        // DB에 저장
        Etc etc = Etc.builder()
                .year(year)
                .title(title)
                .participant(participant)
                .imagePath(imageUrl)
                .pdfPath(pdfUrl)
                .build();
        etcRepository.save(etc);

        return BaseResponse.builder()
                .message(SUCCESS_ETC_UPLOAD)
                .build();
    }

    public EtcDTO updateEtc(Long id, String year, String title, String participant, MultipartFile imagePath, MultipartFile pdfPath) {
        // 해당 기타활동 가져옴
        Etc etc = etcRepository.findById(id)
                .orElseThrow(() -> new DesigntonNotFoundException("해당 기타활동은 존재하지 않습니다."));

        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);
        String pdfUrl = s3Uploader.uploadImage(pdfPath, bucketName);

        // 업데이트 할 기타활동 새로 구성
        Etc updateEtc = Etc.builder()
                .id(etc.getId())
                .year(year)
                .title(title)
                .participant(participant)
                .imagePath(imageUrl)
                .pdfPath(pdfUrl)
                .build();

        // DB에 저장
        etcRepository.save(updateEtc);

        // DTO로 반환
        return convertToEtcDTO(updateEtc);
    }

    private EtcDTO convertToEtcDTO(Etc etc) {
        return EtcDTO.builder()
                .id(etc.getId())
                .year(etc.getYear())
                .title(etc.getTitle())
                .participant(etc.getParticipant())
                .imagePath(etc.getImagePath())
                .pdfPath(etc.getPdfPath())
                .build();
    }

    public BaseResponse deleteEtc(Long id) {
        etcRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_ETC_DELETE)
                .build();
    }
}
