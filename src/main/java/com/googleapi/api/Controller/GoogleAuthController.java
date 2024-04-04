package com.googleapi.api.Controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@RestController
public class GoogleAuthController {

    private final String clientId = "482980499308-am2g81er3h2almngdjdc2cmi27i7ou0o.apps.googleusercontent.com";
    private final String clientSecret = "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";
    private final String redirectUri = "https://tokengenerator-jw7r.onrender.com:8082/driveCallback";
    private final String authorizationUrl = "https://accounts.google.com/o/oauth2/auth";
    private final String tokenUrl = "https://oauth2.googleapis.com/token";
    private final String scope = "https://www.googleapis.com/auth/drive";



    @GetMapping("/driveTokens")
    public String getAccessToken() {
        try {
            // Load service account credentials JSON file for Google Sheets API
            InputStream serviceAccountStream = getClass().getResourceAsStream("/credentialsDrive.json");
            GoogleCredential credential = GoogleCredential.fromStream(serviceAccountStream)
                    .createScoped(Collections.singleton(scope));

            // Generate access token for Google Sheets API
            credential.refreshToken();
            String accessToken = credential.getAccessToken();

            return  accessToken;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to generate access token for Google Sheets API";
        }
    }
//    @GetMapping("/driveAuthorize")
//    public ResponseEntity<String> authorize() {
//        // Redirect user to Google's authorization endpoint
//        String url = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s",
//                authorizationUrl, clientId, redirectUri, scope);
//        return ResponseEntity.status(HttpStatus.FOUND).header("Location", url).build();
//    }

//    @GetMapping("/driveCallback")
//    public String handleCallback(@RequestParam("code") String code) {
//        try {
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(tokenUrl))
//                    .header("Content-Type", "application/x-www-form-urlencoded")
//                    .POST(HttpRequest.BodyPublishers.ofString(
//                            "code=" + code +
//                                    "&client_id=" + clientId +
//                                    "&client_secret=" + clientSecret +
//                                    "&redirect_uri=" + redirectUri +
//                                    "&grant_type=authorization_code"))
//                    .build();
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            // Handle response to get access token
//            if (response.statusCode() == HttpStatus.OK.value()) {
//                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
//                String accessToken = json.get("access_token").getAsString();
//                // Store or use the access token as needed
//                return "Access token: " + accessToken;
//            } else {
//                return "Failed to get access token";
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return "Error occurred during authorization";
//        }
//    }
}

