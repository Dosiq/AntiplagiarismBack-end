package kz.university.Antiplagiarizm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.university.Antiplagiarizm.domain.deletion.Notifications;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookResponse {
    private ScannedDocument scannedDocument;
    private Results results;
    private Notifications notifications;
    private int status;
    private String developerPayload;
}
