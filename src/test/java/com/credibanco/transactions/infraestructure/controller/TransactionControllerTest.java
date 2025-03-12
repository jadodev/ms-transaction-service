package com.credibanco.transactions.infraestructure.controller;

import com.credibanco.transactions.application.dto.TransactionResponseDto;
import com.credibanco.transactions.application.service.TransactionService;
import com.credibanco.transactions.domain.model.Transaction;
import com.credibanco.transactions.domain.model.TransactionStatus;
import com.credibanco.transactions.domain.model.TransactionType;
import com.credibanco.transactions.infrastructure.controller.TransactionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void testRecharge() throws Exception {
        Transaction transaction = new Transaction(1L, "1234567890", new BigDecimal("100.0"), TransactionType.RECARGA, TransactionStatus.SUCCESS, "USD", LocalDateTime.now());
        TransactionResponseDto responseDto = new TransactionResponseDto(1L, "1234567890", new BigDecimal("100.0").setScale(2), TransactionType.RECARGA, TransactionStatus.SUCCESS, "USD", LocalDateTime.now());

        when(transactionService.processRecharge(any(), any(), any())).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/recharge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardNumber\":\"1234567890\",\"amount\":100.0,\"currency\":\"USD\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.cardNumber").value(responseDto.getCardNumber()))
                .andExpect(jsonPath("$.type").value(responseDto.getType().toString()))
                .andExpect(jsonPath("$.status").value(responseDto.getStatus().toString()))
                .andExpect(jsonPath("$.currency").value(responseDto.getCurrency()))
                .andExpect(jsonPath("$.transactionDate").exists());
    }

    @Test
    void testPayment() throws Exception {
        Transaction transaction = new Transaction(1L, "1234567890", new BigDecimal("100.0"), TransactionType.PAGO, TransactionStatus.SUCCESS, "USD", LocalDateTime.now());
        TransactionResponseDto responseDto = new TransactionResponseDto(1L, "1234567890", new BigDecimal("100.0").setScale(2), TransactionType.PAGO, TransactionStatus.SUCCESS, "USD", LocalDateTime.now());

        when(transactionService.processPayment(any(), any(), any())).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardNumber\":\"1234567890\",\"amount\":100.0,\"currency\":\"USD\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.cardNumber").value(responseDto.getCardNumber()))
                .andExpect(jsonPath("$.type").value(responseDto.getType().toString()))
                .andExpect(jsonPath("$.status").value(responseDto.getStatus().toString()))
                .andExpect(jsonPath("$.currency").value(responseDto.getCurrency()))
                .andExpect(jsonPath("$.transactionDate").exists());
    }

    @Test
    void testCancel() throws Exception {
        Transaction transaction = new Transaction(1L, "1234567890", new BigDecimal("100.0"), TransactionType.PAGO, TransactionStatus.CANCELLED, "USD", LocalDateTime.now());
        TransactionResponseDto responseDto = new TransactionResponseDto(1L, "1234567890", new BigDecimal("100.0").setScale(2), TransactionType.PAGO, TransactionStatus.CANCELLED, "USD", LocalDateTime.now());

        when(transactionService.cancelTransaction(any())).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.cardNumber").value(responseDto.getCardNumber()))
                .andExpect(jsonPath("$.type").value(responseDto.getType().toString()))
                .andExpect(jsonPath("$.status").value(responseDto.getStatus().toString()))
                .andExpect(jsonPath("$.currency").value(responseDto.getCurrency()))
                .andExpect(jsonPath("$.transactionDate").exists());
    }

    @Test
    void testListTransactions() throws Exception {
        Transaction transaction = new Transaction(1L, "1234567890", new BigDecimal("100.0"), TransactionType.PAGO, TransactionStatus.SUCCESS, "USD", LocalDateTime.now());
        TransactionResponseDto responseDto = new TransactionResponseDto(1L, "1234567890", new BigDecimal("100.0").setScale(2), TransactionType.PAGO, TransactionStatus.SUCCESS, "USD", LocalDateTime.now());

        when(transactionService.listTransactions(any())).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/api/transactions/card/1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.getId()))
                .andExpect(jsonPath("$[0].cardNumber").value(responseDto.getCardNumber()))
                .andExpect(jsonPath("$[0].type").value(responseDto.getType().toString()))
                .andExpect(jsonPath("$[0].status").value(responseDto.getStatus().toString()))
                .andExpect(jsonPath("$[0].transactionDate").exists());
    }
}