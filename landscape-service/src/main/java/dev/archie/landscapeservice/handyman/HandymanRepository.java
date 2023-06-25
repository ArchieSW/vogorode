package dev.archie.landscapeservice.handyman;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HandymanRepository extends JpaRepository<Handyman, UUID> {
}