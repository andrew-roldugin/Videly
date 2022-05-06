package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.UserDetailsImpl;

import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        try {
            DocumentSnapshot documentSnapshot = dbFirestore
                    .collection("users")
                    .document(FirebaseAuth.getInstance().getUserByEmail(email).getUid())
                    .get()
                    .get();
            return documentSnapshot.toObject(UserDetailsImpl.class);
        } catch (InterruptedException | ExecutionException | FirebaseAuthException e) {
           log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public UserDetailsImpl loadUserById(Long id) {
        return null;
//        return build(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

//    private User build(UserRecord u) {
//        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(u.get));
//        u.setGrantedAuthorities(grantedAuthorities);
//        return u;
//    }
}
