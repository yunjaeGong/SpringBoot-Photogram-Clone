package com.cos.photogramstart.web.api;

import com.cos.photogramstart.configuration.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.handler.exception.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> postComment(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // 함수 파라미터로 dto 받기 위해 request body 이용
        Comment comment = new Comment();
        String content = commentDto.getContent();
        long imageId = commentDto.getImageId();

        try {
            comment = commentService.saveComment(content, imageId, principalDetails.getUser().getId());
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }

        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 작성 완료", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long commentId) {
        try {
            commentService.deleteComment(commentId);
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 완료", null), HttpStatus.OK);
    }
}
