package com.example.web_zaim.controller;

import com.example.web_zaim.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/calculate")
    public ResponseEntity<String> calculateCrossPaymentString() {
        String crossPaymentString = paymentService.calculateCrossPaymentString();
        return ResponseEntity.ok(crossPaymentString);
    }
}
