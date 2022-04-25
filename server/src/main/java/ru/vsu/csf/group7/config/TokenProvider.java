package ru.vsu.csf.group7.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.internal.FirebaseTokenFactory;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1.ListenResponse;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.protocol.HTTP;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vsu.csf.group7.entity.Key;
import ru.vsu.csf.group7.entity.User;

import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class TokenProvider {

    public String generateToken(User user) throws FirebaseAuthException {
        String userId = user.getId();
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("email", user.getEmail());
        //additionalClaims.put("role", user.getAuthorities());

        return FirebaseAuth.getInstance().createCustomToken(userId, additionalClaims);
    }


    private Object getKey(){
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";
//
//        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
//        if(response.getStatusCode() == HttpStatus.OK) {
//            response.getBody();
//            try {
//                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//                InputStream in = new InputStreamReader(response.getBody());
//                X509Certificate cert = (X509Certificate)certFactory.generateCertificate(in);
//                Jwts.parser()
//                        .setSigningKey(cert.getPublicKey())
//                        .parseClaimsJws("token");
//            } catch (CertificateException | IOException e) {
//                e.printStackTrace();
//            }
//        }
            return null;
    }

    public FirebaseToken validateToken(String token) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(token, true);
//        try {
//            return FirebaseAuth.getInstance().verifyIdToken(token, true);
//        } catch (FirebaseAuthException e) {
//            log.error(e.getMessage());
//        }
//        return null;



//        getKey();
//        return null;

//        try {
//            Key key = new sun.security.x509.X509Key();
//            Jwts.parser()
//                    .setSigningKey(key)
//                    .parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException |
//                UnsupportedJwtException |
//                MalformedJwtException |
//                SignatureException | IllegalArgumentException e
//        ) {
//            log.error(e.getMessage());
//            return false;
//        }

    }

    public String getUserLoginFromToken(FirebaseToken firebaseToken) {
        return firebaseToken.getEmail();
    }
}
