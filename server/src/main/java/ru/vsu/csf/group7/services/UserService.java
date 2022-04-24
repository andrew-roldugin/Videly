package ru.vsu.csf.group7.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.http.request.SignupRequest;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    public String createUser(SignupRequest signupRequest) throws FirebaseAuthException, ExecutionException, InterruptedException {
        UserRecord.CreateRequest req = new UserRecord.CreateRequest()
                .setEmail(signupRequest.getEmail())
                .setPassword(signupRequest.getPassword());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(req);
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore
                .collection("users")
                .document(userRecord.getUid()).set(new User(signupRequest));
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
}
