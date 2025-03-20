package com.b_cube.website.global.service;

import com.b_cube.website.global.exception.FileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageHandler {

    @Value("${file.access.img.dir}")
    private String accessImgDir;
    @Value("${file.access.pdf.dir}")
    private String accessPdfDir;
    @Value("${file.upload.pdf.dir}")
    private String uploadPdfDir;
    @Value("${file.upload.img.dir}")
    private String uploadImgDir;

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
        String fileUrl = accessImgDir + fileName;
        return fileUrl;
    }

    public String savePDF(MultipartFile pdf) throws IOException {
        // 정적 파일 저장
        String fileName = getOriginName(pdf);
        String fullPathName = uploadPdfDir + fileName;
        pdf.transferTo(new File(fullPathName));

        // Nginx를 통해 접근 가능한 URL 생성
        String fileUrl = accessPdfDir + fileName;
        return fileUrl;
    }

    public void deleteImage(String imagePath) {
        int index = imagePath.indexOf("/imgs/");
        System.out.println(index);
        String fileName = imagePath.substring(index + 6);
        System.out.println(fileName);
        String fullPathName = uploadImgDir + fileName;
        System.out.println(fullPathName);

        // 파일 객체 생성
        File file = new File(fullPathName);

        // 파일이 존재하면 삭제, 없으면 예외 던짐
        if(file.exists()) {
            file.delete();
        } else {
            throw new FileNotFoundException("해당 파일을 찾을 수 없습니다.");
        }
    }

    public void deletePdf(String imagePath) {
        int index = imagePath.indexOf("/pdf/");
        System.out.println(index);
        String fileName = imagePath.substring(index + 5);
        System.out.println(fileName);
        String fullPathName = uploadImgDir + fileName;
        System.out.println(fullPathName);

        // 파일 객체 생성
        File file = new File(fullPathName);

        // 파일이 존재하면 삭제, 없으면 예외 던짐
        if(file.exists()) {
            file.delete();
        } else {
            throw new FileNotFoundException("해당 파일을 찾을 수 없습니다.");
        }
    }

    private String getOriginName(MultipartFile image) {
        return image.getOriginalFilename();
    }
}
