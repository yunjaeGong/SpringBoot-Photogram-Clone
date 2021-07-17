package com.cos.photogramstart.web;

import com.cos.photogramstart.configuration.auth.PrincipalDetails;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ImageService imageService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principal) {
        // 업로드 된 이미지를 profile 페이지에 들고와야
        UserProfileDto dto = userService.profile(pageUserId, principal.getUser().getId());

        model.addAttribute("dto", dto);
        // 이 페이지를 방문하는 유저가 게시글을 작성한 본인인지 구분 - 구독하기 버튼
        return "user/profile";
    }

    @GetMapping("/user/{userId}/update")
    public String updateForm(@PathVariable int userId, @AuthenticationPrincipal PrincipalDetails principal) {
        return "user/update";
    }
}
