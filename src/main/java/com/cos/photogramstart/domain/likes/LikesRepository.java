package com.cos.photogramstart.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "insert into LIKES (imageId, userId, createDate) values ( :imageId, :principalId , now())", nativeQuery = true)
    int mLike(long principalId, long imageId);

    @Modifying
    @Query(value = "delete from LIKES where userId = :principalId and imageId = :imageId", nativeQuery = true)
    int mUnLike(long principalId, long imageId);

    @Query(value = "select count(*) from LIKES where imageId = :imageId", nativeQuery = true)
    int mLikeCount(long imageId);
}
