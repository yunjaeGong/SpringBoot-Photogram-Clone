package com.cos.photogramstart.web.api;

import com.cos.photogramstart.configuration.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @Value("${story.pageable}")
    private boolean ifPageable;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                        @PageableDefault(size = 3, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        if (ifPageable) {
            Page<Image> imageList = imageService.pageableImageStory(principalDetails.getUser().getId(), pageable);
            return new ResponseEntity<>(new CMRespDto<>(1,"성공", imageList), HttpStatus.OK);

        } else {
            List<Image> imageList = imageService.imageStory(principalDetails.getUser().getId());
            return new ResponseEntity<>(new CMRespDto<>(1,"성공", imageList), HttpStatus.OK);
        }
    }
    
    @PostMapping("/api/{imageId}/like")
    public ResponseEntity<?> like(@PathVariable long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.like(principalDetails.getUser().getId(), imageId);
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공.", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/{imageId}/unlike")
    public ResponseEntity<?> unlike(@PathVariable long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.unlike(principalDetails.getUser().getId(), imageId);
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요를 취소 성공.", null), HttpStatus.OK);
    }

}