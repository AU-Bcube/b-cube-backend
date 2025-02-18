package com.b_cube.website.domain.photo.service;

import com.b_cube.website.domain.photo.dto.PhotoDTO;
import com.b_cube.website.domain.photo.entity.Photo;
import com.b_cube.website.domain.photo.repository.PhotoRepository;
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
public class PhotoService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final PhotoRepository photoRepository;
    private final S3Uploader s3Uploader;
    private final String SUCCESS_PHOTO_UPLOAD = "활동 사진 업로드가 완료되었습니다.";
    private final String SUCCESS_PHOTO_DELETE = "활동 사진 삭제가 완료되었습니다.";

    public List<PhotoDTO> getPhoto() {
        List<Photo> photos = photoRepository.findAll();

        // 활동사진 최신 버전이 먼저 나오도록 내림차순 정렬
        photos.sort((s1,s2) -> s2.getDate().compareTo(s1.getDate()));

        return photos.stream()
                .map(photo -> PhotoDTO.builder()
                        .id(photo.getId())
                        .description(photo.getDescription())
                        .date(photo.getDate())
                        .imagePath(photo.getImagePath())
                        .build())
                .collect(Collectors.toList());
    }


    public BaseResponse addPhoto(String description, LocalDate date, MultipartFile imagePath) {
        // S3에 파일 업로드
        String imageUrl = s3Uploader.uploadImage(imagePath, bucketName);

        if (date == null) {
            // DB에 저장
            Photo photo = Photo.builder()
                    .description(description)
                    .date(LocalDate.now())
                    .imagePath(imageUrl)
                    .build();
            photoRepository.save(photo);
        } else {
            // DB에 저장
            Photo photo = Photo.builder()
                    .description(description)
                    .date(date)
                    .imagePath(imageUrl)
                    .build();
            photoRepository.save(photo);
        }

        return BaseResponse.builder()
                .message(SUCCESS_PHOTO_UPLOAD)
                .build();
    }


    public BaseResponse deletePhoto(Long id) {

        photoRepository.deleteById(id);
        return BaseResponse.builder()
                .message(SUCCESS_PHOTO_DELETE)
                .build();

    }
}
