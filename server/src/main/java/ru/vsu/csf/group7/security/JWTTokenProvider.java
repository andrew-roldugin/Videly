package ru.vsu.csf.group7.security;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JWTTokenProvider {

    public String generateToken(User user, Long expTime, String secret) {
//        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + (expTime != null ? expTime : SecurityConstants.EXPIRATION_TIME));

//        String userId = Long.toString(user.getId());
//        Map<String, Object> claimsMap = new HashMap<>();
//        claimsMap.put("id", userId);
//        claimsMap.put("login", user.getLogin());
//        claimsMap.put("email", user.getEmail());
//        claimsMap.put("role", user.getRole().getRole().name());

        return Jwts.builder()
                .setId("userId")
                .addClaims(null /*claimsMap*/)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret != null ? secret : SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException |
                UnsupportedJwtException |
                MalformedJwtException |
                SignatureException | IllegalArgumentException e
        ) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String getUserLoginFromToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("login");
    }
}
