package com.credibanco.transactions.infrastructure.configuration;

import com.credibanco.transactions.application.service.TransactionService;
import com.credibanco.transactions.application.service.TransactionServiceImpl;
import com.credibanco.transactions.domain.port.TransactionRepository;
import com.credibanco.transactions.infrastructure.client.CardServiceClient;
import com.credibanco.transactions.infrastructure.persistence.adapter.TransactionRepositoryAdapter;
import com.credibanco.transactions.infrastructure.persistence.repository.TransactionJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfiguration {

    @Bean
    public TransactionRepository transactionRepository(TransactionJpaRepository repository) {
        return new TransactionRepositoryAdapter(repository);
    }

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository, CardServiceClient cardServiceClient) {
        return new TransactionServiceImpl(transactionRepository, cardServiceClient);
    }
}
