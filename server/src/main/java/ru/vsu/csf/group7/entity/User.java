package ru.vsu.csf.group7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vsu.csf.group7.http.request.SignupRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Setter
public class User implements UserDetails {

    private String email, password;

    @JsonIgnore
    private List<GrantedAuthority> grantedAuthorities;

    public User(String email, String password, List<GrantedAuthority> grantedAuthorities) {
        this.email = email;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public User(SignupRequest signupRequest, List<GrantedAuthority> grantedAuthorities) {
        this(signupRequest.getEmail(), signupRequest.getPassword(), grantedAuthorities);
    }

    public User(SignupRequest signupRequest) {
        this(signupRequest.getEmail(),
                signupRequest.getPassword(),
                List.of(new SimpleGrantedAuthority(signupRequest.getRole().name()))
        );
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
