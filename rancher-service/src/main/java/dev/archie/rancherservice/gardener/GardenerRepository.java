package dev.archie.rancherservice.gardener;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GardenerRepository extends JpaRepository<Gardener, Long> {
    Optional<Gardener> findByEmail(String email);

    boolean existsByEmail(String email);
}
