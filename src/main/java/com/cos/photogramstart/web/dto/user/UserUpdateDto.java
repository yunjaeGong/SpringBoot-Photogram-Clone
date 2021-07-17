package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.DomainValidationProperties;
import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
public class UserUpdateDto implements DomainValidationProperties {

    @NotBlank
    @Size(min = 2, max = USERNAME_LENGTH)
    private final String name; // 필수

    @NotBlank
    @Size(min = 2, max = PASSWORD_LENGTH)
    private final String password; // 필수

    private final String website;
    private final String bio;
    private final String phone;
    private final String gender;

    public User toEntity() {
        return User.builder()
                .username(name)
                .password(password)
                .email(website)
                .email(bio)
                .email(phone)
                .name(gender).build();
    }
}
