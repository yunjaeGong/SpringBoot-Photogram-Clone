package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public void like(long principalId, long imageId) {
        likesRepository.mLike(principalId, imageId);
    }

    @Transactional
    public void unlike(long principalId, long imageId) {
        likesRepository.mUnLike(principalId, imageId);
    }

}
