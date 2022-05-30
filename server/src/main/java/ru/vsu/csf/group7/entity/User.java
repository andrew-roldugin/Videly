package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.firebase.auth.UserRecord;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.vsu.csf.group7.http.request.SignupRequest;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Data
@NoArgsConstructor
@Log4j2
public class User {

    private String id;
    private String email, password;
    private boolean isBanned = false, isDeleted = false;
    @Nullable
    private DocumentReference channelRef;
    @Exclude
    private Channel channel;
    private Timestamp createdAt = Timestamp.now();
    private ERole role = ERole.USER;

    public User(SignupRequest req, UserRecord userRecord) {
        this.password = req.getPassword();
        this.email = req.getEmail();
        this.setId(userRecord.getUid());
        this.setBanned(userRecord.isDisabled());
        this.setDeleted(false);
        this.role = req.getRole();
    }

    @Exclude
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.getRole().name()));
    }

    @Exclude
    public Channel getChannel() {
        try {
            return this.channel != null
                    ? this.channel
                    : (this.channel = channelRef.get().get().toObject(Channel.class));
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getLocalizedMessage());
        } catch (NullPointerException e) {
            log.warn("Пользователь не создал канал");
        }
        return null;
    }

    @Exclude
    public boolean userProfileIsActive() {
        return !(this.isBanned || this.isDeleted);
    }
}
