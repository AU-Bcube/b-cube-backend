package com.b_cube.website.global.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageHandler {

    @Value("${file.upload.img.dir}")
    private String uploadImgDir;
    @Value("${file.upload.pdf.dir}")
    private String uploadPdfDir;
    @Value("${file.access.dir}")
    private String accessDir;

    /**
     * MultipartFile을 받아서 지정된 경로에 이미지를 저장하는 기능
     * @param image
     * @return 경로 반환
     * @throws IOException
     */
    public String saveImage(MultipartFile image) throws IOException {
        // 정적 파일 저장
        String fileName = getOriginName(image);
        String fullPathName = uploadImgDir + fileName;
        image.transferTo(new File(fullPathName));

        // Nginx를 통해 접근 가능한 URL 생성
        String fileUrl = accessDir + fileName;
        return fileUrl;
    }

    public String savePDF(MultipartFile pdf) throws IOException {
        // 정적 파일 저장
        String fileName = getOriginName(pdf);
        String fullPathName = uploadPdfDir + fileName;
        pdf.transferTo(new File(fullPathName));

        // Nginx를 통해 접근 가능한 URL 생성
        String fileUrl = accessDir + fileName;
        return fileUrl;
    }

    private String getOriginName(MultipartFile image) {
        return image.getOriginalFilename();
    }
}
