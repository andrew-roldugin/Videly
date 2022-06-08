package ru.vsu.csf.group7.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TokenProvider {

    public String generateToken(String userId) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().createCustomToken(userId);
    }


//    private Object getKey() {
////        RestTemplate restTemplate = new RestTemplate();
////        String url = "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";
////
////        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
////        if(response.getStatusCode() == HttpStatus.OK) {
////            response.getBody();
////            try {
////                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
////                InputStream in = new InputStreamReader(response.getBody());
////                X509Certificate cert = (X509Certificate)certFactory.generateCertificate(in);
////                Jwts.parser()
////                        .setSigningKey(cert.getPublicKey())
////                        .parseClaimsJws("token");
////            } catch (CertificateException | IOException e) {
////                e.printStackTrace();
////            }
////        }
//            return null;
//    }

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

    public String getUserIdFromToken(FirebaseToken firebaseToken) {
        return firebaseToken.getUid();
    }


    public String getUserLoginFromToken(FirebaseToken firebaseToken) {
        return firebaseToken.getEmail();
    }
}
