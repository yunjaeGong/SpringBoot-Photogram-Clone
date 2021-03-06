package com.cos.photogramstart.web.api;

import com.cos.photogramstart.configuration.auth.PrincipalDetails;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.exception.CustomValidationApiException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUpdate(@PathVariable long principalId, MultipartFile profileImageFile,
                                                @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // multipartFile 변수명은 form 의 key 값과 동일해야 파싱됨.
        User updatedUser = userService.updateProfileImage(principalId, profileImageFile);
        principalDetails.setUser(updatedUser); // 세선 정보 변경
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필 사진 수정 완료", null), HttpStatus.OK);
    }

    @PutMapping("/api/user/{userId}")
    public ResponseEntity<?> update(
            @PathVariable int userId,
            @Valid UserUpdateDto dto,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principal) {

        User updatedUser = userService.update(userId, dto.toEntity());
        principal.setUser(updatedUser);
        return new ResponseEntity<>(new CMRespDto<>(1, "회원수정 완료", updatedUser), HttpStatus.OK);
        // 응답 시 message converter 실행되며 updatedUser의 모든 getter, setter 호출

    }

    @GetMapping("/api/user/{pageUserId}/subscribe") // profile page 주인의 구독 정보
    public ResponseEntity<?> subscribeList(@PathVariable long pageUserId, @AuthenticationPrincipal PrincipalDetails principal) {

        List<SubscribeDto> subscribeDto = subscribeService.subscribeList(principal.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
    }
}
