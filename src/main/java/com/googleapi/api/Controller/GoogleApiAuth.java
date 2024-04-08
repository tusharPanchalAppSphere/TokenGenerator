package com.googleapi.api.Controller;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class GoogleApiAuth {

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = getCredentials(HTTP_TRANSPORT);

        System.out.println(credentials.getRefreshToken());
    }

    private static Credential getCredentials(final NetHttpTransport transport) throws IOException {
        String scope = "https://www.googleapis.com/auth/spreadsheets";
        AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport,
                GsonFactory.getDefaultInstance(),
                "1004637461184-eq1nlfkc5n2tk85ha6b5v54jl2aqdgmm.apps.googleusercontent.com",    // Client ID
                "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",                                          // Client Secret
                Arrays.asList(scope))
                .setAccessType("offline")
                .build();
        return new Credential(null);
    }

}
