package dev.archie.handymanservice.account.bank;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BankRepository extends MongoRepository<Bank, Long> {
    Optional<Bank> findByName(String name);
}