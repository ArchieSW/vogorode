package dev.archie.landscapeservice.handyman;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HandymanRepository extends JpaRepository<Handyman, UUID> {
    @Query("""
            select h
            from Handyman h
            join User u on u.id = h.id
            where u.orders.size = 0
            order by ST_Distance(ST_Point(?1, ?2), ST_Point(u.latitude, u.longitude))
            """)
    List<Handyman> findNearestNotBusyHandyman(Double latitude, Double longitude);
}