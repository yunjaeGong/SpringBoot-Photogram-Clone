package com.cos.photogramstart.configuration.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {
    User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 계정 권환 목록(admin, user..) 반환 - 권한이 여러 개 있으면 loop를 돌아야 하지만 지금으로서는 하나밖에 없다.
        Collection<GrantedAuthority> collection = new ArrayList<>();
        //TODO: 계정 권한 추가되면 아래 수정
        collection.add(() -> { return "ROLE_" + user.getRole(); }); // 권한이 하나만 남아 가능
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
