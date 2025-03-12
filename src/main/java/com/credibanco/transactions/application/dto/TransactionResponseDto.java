package com.credibanco.transactions.application.dto;

import com.credibanco.transactions.domain.model.TransactionStatus;
import com.credibanco.transactions.domain.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponseDto {
    private Long id;
    private String cardNumber;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String currency;
    private LocalDateTime transactionDate;
}
