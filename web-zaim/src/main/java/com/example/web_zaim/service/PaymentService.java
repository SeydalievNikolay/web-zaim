package com.example.web_zaim.service;

public interface PaymentService {
    String calculateCrossPaymentString();
    int getDelayDays(char code);
    char getCodeForDelay(int delayDays);

}
