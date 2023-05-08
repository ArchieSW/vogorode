package dev.archie.handymanservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HandymanUserRepository extends JpaRepository<HandymanUser, Long> {
    Optional<HandymanUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
