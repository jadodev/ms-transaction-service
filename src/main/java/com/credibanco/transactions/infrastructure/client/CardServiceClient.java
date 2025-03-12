package com.credibanco.transactions.infrastructure.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CardServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${cards.service.url}")
    String cardsServiceUrl;

    public boolean rechargeCard(@NotNull String cardNumber, @NotNull BigDecimal amount) {
        String url = cardsServiceUrl + "/api/cards/" + cardNumber + "/recharge";
        try {
            restTemplate.postForObject(url, new AmountDto(amount), Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean debitCard(@NotNull String cardNumber, @NotNull BigDecimal amount) {
        String url = cardsServiceUrl + "/api/cards/" + cardNumber + "/debit";
        try {
            restTemplate.postForObject(url, new AmountDto(amount), Void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Data
    @AllArgsConstructor
    public static class AmountDto {
        private BigDecimal amount;
    }
}
