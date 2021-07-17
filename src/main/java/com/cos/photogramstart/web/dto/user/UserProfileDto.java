package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private User user;

    // 기본 페이지 정보
    private boolean pageOwnerState;
    private int imageCount;

    // 구독 정보 (프로필 상단)
    private boolean subscribedStatus;
    private int subscribeCount;

}
