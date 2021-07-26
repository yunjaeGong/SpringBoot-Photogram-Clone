package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Entity
public class Image { // N, 1 N개의 이미지는 1명이 등록 가능, 1개의 이미지는 여러명이 등록 불가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String caption; // 사진 설명

    private String postImageUrl; // 사진은 서버 특정 폴더에 저장이 되는데, db에는 경로만 저장

    @JsonIgnoreProperties({"images"}) // story에서 이미지 가져 올 때 user 내 images는 필요 없음.
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER) // N-1의 관계
    private User user; // 업로드 유저, 1,1

    // TODO: 이미지 좋아요
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image") // fk image테이블에
    private List<Likes> likes; // likes getter 호출하면 가져옴 - Lazy loading

    @OrderBy(value = "createDate DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    @Transient // DB에 Column 생성 x
    private boolean likeState = false;

    @Transient
    private int likeCount = 0;

    @CreationTimestamp
    private LocalDateTime createDate;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", postImageUrl='" + postImageUrl + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
