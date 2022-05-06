package ru.vsu.csf.group7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.auth.UserRecord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.vsu.csf.group7.http.request.SignupRequest;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private String id;
    private String email, password;
    private boolean isBanned = false, isDeleted = false;
    private Channel channel;
    private DocumentReference ref, channelRef;
    private Timestamp creationTS = Timestamp.now();


    /*
    private String id;
    private String email, password;
    private boolean isBanned = false, isDeleted = false;

    private DocumentReference ref, channelRef;
    private Channel channel;
    private List<Subscription> subscriptions;
    private Timestamp creationTS = Timestamp.now();

     */

    private List<? extends GrantedAuthority> grantedAuthorities;

    public User(SignupRequest req, UserRecord userRecord){
        this.password = req.getPassword();
        this.email = req.getEmail();
        this.setId(userRecord.getUid());
        this.setBanned(userRecord.isDisabled());
        this.setDeleted(false);
        this.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(req.getRole().name()));
    }
}
