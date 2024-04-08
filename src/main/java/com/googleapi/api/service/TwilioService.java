package com.googleapi.api.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TwilioService{
    @Value("${twilio.account-sid}")
private String twilioAccountSid;

@Value("${twilio.auth-token}")
private String twilioAuthToken;

@Value("${twilio.phone-number}")
private String twilioPhoneNumber;

public String generateOtp() {
    // Generate a random 6-digit OTP
    Random random = new Random();
    int otp = 100000 + random.nextInt(900000);
    return String.valueOf(otp);
}

public void sendOtp(String phoneNumber) {
    String otp = generateOtp();
    Twilio.init(twilioAccountSid, twilioAuthToken);
    phoneNumber="+91"+phoneNumber;
    System.out.println(twilioPhoneNumber);

    // Send SMS containing the OTP
    Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber("+918462056100"),
                    "Your OTP for verification is: " + otp)
            .create();

    System.out.println("OTP sent successfully: " + message.getSid());
}
}
