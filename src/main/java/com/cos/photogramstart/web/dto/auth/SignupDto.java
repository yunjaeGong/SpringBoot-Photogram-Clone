package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.DomainValidationProperties;
import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
public class SignupDto implements DomainValidationProperties{
    @NotBlank
    @Size(min = 2, max = USERNAME_LENGTH)
    private String username;

    @NotBlank
    @Size(min = 8, max = PASSWORD_LENGTH)
    private String password;

    @NotBlank
    @Size(min = 1, max = EMAIL_LENGTH)
    private String email;

    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name).build();
    }
}
