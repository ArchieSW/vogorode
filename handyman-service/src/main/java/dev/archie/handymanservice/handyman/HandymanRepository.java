package dev.archie.handymanservice.handyman;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface HandymanRepository extends MongoRepository<Handyman, String> {
    boolean existsByInnerId(UUID innerId);
    Optional<Handyman> findByInnerId(UUID innerId);
}
