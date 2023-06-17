package dev.archie.landscapeservice.user;

import dev.archie.landscapeservice.stat.RancherStat;
import dev.archie.landscapeservice.user.type.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByUserType(UserType userType, Pageable pageable);
    Optional<User> findByEmail(String email);

    @Query("""
            select new dev.archie.landscapeservice.stat.RancherStat(g.id, g.login, min(ST_Area(f.area)), max(ST_Area(f.area)), avg(ST_Area(f.area)))
            from Gardener g join Field f on f.gardener.id = g.id
            group by g.login, g.id""")
    Page<RancherStat> findRancherStatGroupByLogin(Pageable pageable);
}
