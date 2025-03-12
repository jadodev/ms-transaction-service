package com.credibanco.transactions.infrastructure.persistence.repository;

import com.credibanco.transactions.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findById(Long id);
    List<TransactionEntity> findByCardNumber(String cardNumber);
}
