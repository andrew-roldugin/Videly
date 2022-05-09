package ru.vsu.csf.group7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.firebase.auth.UserRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vsu.csf.group7.http.request.SignupRequest;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private String email, password;
    private String id;
    private List<? extends GrantedAuthority> grantedAuthorities;


    public UserDetailsImpl(String email, String password, List<? extends GrantedAuthority> grantedAuthorities) {
        this.email = email;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public UserDetailsImpl(SignupRequest signupRequest, List<GrantedAuthority> grantedAuthorities) {
        this(signupRequest.getEmail(), signupRequest.getPassword(), grantedAuthorities);
    }

    public UserDetailsImpl(SignupRequest signupRequest) {
        this(signupRequest, List.of(new SimpleGrantedAuthority(signupRequest.getRole().name())));
    }

    public UserDetailsImpl(User userByEmail) {
        this(userByEmail.getEmail(), userByEmail.getPassword(), userByEmail.getGrantedAuthorities());
        this.id = userByEmail.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return password;
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
