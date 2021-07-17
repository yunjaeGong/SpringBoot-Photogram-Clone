package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void subscribe(long fromUserId, long toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (CustomApiException e) {
            throw new CustomApiException("이미 구독이 완료되었습니다.");
        }
    }

    @Transactional
    public void unSubscribe(long fromUserId, long toUserId) {
        try {
            subscribeRepository.mUnSubscribe(fromUserId, toUserId);
        } catch (CustomApiException e) {
            throw new CustomApiException("구독 취소할 유저가 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<SubscribeDto> subscribeList(long principalId, long pageUserId) {
        List<SubscribeDto> subscribeList = new ArrayList<>();
        subscribeRepository.mSubscribeList(principalId, pageUserId);
        return subscribeList;
    }
}
