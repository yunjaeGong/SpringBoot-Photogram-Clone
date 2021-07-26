package com.cos.photogramstart.domain.likes;


import com.cos.photogramstart.domain.image.Image;
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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"} // 한 이미지가 한 이미지를 여러 번 좋아요 불가능
                )
        }
)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 어떤 이미지를 누가 좋아했는지

    @JoinColumn(name="imageId") // fk명 imageId
    @ManyToOne // fetch - Eager
    private Image image; // 하나의 이미지는 여러번 좋아요 가능 1:N, 하나의 좋아요를 여러 이미지가 동시에 - x

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name="userId")
    @ManyToOne
    private User user; // 한 유저는 여러 번 좋아요 가능 1:N

    @CreationTimestamp
    private LocalDateTime createDate;
}

