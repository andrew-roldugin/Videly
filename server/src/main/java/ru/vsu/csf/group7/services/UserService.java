package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.dto.UserDTO;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.User;
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

        UserDTO u = UserDTO.fromUserRecord(signupRequest, userRecord);

        dbFirestore
                .collection("users")
                .document(userRecord.getUid())
                .set(u);

        return u.getId();
    }

    public User getUserData(String login) throws ExecutionException, InterruptedException {
        QueryDocumentSnapshot queryDocumentSnapshot = findUser(login);

        User userByEmail = mapToUser(queryDocumentSnapshot);
        try {
            userByEmail.setChannel(((DocumentReference) Objects.requireNonNull(queryDocumentSnapshot.get("channelRef"))).get().get().toObject(Channel.class));
        } catch (NullPointerException e){
            throw new NotFoundException("Канал не найден");
        }
        return userByEmail;
    }

    private User mapToUser(QueryDocumentSnapshot queryDocumentSnapshot) {
        User u = new User();
        u.setId(queryDocumentSnapshot.getId());
        u.setRef(queryDocumentSnapshot.getReference());
        u.setEmail(queryDocumentSnapshot.getString("email"));
        u.setPassword(queryDocumentSnapshot.getString("password"));
        u.setEnabled(queryDocumentSnapshot.getBoolean("enabled"));
        ArrayList<Map<String, String>> grantedAuthorities = (ArrayList<Map<String, String>>) queryDocumentSnapshot.get("authorities");

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

    public void removeUser(String id) throws FirebaseAuthException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        FirebaseAuth.getInstance().deleteUser(id);

        dbFirestore
                .collection("users")
                .document(id)
                .delete();
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
}
