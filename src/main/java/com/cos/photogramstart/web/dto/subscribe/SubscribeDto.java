package com.cos.photogramstart.web.dto.subscribe;

import lombok.*;

import java.math.BigInteger;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeDto {
    private BigInteger id; // toUserId로 이용
    private String username;
    private String profileImageUrl;
    private Integer subscribeState; // 구독한 상태인지
    private Integer equalUserState; // pageUserId가 구독중인 유저가 principal과 동일인인지
}
