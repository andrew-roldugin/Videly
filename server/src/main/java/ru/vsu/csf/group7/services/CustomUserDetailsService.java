package ru.vsu.csf.group7.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String login) {
        final User user = new User();

//                userRepository.findUserByLogin(login)
//                .orElseThrow(() -> new UsernameNotFoundException(String.format(("Пользователь с логином %s не найден"), login)));

        return build(user);
    }

    public User loadUserById(Long id) {
        return null;
//        return build(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    private User build(User u) {
//        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(u.getRole().getRole().name()));
//        u.setGrantedAuthorities(grantedAuthorities);
        return u;
    }
}
