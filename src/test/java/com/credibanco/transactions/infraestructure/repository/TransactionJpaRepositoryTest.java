package com.credibanco.transactions.infraestructure.repository;

import com.credibanco.transactions.domain.model.TransactionStatus;
import com.credibanco.transactions.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionJpaRepositoryTest {

    @Autowired
    private TransactionJpaRepository transactionJpaRepository;

    private TransactionEntity transactionEntity;

    @BeforeEach
    void setUp() {
        transactionEntity = new TransactionEntity();
        transactionEntity.setCardNumber("1234567890");
        transactionEntity.setAmount(new BigDecimal("100.00"));
        transactionEntity.setTransactionDate(LocalDateTime.now());
        transactionEntity.setCurrency("USD"); // Set the currency field
        transactionEntity.setStatus(TransactionStatus.SUCCESS); // Set the status field using the enum
        transactionEntity.setType(TransactionType.PAGO); // Set the type field using the enum
        transactionJpaRepository.save(transactionEntity);
    }

    @Test
    void testFindById() {
        Optional<TransactionEntity> foundEntity = transactionJpaRepository.findById(transactionEntity.getId());
        assertTrue(foundEntity.isPresent());
        assertEquals(transactionEntity.getId(), foundEntity.get().getId());
    }

    @Test
    void testFindByCardNumber() {
        List<TransactionEntity> foundEntities = transactionJpaRepository.findByCardNumber("1234567890");
        assertFalse(foundEntities.isEmpty());
        assertEquals("1234567890", foundEntities.get(0).getCardNumber());
    }
}