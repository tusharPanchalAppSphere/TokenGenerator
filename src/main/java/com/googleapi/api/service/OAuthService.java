package com.googleapi.api.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class OAuthService {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private AuthorizationCodeFlow flow;

    // Change this IP address and port to your desired values
    private static final String CALLBACK_IP_ADDRESS = "tushar.com";
    private static final int CALLBACK_PORT = 8082;
    private static final String redirectUri2 = "http://" + CALLBACK_IP_ADDRESS + ":" + CALLBACK_PORT + "/callback";

    public OAuthService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                Collections.singletonList("https://www.googleapis.com/auth/spreadsheets"))
                .setAccessType("offline")
                .build();
    }

    public String getAuthorizationUrl() {
        return flow.newAuthorizationUrl().setRedirectUri(redirectUri2).build();
    }

    public String exchangeCodeForToken(String code) throws IOException {
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri2).execute();
        Credential credential = flow.createAndStoreCredential(response, null);
        return credential.getAccessToken();
    }
}
