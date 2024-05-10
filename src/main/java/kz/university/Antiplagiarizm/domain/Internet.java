package kz.university.Antiplagiarizm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.boot.Metadata;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Internet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String url;
    private String title;
    private String introduction;
    private int matchedWords;
    private int identicalWords;
    private int similarWords;
    private int paraphrasedWords;
    private int totalWords;
    @ElementCollection
    private List<String> tags;
}