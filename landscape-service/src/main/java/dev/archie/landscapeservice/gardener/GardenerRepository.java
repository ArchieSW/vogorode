package dev.archie.landscapeservice.gardener;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GardenerRepository extends JpaRepository<Gardener, UUID> {
}