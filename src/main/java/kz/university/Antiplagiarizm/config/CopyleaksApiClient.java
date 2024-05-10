package kz.university.Antiplagiarizm.config;

import classes.Copyleaks;
import lombok.Data;
import models.exceptions.CommandException;
import models.exceptions.RateLimitException;
import models.exceptions.UnderMaintenanceException;
import models.response.CopyleaksAuthToken;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Data
public class CopyleaksApiClient {

    private static final String EMAIL_ADDRESS = "muchomusica02@gmail.com";
    private static final String KEY = "8b476eb6-d780-42bf-b523-94ae335c5ba3";
    public static CopyleaksAuthToken token;

    @Bean
    public static void login(){
        try {
            token = Copyleaks.login(EMAIL_ADDRESS, KEY);
        } catch (ExecutionException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (UnderMaintenanceException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (RateLimitException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        } catch (CommandException e) {
            System.out.println(e.getMessage() + "\n");
            e.printStackTrace();
            return;
        }

        System.out.println("Logged successfully!\nToken:");
        System.out.print(token.getAccessToken().toString());
    }
}
