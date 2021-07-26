package com.cos.photogramstart.web;

import com.cos.photogramstart.configuration.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.exception.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/stroy"})
    public String story() {
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular(Model model) {
        List<Image> images = imageService.imagePopular();
        model.addAttribute("images", images);
        return "image/popular";
    }

    @GetMapping("/image/imageUpload")
    public String upload() {
        return "image/upload";
    }

    // 이미지 업로드
    @PostMapping("/image")
    public String imageUpload(ImageUploadDto dto, @AuthenticationPrincipal PrincipalDetails principal) {

        if (dto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }

        User currentUser = principal.getUser();
        imageService.imageUpload(dto, principal);
        return "redirect:/user/" + currentUser.getId();
    }
}
