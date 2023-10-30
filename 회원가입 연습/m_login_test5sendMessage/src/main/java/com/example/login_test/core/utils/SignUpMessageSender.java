package com.example.login_test.core.utils;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignUpMessageSender {

    // **
    public static final String API_KEY = "NCSO0RAYTRNI7F53";

    // **
    public static final String APISECRETKEY = "WPJ03FMIJKSMQESRVFYQNXNDJWI30LJX";

    // **
    private static final String DOMAIN = "https://api.solapi.com";


    private static DefaultMessageService messageService = null;

    @Bean
    public void initialize() {
        // ** 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        /* this.messageService = NurigoApp.INSTANCE.initialize(
                "INSERT_API_KEY",               apiKey
                "INSERT_API_SECRET_KEY",        apiSecretKey
                "https://api.solapi.com");       domain
         */
        messageService = NurigoApp.INSTANCE.initialize(API_KEY, APISECRETKEY, DOMAIN);
    }

    public static void sendMessage(String fromPhoneNumber, String toPhoneNumber, String text) {
        Message message = new Message();

        message.setFrom(fromPhoneNumber);
        message.setTo(toPhoneNumber);
        message.setText(text);

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);
    }
}
