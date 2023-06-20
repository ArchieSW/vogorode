package dev.archie.rancherservice.rancher;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GardenerRepository extends MongoRepository<Gardener, String> {
    Optional<Gardener> findByEmail(String email);

    boolean existsByEmail(String email);
}
