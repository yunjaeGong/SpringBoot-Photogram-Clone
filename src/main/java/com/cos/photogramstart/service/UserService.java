package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.exception.CustomException;
import com.cos.photogramstart.handler.exception.CustomValidationApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true) // dirty checking x
    public UserProfileDto profile(long pageUserId, long principalId) {
        // 유저가 업로드 한 모든 사진 가져오기
        UserProfileDto dto = new UserProfileDto();
        User user = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(user);

        // 기본 페이지 정보
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(user.getImages().size());

        // 구독 정보
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
        int subscribedState = subscribeRepository.mSubscribeState(pageUserId, principalId);
        dto.setSubscribeCount(subscribeCount);
        dto.setSubscribedStatus(subscribedState == 1);

        return dto;
    }

    @Transactional
    public User update(long userId, User updatedUser) {
        // 1. 영속성 컨텍스트에 영속화
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomValidationApiException("찾을 수 없는 id 입니다.");
        });

        // 2. 영속화된 Object를 수정
        user.setName(updatedUser.getName());

        String updatedPassword = updatedUser.getPassword();
        updatedPassword = passwordEncoder.encode(updatedPassword);
        user.setName(updatedPassword);

        user.setBio(updatedUser.getBio());
        user.setWebsite(updatedUser.getWebsite());
        user.setPhone(updatedUser.getPhone());
        user.setGender(updatedUser.getGender());

        return user;
    }
}
