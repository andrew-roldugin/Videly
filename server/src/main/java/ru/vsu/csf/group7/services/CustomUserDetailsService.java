package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String login) {
            Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            return dbFirestore
                    .collection("users")
                    .document(FirebaseAuth.getInstance().getUserByEmail(login).getUid())
                    .get()
                    .get()
                    .toObject(User.class);
        } catch (InterruptedException | ExecutionException | FirebaseAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User loadUserById(Long id) {
        return null;
//        return build(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

//    private User build(UserRecord u) {
//        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(u.get));
//        u.setGrantedAuthorities(grantedAuthorities);
//        return u;
//    }
}
