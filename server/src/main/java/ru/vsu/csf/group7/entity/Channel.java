package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Data
@NoArgsConstructor
@Log4j2
public class Channel {
    private String id;

    private String name;

    private DocumentReference userRef;

    @Exclude
    private User user;

    private String avatarURL, headerURL, about;

    @Exclude
    private List<Video> videos;

    private boolean allowComments = true, allowRating = true;

    private boolean isDeleted = false;

    private Timestamp createdAt = Timestamp.now();

    public Channel(CreateChannelRequest request) {
        this.name = request.getName();
        this.avatarURL = request.getAvatarURL();
        this.about = request.getAbout();
        this.headerURL = request.getHeaderURL();
    }

    @Exclude
    private User getUser() {
        try {
            return this.user != null
                    ? this.user
                    : (this.user = userRef.get().get().toObject(User.class));
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Exclude
    public boolean isOwner() {
        User user = Objects.requireNonNull(getUser(), "Учетная запись не найдена");
        try {
            UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getId().equals(principal.getId());
        }catch (ClassCastException ignored){
            return false;
        }
    }

    @Exclude
    public boolean isActive() throws NullPointerException {
        Objects.requireNonNull(getUser(), "Учетная запись пользователя не найдена или удалена");
        return !this.isDeleted && user.userProfileIsActive();
    }
}
