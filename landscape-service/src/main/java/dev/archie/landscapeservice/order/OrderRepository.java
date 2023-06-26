package dev.archie.landscapeservice.order;

import dev.archie.landscapeservice.handyman.Handyman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
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
            select h
            from Order o join o.user u
            join Handyman h on u.id = h.id
            where o.field.id = ?1
            """)
    List<Handyman> findHandymenByField_Id(Long id);

    @Query("""
            select count(distinct s) < count(distinct o.workType)
            from Order o join o.user u
            join Handyman h on u.id = h.id
            join h.skills s
            join o.field f
            where f.id = ?1
            """)
    Boolean isWorkDeficit(Long id);

    @Query("""
            select o
            from Order o
            where o.createdAt = (
                select min(o1.createdAt) from Order o1
                where o1.status = 'CREATED'
            )
            """)
    Optional<Order> findFirstCreatedOrder();
}