package kz.university.Antiplagiarizm.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import kz.university.Antiplagiarizm.domain.JsonResponse;
import kz.university.Antiplagiarizm.services.PlagiarizmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class FileSendController {

    private final PlagiarizmService plagiarizmService;

    @PostMapping("/submit")
    public void submit(@RequestBody String text) throws IOException, InterruptedException {
        System.out.println(text);
        plagiarizmService.submitText(text);
    }

    @PostMapping("/webhook/{STATUS}")
    public ResponseEntity<String> handleWebhook(@PathVariable("STATUS") String status, @RequestBody String requestBody) {
        System.out.println("Received webhook request with status: " + status);
        System.out.println("Request body: " + requestBody);
        System.out.println("answer was taken");

        try {
            plagiarizmService.parseJson(requestBody);
            System.out.println(requestBody);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public List<JsonResponse> getAll(){

        return plagiarizmService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJsonResponse(@PathVariable Long id){
        return plagiarizmService.deleteJson(id);
    }
}
