package kz.university.Antiplagiarizm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ScannedDocument {
    @Id
    private String scanId;
    private int totalWords;
    private int totalExcluded;
    private int credits;
    private int expectedCredits;
    private String creationTime;
}
