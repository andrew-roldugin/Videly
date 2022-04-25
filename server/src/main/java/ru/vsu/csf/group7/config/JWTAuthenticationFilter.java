package ru.vsu.csf.group7.config;

import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vsu.csf.group7.services.CustomUserDetailsService;
import ru.vsu.csf.group7.services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider provider;
    @Autowired
    private UserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String jwt = getJwtFromRequest(request);
            FirebaseToken firebaseToken;
            if (StringUtils.hasText(jwt) && (firebaseToken = provider.validateToken(jwt)) != null) {
                String email = provider.getUserLoginFromToken(firebaseToken);
                UserDetails user = service.findUserByEmail(email);
                if (user == null) return;
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(bearer) && bearer.startsWith(SecurityConstants.TOKEN_PREFIX))
            return bearer.split(" ")[1];

        return null;
    }
}
