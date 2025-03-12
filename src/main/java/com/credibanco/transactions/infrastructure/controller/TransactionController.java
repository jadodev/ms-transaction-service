package com.credibanco.transactions.infrastructure.controller;

import com.credibanco.transactions.application.dto.TransactionRequestDto;
import com.credibanco.transactions.application.dto.TransactionResponseDto;
import com.credibanco.transactions.application.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/recharge")
    public ResponseEntity<TransactionResponseDto> recharge(@Valid @RequestBody TransactionRequestDto request) {
        var transaction = transactionService.processRecharge(request.getCardNumber(), request.getAmount(), request.getCurrency());
        var response = new TransactionResponseDto(
                transaction.getId(),
                transaction.getCardNumber(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getCurrency(),
                transaction.getTransactionDate()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment")
    public ResponseEntity<TransactionResponseDto> payment(@Valid @RequestBody TransactionRequestDto request) {
        var transaction = transactionService.processPayment(request.getCardNumber(), request.getAmount(), request.getCurrency());
        var response = new TransactionResponseDto(
                transaction.getId(),
                transaction.getCardNumber(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getCurrency(),
                transaction.getTransactionDate()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cancel/{transactionId}")
    public ResponseEntity<TransactionResponseDto> cancel(@PathVariable Long transactionId) {
        var transaction = transactionService.cancelTransaction(transactionId);
        var response = new TransactionResponseDto(
                transaction.getId(),
                transaction.getCardNumber(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getCurrency(),
                transaction.getTransactionDate()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<List<TransactionResponseDto>> listTransactions(@PathVariable String cardNumber) {
        List<TransactionResponseDto> response = transactionService.listTransactions(cardNumber)
                .stream()
                .map(t -> new TransactionResponseDto(
                        t.getId(),
                        t.getCardNumber(),
                        t.getAmount(),
                        t.getType(),
                        t.getStatus(),
                        t.getCurrency(),
                        t.getTransactionDate()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
