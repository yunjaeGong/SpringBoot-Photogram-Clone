package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.exception.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment saveComment(String content, long imageId, long userId) {
        // Pseudo Object
        Image image = new Image();
        User userEntity = userRepository.findById(userId).orElseThrow(()-> {
            throw new CustomApiException("해당 유저 정보를 찾을 수 없습니다.");
        });
        User user = new User();
        user.setName(userEntity.getName()); // 필요한 유저 정보만

        image.setId(imageId);
        user.setId(userId);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(user);

         return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

}
