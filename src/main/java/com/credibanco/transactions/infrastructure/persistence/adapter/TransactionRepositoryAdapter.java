package com.credibanco.transactions.infrastructure.persistence.adapter;

import com.credibanco.transactions.domain.model.Transaction;
import com.credibanco.transactions.domain.port.TransactionRepository;
import com.credibanco.transactions.infrastructure.persistence.entity.TransactionEntity;
import com.credibanco.transactions.infrastructure.persistence.repository.TransactionJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionJpaRepository repository;

    public TransactionRepositoryAdapter(TransactionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = mapToEntity(transaction);
        TransactionEntity savedEntity = repository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return repository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Transaction> findByCardNumber(String cardNumber) {
        return repository.findByCardNumber(cardNumber)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    private TransactionEntity mapToEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .id(transaction.getId())
                .cardNumber(transaction.getCardNumber())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .currency(transaction.getCurrency())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

    private Transaction mapToDomain(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .cardNumber(entity.getCardNumber())
                .amount(entity.getAmount())
                .type(entity.getType())
                .status(entity.getStatus())
                .currency(entity.getCurrency())
                .transactionDate(entity.getTransactionDate())
                .build();
    }
}