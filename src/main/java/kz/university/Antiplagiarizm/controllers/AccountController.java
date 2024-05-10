package kz.university.Antiplagiarizm.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kz.university.Antiplagiarizm.domain.Account;
import kz.university.Antiplagiarizm.domain.dto.JwtAuthenticationResponse;
import kz.university.Antiplagiarizm.domain.dto.SignInRequest;
import kz.university.Antiplagiarizm.services.AccountService;
import kz.university.Antiplagiarizm.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AccountController {
    private final AccountService accountService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public Account signup(@RequestBody Account account){
        return accountService.create(account);
    }


    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        System.out.println("logout");
        return ResponseEntity.ok("Account logout");
    }
}
