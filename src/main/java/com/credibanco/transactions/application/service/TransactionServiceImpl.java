package com.credibanco.transactions.application.service;

import com.credibanco.transactions.domain.model.Transaction;
import com.credibanco.transactions.domain.model.TransactionStatus;
import com.credibanco.transactions.domain.model.TransactionType;
import com.credibanco.transactions.domain.port.TransactionRepository;
import com.credibanco.transactions.infrastructure.client.CardServiceClient;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardServiceClient cardServiceClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CardServiceClient cardServiceClient) {
        this.transactionRepository = transactionRepository;
        this.cardServiceClient = cardServiceClient;
    }

    @Override
    public Transaction processRecharge(String cardNumber, BigDecimal amount, String currency) {
        validateCurrency(currency);
        boolean success = cardServiceClient.rechargeCard(cardNumber, amount);
        TransactionStatus status = success ? TransactionStatus.SUCCESS : TransactionStatus.REJECTED;
        Transaction transaction = Transaction.builder()
                .cardNumber(cardNumber)
                .amount(amount)
                .type(TransactionType.RECARGA)
                .status(status)
                .currency(currency)
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction processPayment(String cardNumber, BigDecimal amount, String currency) {
        validateCurrency(currency);
        boolean success = cardServiceClient.debitCard(cardNumber, amount);
        TransactionStatus status = success ? TransactionStatus.SUCCESS : TransactionStatus.REJECTED;
        Transaction transaction = Transaction.builder()
                .cardNumber(cardNumber)
                .amount(amount)
                .type(TransactionType.PAGO)
                .status(status)
                .currency(currency)
                .build();
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction cancelTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        if (Duration.between(transaction.getTransactionDate(), LocalDateTime.now()).toHours() > 24) {
            throw new IllegalArgumentException("La transacción no puede ser anulada, excede el límite de 24 horas");
        }
        boolean success = cardServiceClient.rechargeCard(transaction.getCardNumber(), transaction.getAmount());
        TransactionStatus status = success ? TransactionStatus.CANCELLED : TransactionStatus.REJECTED;
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> listTransactions(String cardNumber) {
        return transactionRepository.findByCardNumber(cardNumber);
    }

    private void validateCurrency(String currency) {
        if (!"USD".equalsIgnoreCase(currency)) {
            throw new IllegalArgumentException("Currency must be USD");
        }
    }
}
