package kz.university.Antiplagiarizm.services;

import kz.university.Antiplagiarizm.domain.Account;
//import kz.university.Antiplagiarizm.config.AccountDetails;
import kz.university.Antiplagiarizm.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    public Account create(Account account){
        if(accountRepository.existsByEmail(account.getEmail()) && account.getEmail()!=null){
            throw new RuntimeException(account.getEmail() + " is exist or null");
        }
        if(accountRepository.existsByUsername(account.getUsername()) && account.getEmail()!=null){
            throw new RuntimeException(account.getUsername() + " is exist or null");
        }
        if(account.getPassword()==null){
            throw new RuntimeException(account.getPassword() + " must not be null");
        }

        account.setPassword(encoder.encode(account.getPassword()));
        account.setRole("ROLE_USER");
        return accountRepository.save(account);
    }

    public Account getByUsername(String username){
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public Account getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
//        return account.map(AccountDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }


}

