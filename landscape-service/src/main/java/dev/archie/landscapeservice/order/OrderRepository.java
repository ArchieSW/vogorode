package dev.archie.landscapeservice.order;

import dev.archie.landscapeservice.handyman.Handyman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_IdOrderByUser_LoginDesc(UUID id, Pageable pageable);

    Page<Order> findByUser_IdOrderByUser_LoginAsc(UUID id, Pageable pageable);

    @Query("""
            select distinct o.workType
            from Order o
            where o.field.id = ?1
            """)
    List<WorkType> findDistinctByField_Id(Long id);

    @Query("""
            SELECT h
            FROM Order o JOIN o.user u
            JOIN Handyman h ON u.id = h.id
            WHERE o.field.id = ?1
            """)
    List<Handyman> findHandymenByField_Id(Long id);

    @Query("""
            SELECT COUNT(DISTINCT s) < COUNT(DISTINCT o.workType)
            FROM Order o JOIN o.user u
            JOIN Handyman h ON u.id = h.id
            JOIN h.skills s
            JOIN o.field f
            WHERE f.id = ?1
            """)
    Boolean isWorkDeficit(Long id);
}