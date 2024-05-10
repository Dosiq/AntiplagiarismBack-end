package kz.university.Antiplagiarizm.repo;

import kz.university.Antiplagiarizm.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
