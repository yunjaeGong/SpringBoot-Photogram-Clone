package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    @Modifying
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES (:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(long fromUserId, long toUserId); // (변경된 행의 개수) / -1

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(long fromUserId, long toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
    int mSubscribeState(long pageUserId, long principalId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
    int mSubscribeCount(long pageUserId);

    // id, username, profileImgUrl, 구독중인 유저인지, 동일 유저인지
    // u
    @Query(value = "select u.ID as id,u.PROFILEIMAGEURL as profileImageUrl ,u.USERNAME, s.TOUSERID, " +
            "case when (select true from SUBSCRIBE where FROMUSERID = :principalId and s.TOUSERID = TOUSERID) then 1 else 0 end as subcribeState," +
            "case when (s.TOUSERID = :principalId) then 1 else 0 end as equalUserState" +
            "from USER u inner join SUBSCRIBE s on u.id = s.FROMUSERID where s.FROMUSERID = :pageUserId", nativeQuery = true)
    List<SubscribeDto> mSubscribeList(long principalId, long pageUserId);

}
