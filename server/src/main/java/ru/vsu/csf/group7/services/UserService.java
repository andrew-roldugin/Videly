package ru.vsu.csf.group7.services;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.entity.UserDetailsImpl;
import ru.vsu.csf.group7.exceptions.NotFoundException;
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

//        UserDTO u = UserDTO.fromUserRecord(signupRequest, userRecord);

        dbFirestore
                .collection("users")
                .document(userRecord.getUid())
                .set(new User(signupRequest, userRecord));

        return userRecord.getUid();
    }

    public User getUserData(String login) throws ExecutionException, InterruptedException, UserNotFoundException, NotFoundException {
        QueryDocumentSnapshot queryDocumentSnapshot = findUser(login);

        User userByEmail = mapToUser(queryDocumentSnapshot);
        if (userByEmail.isDeleted())
            throw new UserNotFoundException();

        try {
            Channel channel = ((DocumentReference) Objects.requireNonNull(queryDocumentSnapshot.get("channelRef")))
                    .get()
                    .get()
                    .toObject(Channel.class);

            userByEmail.setChannel(channel);
        } catch (NullPointerException | ClassCastException e) {
//            throw new NotFoundException("Канал не найден");
        }
        return userByEmail;
    }

    private User mapToUser(DocumentSnapshot queryDocumentSnapshot) {
        User u = new User();
        u.setId(queryDocumentSnapshot.getId());
//        u.setRef(queryDocumentSnapshot.getReference());
        u.setEmail(queryDocumentSnapshot.getString("email"));
        u.setPassword(queryDocumentSnapshot.getString("password"));
        u.setBanned(Boolean.TRUE.equals(queryDocumentSnapshot.getBoolean("banned")));
        u.setDeleted(Boolean.TRUE.equals(queryDocumentSnapshot.getBoolean("deleted")));
        ArrayList<Map<String, String>> grantedAuthorities = (ArrayList<Map<String, String>>) queryDocumentSnapshot.get("grantedAuthorities");

        assert grantedAuthorities != null;
        List<SimpleGrantedAuthority> collect = grantedAuthorities.stream()
                .map(Map::values)
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .toList();
        u.setGrantedAuthorities(collect);

//        HashMap<String, Object> m = new HashMap<String, Object>();
//        m.put("userRef", queryDocumentSnapshot.getReference());

//        dbFirestore
//                .collection("users")
//                .document("UcIfOZeQ5mYlAYCiNvA5loIUwwz2")
//                .collection("channel")
//                .document("reA8xKBJxUUsO2E5s9Ys")
//                .update(m);

//        List<QueryDocumentSnapshot> test = dbFirestore
//                .collection("users")
//                .document("UcIfOZeQ5mYlAYCiNvA5loIUwwz2")
//                .collection("channel")
//                .get()
//                .get()
//                .getDocuments();

//        var userRef = dbFirestore.collection("users")
//                .document("UcIfOZeQ5mYlAYCiNvA5loIUwwz2")
//                .collection("channel")
//                .document("reA8xKBJxUUsO2E5s9Ys")
//                .get(FieldMask.of("userRef"))
//                .get();
//
//        DocumentSnapshot user = ((DocumentReference) userRef.get("userRef"))
//                .get()
//                .get();

//        QuerySnapshot documentSnapshots = dbFirestore
//                .collection(userRef1)
//                .get()
//                .get();

//        List<QueryDocumentSnapshot> test = dbFirestore
//                .collection(userRef)
//                .document("UcIfOZeQ5mYlAYCiNvA5loIUwwz2")
//                .collection("channel")
//                .get()
//                .get()
//                .getDocuments();

        return u;
    }

    private QueryDocumentSnapshot findUser(String login) throws ExecutionException, InterruptedException, UserNotFoundException {
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
        return queryDocumentSnapshot;
    }

    public User findUserByEmail(String login) throws InterruptedException, ExecutionException {
        QueryDocumentSnapshot queryDocumentSnapshot = findUser(login);
        return mapToUser(queryDocumentSnapshot);
    }

    public void removeUser(String id, boolean fullDelete) throws FirebaseAuthException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        DocumentReference documentReference = dbFirestore
                .collection("users")
                .document(id);
        if (fullDelete) {
            dbFirestore.recursiveDelete(documentReference);
            FirebaseAuth.getInstance().deleteUser(id);
        } else
            documentReference.update(Map.of("deleted", true));
    }

    public void updateUserById(UpdateUserRequest request, String userId) throws FirebaseAuthException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        String password = request.getPassword();
        FirebaseAuth.getInstance().getUser(userId).updateRequest().setPassword(password);
        dbFirestore
                .collection("users")
                .document(userId)
                .update(Map.of("password", password));
    }

    public DocumentReference getUserRef() {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String userId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return dbFirestore.collection("users").document(userId);
    }

    public void banUser(String userId, boolean banned) {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Map<String, Object> extraInfo = new HashMap<>();
        extraInfo.put("bannedBy", getUserRef());
        extraInfo.put("reason", "");
        extraInfo.put("timestamp", Timestamp.now());

        dbFirestore.collection("users")
                .document(userId)
                .update(Map.of("banned", banned, "extra", extraInfo));
    }

    public boolean userProfileIsActive(DocumentReference userRef) throws ExecutionException, InterruptedException {
        User user = mapToUser(userRef.get().get());
        return !(Objects.requireNonNull(user).isBanned() || user.isDeleted());
    }
}
