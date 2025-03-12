package com.credibanco.transactions.infraestructure.configuration;

import com.credibanco.transactions.application.service.TransactionService;
import com.credibanco.transactions.domain.port.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class TransactionConfigurationTest {

    @Mock
    private TransactionJpaRepository transactionJpaRepository;

    @Mock
    private CardServiceClient cardServiceClient;

    @InjectMocks
    private TransactionConfiguration transactionConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTransactionRepository() {
        TransactionRepository transactionRepository = transactionConfiguration.transactionRepository(transactionJpaRepository);
        assertNotNull(transactionRepository);
    }

    @Test
    void testTransactionService() {
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        TransactionService transactionService = transactionConfiguration.transactionService(transactionRepository, cardServiceClient);
        assertNotNull(transactionService);
    }
}