package ru.vsu.csf.group7;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Log4j2
public class FirebaseInit {

    public static void initialize() {
        try {
            InputStream serviceAccount = FirebaseInit.class
                    .getClassLoader()
                    .getResourceAsStream("serviceAccount.json");

            assert serviceAccount != null;
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            log.info("Firebase успешно проинициализирована");
        } catch (Exception e) {
            log.error("При инициализации Firebase произошла ошибка " + e);
        }
    }
}
