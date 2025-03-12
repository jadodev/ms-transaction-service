package com.credibanco.transactions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private Long id;
    private String cardNumber;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String currency;
    @Builder.Default
    private LocalDateTime transactionDate = LocalDateTime.now();

}
