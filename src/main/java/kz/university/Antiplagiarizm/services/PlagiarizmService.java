package kz.university.Antiplagiarizm.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.university.Antiplagiarizm.config.CopyleaksApiClient;
import kz.university.Antiplagiarizm.domain.*;
import kz.university.Antiplagiarizm.repo.*;
import kz.university.Antiplagiarizm.repo.JsonResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@CrossOrigin
public class PlagiarizmService {

    private final JsonResponseRepository antiplagiarizmRepository;
    private final ResultsRepository resultsRepository;
    private final ScoreRepository scoreRepository;
    private final InternetRepository internetRepository;
    private final ScannedDocumentRepository scannedDocumentRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final JsonResponseRepository jsonResponseRepository;
    private final TextRepository textRepository;

    public void submitText(String text) throws IOException, InterruptedException {
        String timestamp = Long.toString(Instant.now().toEpochMilli());
        String username = accountService.getCurrentUser().getUsername();
        String accessToken = CopyleaksApiClient.token.getAccessToken();
        String base64Content = Base64.getEncoder().encodeToString(text.getBytes());

        Text textToSave = new Text(timestamp + "-" + username, text);
        textRepository.save(textToSave);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.copyleaks.com/v3/scans/submit/file/" + timestamp + "-" + username ))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method("PUT", HttpRequest.BodyPublishers.ofString("{\n" +
                        "  \"base64\": \"" + base64Content + "\",\n" +
                        "  \"filename\": \"file.txt\",\n" +
                        "  \"properties\": {\n" +
                        "    \"webhooks\": {\n" +
                        "      \"status\": \"https://chubby-buses-play.loca.lt/webhook/{STATUS}\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    public ResponseEntity parseJson(String jsonString) throws JsonProcessingException{

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonResponse jsonResponse = objectMapper.readValue(jsonString, JsonResponse.class);

        scannedDocumentRepository.save(jsonResponse.getScannedDocument());
        List<Internet> tempInternet = jsonResponse.getResults().getInternet();
        for (Internet internet : tempInternet) {
            if (internet.getId() == null) {
                internetRepository.save(internet);
            }
        }
        scoreRepository.save(jsonResponse.getResults().getScore());
        resultsRepository.save(jsonResponse.getResults());

        String[] parts = jsonResponse.getScannedDocument().getScanId().split("-");
        Account account = accountRepository.findByUsername(parts[1]).orElse(null);
        jsonResponse.setAccount(account);
        jsonResponse.setText(textRepository.findById(jsonResponse.getScannedDocument().getScanId()).orElse(null));
        jsonResponseRepository.save(jsonResponse);

        return ResponseEntity.ok("Account was updated");
    }



    public List<JsonResponse> getAll(){
        String username = accountService.getCurrentUser().getUsername();
        List<JsonResponse> responses = accountRepository.findByUsername(username).get().getJsonResponses();
        return responses;
    }

    public ResponseEntity<String> deleteJson(Long id){
        antiplagiarizmRepository.deleteById(id);
        return ResponseEntity.ok(id + " - was deleted");
    }

}
