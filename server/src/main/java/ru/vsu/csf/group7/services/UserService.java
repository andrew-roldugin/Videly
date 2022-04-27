package ru.vsu.csf.group7.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.exceptions.UserNotFoundException;
import ru.vsu.csf.group7.http.request.SignupRequest;
import ru.vsu.csf.group7.http.request.UpdateUserRequest;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    public String createUser(SignupRequest signupRequest) throws FirebaseAuthException, ExecutionException, InterruptedException {
        UserRecord.CreateRequest req = new UserRecord.CreateRequest()
                .setEmail(signupRequest.getEmail())
                .setPassword(signupRequest.getPassword());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(req);
        Firestore dbFirestore = FirestoreClient.getFirestore();

        UserDTO u = UserDTO.builder()
                .id(userRecord.getUid())
                .email(userRecord.getEmail())
                .enabled(!userRecord.isDisabled())
                .password(signupRequest.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(signupRequest.getRole().name())))
                .build();

        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore
                .collection("users")
                .document(userRecord.getUid()).set(u);
        return u.getId();
    }

    public User findUserByEmail(String login) throws ExecutionException, InterruptedException, UserNotFoundException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        List<QueryDocumentSnapshot> documents = dbFirestore
                .collection("users")
                .whereEqualTo(FieldPath.of("email"), login)
                .get()
                .get()
                .getDocuments();
        if (documents.isEmpty())
            throw new UserNotFoundException("логином", login);

        QueryDocumentSnapshot queryDocumentSnapshot = documents.get(0);
        User u = new User();
        u.setId(queryDocumentSnapshot.getId());
        u.setEmail(queryDocumentSnapshot.getString("email"));
        u.setPassword(queryDocumentSnapshot.getString("password"));
        u.setEnabled(queryDocumentSnapshot.getBoolean("enabled"));
        ArrayList<Map<String, String >> grantedAuthorities = (ArrayList<Map<String, String >>) queryDocumentSnapshot.get("authorities");
        List<SimpleGrantedAuthority> collect = grantedAuthorities.stream()
                .map(Map::values)
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .toList();
        u.setGrantedAuthorities(collect);

        return u; //FirebaseAuth.getInstance().getUserByEmail(login);
    }

    public void removeUser(String id) {

    }

    public User updateUserById(UpdateUserRequest req, String userId) {
        return null;
    }
}
