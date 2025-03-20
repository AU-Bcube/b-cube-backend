package com.b_cube.website.domain.etc.service;

import com.b_cube.website.domain.designton.entity.Designton;
import com.b_cube.website.domain.designton.exception.DesigntonNotFoundException;
import com.b_cube.website.domain.etc.dto.EtcDTO;
import com.b_cube.website.domain.etc.entity.Etc;
import com.b_cube.website.domain.etc.exception.EtcNotFoundException;
import com.b_cube.website.domain.etc.repository.EtcRepository;
import com.b_cube.website.global.dto.BaseResponse;
import com.b_cube.website.global.service.ImageHandler;
import com.b_cube.website.global.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtcService {

    private final EtcRepository etcRepository;
    private final ImageHandler imageHandler;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_ETC_UPLOAD = "기타활동 업로드가 완료되었습니다.";
    private final String SUCCESS_ETC_DELETE = "기타활동 삭제가 완료되었습니다.";


    public List<EtcDTO> getEtc() {
        List<Etc> etcs = etcRepository.findAll();

        // 기타활동 최신 버전이 먼저 나오도록 내림차순 정렬
        etcs.sort((s1,s2) -> s2.getYear().compareTo(s1.getYear()));

        return etcs.stream()
                .map(etc -> EtcDTO.builder()
                        .id(etc.getId())
                        .year(etc.getYear())
                        .title(etc.getTitle())
                        .award(etc.getAward())
                        .participant(etc.getParticipant())
                        .imagePath(etc.getImagePath())
                        .pdfPath(etc.getPdfPath())
                        .build())
                .collect(Collectors.toList());

    }

    public BaseResponse addEtc(String year, String title, String award ,String participant , MultipartFile imagePath, MultipartFile pdfPath) throws IOException {
        String fileImgUrl = imageHandler.saveImage(imagePath);
        String filePdfUrl = imageHandler.savePDF(pdfPath);

        // DB에 저장
        Etc etc = Etc.builder()
                .year(year)
                .title(title)
                .award(award)
                .participant(participant)
                .imagePath(fileImgUrl)
                .pdfPath(filePdfUrl)
                .build();
        etcRepository.save(etc);

        return BaseResponse.builder()
                .message(SUCCESS_ETC_UPLOAD)
                .build();
    }

    public EtcDTO updateEtc(Long id, String year, String title, String award, String participant, MultipartFile imagePath, MultipartFile pdfPath) throws IOException {
        // 해당 기타활동 가져옴
        Etc etc = etcRepository.findById(id)
                .orElseThrow(() -> new DesigntonNotFoundException("해당 기타활동은 존재하지 않습니다."));

        imageHandler.deleteImage(etc.getImagePath());
        imageHandler.deletePdf(etc.getPdfPath());
        String fileImgUrl = imageHandler.saveImage(imagePath);
        String filePdfUrl = imageHandler.savePDF(pdfPath);

        // 업데이트 할 기타활동 새로 구성
        Etc updateEtc = Etc.builder()
                .id(etc.getId())
                .year(year)
                .title(title)
                .award(award)
                .participant(participant)
                .imagePath(fileImgUrl)
                .pdfPath(filePdfUrl)
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
                .award(etc.getAward())
                .participant(etc.getParticipant())
                .imagePath(etc.getImagePath())
                .pdfPath(etc.getPdfPath())
                .build();
    }

    public BaseResponse deleteEtc(Long id) {
        Etc etc = etcRepository.findById(id)
                .orElseThrow(() -> new EtcNotFoundException("해당 기타활동은 존재하지 않습니다."));
        imageHandler.deleteImage(etc.getImagePath());
        imageHandler.deletePdf(etc.getPdfPath());

        etcRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_ETC_DELETE)
                .build();
    }
}
