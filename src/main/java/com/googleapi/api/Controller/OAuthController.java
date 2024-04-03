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
    public void authorize(HttpServletResponse response) {
        String authorizationUrl = oauthService.getAuthorizationUrl();
        System.out.println("Authorization URL: " + authorizationUrl);
        //return "redirect:" + authorizationUrl;
//        try {
//            response.sendRedirect(authorizationUrl);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        if(Desktop.isDesktopSupported()){
//            try {
//                URI uri = new URI(authorizationUrl);
//                Desktop desktop = Desktop.getDesktop();
//                desktop.browse(uri);
//            } catch (URISyntaxException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//
//        }
//        else{
//            System.out.println("fatal error ");
//        }

        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        try {
            if (os.contains("win")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + authorizationUrl);
            } else if (os.contains("mac")) {
                runtime.exec("open " + authorizationUrl);
            } else if (os.contains("nix") || os.contains("nux")) {

                StringBuffer cmd = new StringBuffer();
                cmd.append(String.format(    "%s \"%s\"", authorizationUrl));
                runtime.exec(new String[] { "sh", "-c", cmd.toString() });
            } else {
                System.out.println("Unable to open URL, unsupported operating system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return authorizationUrl;
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
