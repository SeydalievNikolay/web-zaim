package com.example.web_zaim.service.impl;

import com.example.web_zaim.model.Credit;
import com.example.web_zaim.repository.CreditRepository;
import com.example.web_zaim.service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private CreditRepository creditRepository;

    private List<Credit> credits;
    public PaymentServiceImpl() {
        initializeCredits();
    }

    @PostConstruct
    public void initializeCredits() {
        try {
            credits = creditRepository.findAll();
            if (credits == null || credits.isEmpty()) {
                throw new IllegalStateException("Кредиты не найдены");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации списка кредитов: " + e.getMessage());
        }
    }
    public String calculateCrossPaymentString() {
        if (credits == null || credits.isEmpty()) {
            throw new IllegalStateException("Список кредитов пуст");
        }

        Map<LocalDate, Integer> paymentDates = new HashMap<>();
        for (Credit credit : credits) {
            String paymentString = credit.getPaymentString();
            for (char code : paymentString.toCharArray()) {
                LocalDate date = credit.getFirstPaymentDate().plusDays(Character.getNumericValue(code));
                paymentDates.putIfAbsent(date, 0);
                paymentDates.put(date, Math.max(paymentDates.get(date), getDelayDays(code)));
            }
        }

        LocalDate minDate = credits.stream()
                .map(Credit::getFirstPaymentDate)
                .min(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalStateException("Не найдена дата последнего платежа"));
        LocalDate maxDate = credits.stream()
                .map(Credit::getFirstPaymentDate)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalStateException("Не найдена дата последнего платежа"));

        StringBuilder crossPaymentString = new StringBuilder();
        for (LocalDate date = minDate; !date.isAfter(maxDate); date = date.plusDays(1)) {
            if (paymentDates.containsKey(date)) {
                crossPaymentString.append(getCodeForDelay(paymentDates.get(date)));
            } else {
                crossPaymentString.append('X');
            }
        }

        return crossPaymentString.toString();
    }

    public int getDelayDays(char code) {

        return switch (Character.toUpperCase(code)) {
            case '1' -> 0;
            case '0' -> Integer.MAX_VALUE;
            case 'A' -> 7;
            case '2' -> 29;
            case '3' -> 39;
            case 'X' -> Integer.MIN_VALUE;
            default -> Integer.MAX_VALUE;
        };
    }

    public char getCodeForDelay(int delayDays) {

        return switch (delayDays) {
            case 0 -> '1';
            case 7 -> 'A';
            case 29 -> '2';
            case 39 -> '3';
            default -> 'X';
        };
    }
}
