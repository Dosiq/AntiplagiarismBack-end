package kz.university.Antiplagiarizm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class JsonResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne()
    @JsonIgnore
    private Account account;
    @JsonProperty("scannedDocument")
    @OneToOne
    private ScannedDocument scannedDocument;
    @OneToOne()
    private Results results;
    private int status;
    private String developerPayload;
    @OneToOne
    private Text text;
}
