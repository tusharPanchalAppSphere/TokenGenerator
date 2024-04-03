package com.googleapi.api.Controller;

import com.googleapi.api.service.OAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.event.IIOReadProgressListener;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

@RestController
@CrossOrigin(origins = "*")
public class OAuthController {

    @Autowired
    private OAuthService oauthService;

    @GetMapping("/tokens")
    public String authorize(HttpServletResponse response) {
        String authorizationUrl = oauthService.getAuthorizationUrl();
        System.out.println("Authorization URL: " + authorizationUrl);
        return authorizationUrl;
    }
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, HttpServletRequest request) {
        try {
            System.out.println("REACHED " +request.getHeader("User-Agent"));
            String accessToken = oauthService.exchangeCodeForToken(code);
            return "Access token: " + accessToken;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
