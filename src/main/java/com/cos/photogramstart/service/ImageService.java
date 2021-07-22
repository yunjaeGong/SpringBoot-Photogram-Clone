package com.cos.photogramstart.service;

import com.cos.photogramstart.configuration.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}") // application.yml의 file.path
    private String uploadFolder;

    @Transactional
    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principal) {
        UUID uuid = UUID.randomUUID(); // 파일명이 아닌 uuid에 따라 파일 구분
        String fileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        // 다른 사진이지만 파일명이 같으면? -> 덮어쓰기

        Path imageFilePath = Paths.get(uploadFolder + fileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(fileName, principal.getUser());

        imageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public Page<Image> pageableImageStory(long principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStoryPageable(principalId, pageable);

        // image에 좋아요 상태 담기
        images.forEach((image) -> {

            // image에 좋아요 수 담기
            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                // 각 이미지 별 좋아요 정보
                if (like.getUser().getId() == principalId)
                    image.setLikeState(true);
            });
        });

        return images;
    }

    @Transactional(readOnly = true)
    public List<Image> imageStory(long principalId) {
        return imageRepository.mStory(principalId);
    }
}
