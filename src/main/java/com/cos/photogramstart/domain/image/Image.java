package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @JoinColumn(name = "userId")
    @ManyToOne // N-1의 관계
    private User user; // 업로드 유저, 1,1

    @CreationTimestamp
    private LocalDateTime createDate;

    // TODO: 이미지 좋아요 누른 유저
    // TODO: 댓글


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
