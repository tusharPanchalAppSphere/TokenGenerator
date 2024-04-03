//package com.googleapi.api.Controller;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//import java.io.File;
//
//
//
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.*;
//
//
//@Controller
//public class GoogleAuthController {
//
//    @Value("${google.client.id}")
//    private String clientId;
//
//    @Value("${google.client.secret}")
//    private String clientSecret;
//
//    @Value("${google.redirect.uri}")
//    private String redirectUri;
//
//    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//  private static final List<String> SCOPES = new ArrayList<>();
//
//    @GetMapping("/tokens")
//    public String getAccessToken() {
//        SCOPES.add("https://www.googleapis.com/auth/spreadsheets");
//        try {
//            // Load client secrets
//            InputStream in = getClass().getResourceAsStream("/credentials.json");
//            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//            // Build flow and trigger user authorization request
//            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
//                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
//                    .setAccessType("offline")
//                    .build();
//
//            // Exchange authorization code for access token
//            String accessToken = flow.loadCredential("user_id").getAccessToken();
//
//            return "Access Token: " + accessToken;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to generate access token";
//        }
//    }
//}
