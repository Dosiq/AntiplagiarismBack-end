package kz.university.Antiplagiarizm.repo;

import kz.university.Antiplagiarizm.domain.ScannedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScannedDocumentRepository extends JpaRepository<ScannedDocument, Long> {
}
