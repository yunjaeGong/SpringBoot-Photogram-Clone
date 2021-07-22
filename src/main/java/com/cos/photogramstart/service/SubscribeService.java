package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager entityManager; // JpaRepository의 superclass

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

        // id, username, profileImgUrl, principalId가 구독중인 유저인지, pageUser가 구독하는 유저가 principal과 동일 유저인지

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.ID, u.USERNAME, u.PROFILEIMAGEURL, ");
        sb.append("CASE WHEN (SELECT 1 FROM SUBSCRIBE WHERE FROMUSERID = ? AND TOUSERID = u.ID) THEN 1 ELSE 0 END AS subscribeState, ");
        sb.append("CASE WHEN (u.ID = ?) THEN 1 ELSE 0 END AS equalUserState ");
        sb.append("FROM USER u INNER JOIN SUBSCRIBE s ON u.ID = s.TOUSERID ");
        sb.append("WHERE s.FROMUSERID = ?");

        // 쿼리 완성
        Query query = entityManager.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        // 쿼리 실행
        JpaResultMapper result = new JpaResultMapper();
        // MessageConverter 지원 x qlrm 혹은 @SqlResultSetMapping 으로 mapping 처리 필요
        return result.list(query, SubscribeDto.class);
    }
}
