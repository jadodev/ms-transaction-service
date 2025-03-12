package com.credibanco.transactions.application.service;

import com.credibanco.transactions.domain.model.Transaction;
import com.credibanco.transactions.domain.model.TransactionStatus;
import com.credibanco.transactions.domain.model.TransactionType;
import com.credibanco.transactions.domain.port.TransactionRepository;
import com.credibanco.transactions.infrastructure.client.CardServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardServiceClient cardServiceClient;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessRecharge_Success() {
        when(cardServiceClient.rechargeCard(anyString(), any(BigDecimal.class))).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.processRecharge("1234567890", new BigDecimal("100.00"), "USD");

        assertNotNull(transaction);
        assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        assertEquals(TransactionType.RECARGA, transaction.getType());
    }

    @Test
    void testProcessRecharge_Failure() {
        when(cardServiceClient.rechargeCard(anyString(), any(BigDecimal.class))).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.processRecharge("1234567890", new BigDecimal("100.00"), "USD");

        assertNotNull(transaction);
        assertEquals(TransactionStatus.REJECTED, transaction.getStatus());
        assertEquals(TransactionType.RECARGA, transaction.getType());
    }

    @Test
    void testProcessPayment_Success() {
        when(cardServiceClient.debitCard(anyString(), any(BigDecimal.class))).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.processPayment("1234567890", new BigDecimal("50.00"), "USD");

        assertNotNull(transaction);
        assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        assertEquals(TransactionType.PAGO, transaction.getType());
    }

    @Test
    void testProcessPayment_Failure() {
        when(cardServiceClient.debitCard(anyString(), any(BigDecimal.class))).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.processPayment("1234567890", new BigDecimal("50.00"), "USD");

        assertNotNull(transaction);
        assertEquals(TransactionStatus.REJECTED, transaction.getStatus());
        assertEquals(TransactionType.PAGO, transaction.getType());
    }

    @Test
    void testCancelTransaction_Success() {
        Transaction existingTransaction = Transaction.builder()
                .id(1L)
                .cardNumber("1234567890")
                .amount(new BigDecimal("100.00"))
                .type(TransactionType.PAGO)
                .status(TransactionStatus.SUCCESS)
                .transactionDate(LocalDateTime.now().minusHours(1))
                .build();

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existingTransaction));
        when(cardServiceClient.rechargeCard(anyString(), any(BigDecimal.class))).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.cancelTransaction(1L);

        assertNotNull(transaction);
        assertEquals(TransactionStatus.CANCELLED, transaction.getStatus());
    }

    @Test
    void testCancelTransaction_Failure() {
        Transaction existingTransaction = Transaction.builder()
                .id(1L)
                .cardNumber("1234567890")
                .amount(new BigDecimal("100.00"))
                .type(TransactionType.PAGO)
                .status(TransactionStatus.SUCCESS)
                .transactionDate(LocalDateTime.now().minusHours(1))
                .build();

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existingTransaction));
        when(cardServiceClient.rechargeCard(anyString(), any(BigDecimal.class))).thenReturn(false);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.cancelTransaction(1L);

        assertNotNull(transaction);
        assertEquals(TransactionStatus.REJECTED, transaction.getStatus());
    }

    @Test
    void testListTransactions() {
        List<Transaction> transactions = List.of(
                Transaction.builder().cardNumber("1234567890").build(),
                Transaction.builder().cardNumber("1234567890").build()
        );

        when(transactionRepository.findByCardNumber("1234567890")).thenReturn(transactions);

        List<Transaction> result = transactionService.listTransactions("1234567890");

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}