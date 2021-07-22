package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select * from image where userid in (select toUserId from subscribe where FROMUSERID = :principalId)", nativeQuery = true)
    Page<Image> mStoryPageable(long principalId, Pageable pageable);

    @Query(value = "select * from image where userid in (select toUserId from subscribe where FROMUSERID = :principalId)", nativeQuery = true)
    List<Image> mStory(long principalId);
}
