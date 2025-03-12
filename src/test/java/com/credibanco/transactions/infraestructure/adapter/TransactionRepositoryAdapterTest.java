package com.credibanco.transactions.infraestructure.adapter;

import com.credibanco.transactions.domain.model.Transaction;
import com.credibanco.transactions.domain.model.TransactionStatus;
import com.credibanco.transactions.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionRepositoryAdapterTest {

    @Mock
    private TransactionJpaRepository transactionJpaRepository;

    @InjectMocks
    private TransactionRepositoryAdapter transactionRepositoryAdapter;

    private Transaction transaction;
    private TransactionEntity transactionEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = Transaction.builder()
                .id(1L)
                .cardNumber("1234567890")
                .amount(new BigDecimal("100.00"))
                .type(TransactionType.PAGO)
                .status(TransactionStatus.SUCCESS)
                .currency("USD")
                .transactionDate(LocalDateTime.now())
                .build();

        transactionEntity = TransactionEntity.builder()
                .id(1L)
                .cardNumber("1234567890")
                .amount(new BigDecimal("100.00"))
                .type(TransactionType.PAGO)
                .status(TransactionStatus.SUCCESS)
                .currency("USD")
                .transactionDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testSave() {
        when(transactionJpaRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntity);

        Transaction savedTransaction = transactionRepositoryAdapter.save(transaction);

        assertNotNull(savedTransaction);
        assertEquals(transaction.getId(), savedTransaction.getId());
        verify(transactionJpaRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    void testFindById() {
        when(transactionJpaRepository.findById(1L)).thenReturn(Optional.of(transactionEntity));

        Optional<Transaction> foundTransaction = transactionRepositoryAdapter.findById(1L);

        assertTrue(foundTransaction.isPresent());
        assertEquals(transaction.getId(), foundTransaction.get().getId());
        verify(transactionJpaRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByCardNumber() {
        when(transactionJpaRepository.findByCardNumber("1234567890")).thenReturn(Collections.singletonList(transactionEntity));

        var transactions = transactionRepositoryAdapter.findByCardNumber("1234567890");

        assertFalse(transactions.isEmpty());
        assertEquals(transaction.getCardNumber(), transactions.get(0).getCardNumber());
        verify(transactionJpaRepository, times(1)).findByCardNumber("1234567890");
    }
}
