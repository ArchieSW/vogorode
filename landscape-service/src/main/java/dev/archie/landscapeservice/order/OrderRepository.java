package dev.archie.landscapeservice.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_IdOrderByUser_LoginDesc(UUID id, Pageable pageable);
    Page<Order> findByUser_IdOrderByUser_LoginAsc(UUID id, Pageable pageable);
}