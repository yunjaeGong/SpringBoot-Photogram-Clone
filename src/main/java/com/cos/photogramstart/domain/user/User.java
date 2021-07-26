package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements DomainValidationProperties{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db따라서
    private long id;

    @Column(nullable = false, length = USERNAME_LENGTH, unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = EMAIL_LENGTH)
    private String email;

    @Column(nullable = false)
    private String name;

    private String website;

    @Lob
    private String bio;

    private String phone;

    private String gender;

    private String profileImageUrl;

    private String role; // TODO: ENUM으로 수정

    // 양방향 매핑
    // user 테이블에 column 생성 x
    // user select 시 해당 user id로 등록된 image 가져오지 않음 - getImages 호출 시
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // db 세션 종료되면 이미지 가져올 수 없음 -> open-in-view: view단까지 db세션 유지
    @JsonIgnoreProperties({"user"}) // 내부 user 객체 무시
    private List<Image> images; // 양방향 매핑

    @CreationTimestamp
    private LocalDateTime createDate;


    /*@PrePersist // db insert 직전에 수행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }*/

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
                + website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
                + ", profileImageUrl=" + profileImageUrl + ", role=" + role +", createDate="
                + createDate + "]";
    }
}
