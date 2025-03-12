package com.credibanco.transactions.domain.port;

import com.credibanco.transactions.domain.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(Long id);
    List<Transaction> findByCardNumber(String cardNumber);
}
