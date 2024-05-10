package kz.university.Antiplagiarizm.repo;

import kz.university.Antiplagiarizm.domain.Account;
import kz.university.Antiplagiarizm.domain.JsonResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository  extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
