package kz.university.Antiplagiarizm.repo;

import kz.university.Antiplagiarizm.domain.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, String> {
}
