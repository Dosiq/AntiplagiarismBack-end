package kz.university.Antiplagiarizm.services;

import kz.university.Antiplagiarizm.domain.Account;
import kz.university.Antiplagiarizm.domain.dto.JwtAuthenticationResponse;
import kz.university.Antiplagiarizm.domain.dto.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
//    public JwtAuthenticationResponse signUp(Account request) {
//
//        var user = Account.builder()
//                .email(request.getEmail())
//                .username(request.getUsername())
//                .password(request.getPassword())
//                        .build();
//
//        accountService.create(user);
//
//        var jwt = jwtService.generateToken(user);
//        return new JwtAuthenticationResponse(jwt);
//    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = accountService.userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse refresh() {

        var user = accountService.userDetailsService()
                .loadUserByUsername(accountService.getCurrentUser().getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

}
