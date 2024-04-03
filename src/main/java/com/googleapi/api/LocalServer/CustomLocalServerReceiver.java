package com.googleapi.api.LocalServer;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.awt.*;
import java.util.spi.LocaleServiceProvider;

public class CustomLocalServerReceiver  implements VerificationCodeReceiver {

    private final LocalServerReceiver receiver;
    private final CountDownLatch latch = new CountDownLatch(1);
    private String redirectUri;

    public CustomLocalServerReceiver(int port, String callbackPath) {
        //receiver.Builder().setPort(port).setCallbackPath(callbackPath).build();
        receiver = new LocalServerReceiver.Builder().setPort(port).setCallbackPath(callbackPath).build();
    }

    @Override
    public String getRedirectUri() throws IOException {
        if (redirectUri == null) {
            redirectUri = receiver.getRedirectUri();
            openUrl(redirectUri);
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return redirectUri;
    }

    private void openUrl(String url) {
        try {
            URI uri = new URI(url);
            Desktop.getDesktop().browse(uri);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String waitForCode() throws IOException {
        return null; // Implement your code retrieval logic here
    }

    @Override
    public void stop() {
        try {
            receiver.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        latch.countDown();
    }
}
