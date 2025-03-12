package com.credibanco.transactions.application.service;

import com.credibanco.transactions.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction processRecharge(String cardNumber, BigDecimal amount, String currency);
    Transaction processPayment(String cardNumber, BigDecimal amount, String currency);
    Transaction cancelTransaction(Long transactionId);
    List<Transaction> listTransactions(String cardNumber);
}
