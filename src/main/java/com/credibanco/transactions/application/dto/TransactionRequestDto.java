package com.credibanco.transactions.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionRequestDto {
    @NotBlank
    private String cardNumber;
    @NotNull
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal amount;
    @NotBlank(message = "Currency is mandatory")
    @Pattern(regexp = "USD", message = "Currency must be USD")
    private String currency;

}
