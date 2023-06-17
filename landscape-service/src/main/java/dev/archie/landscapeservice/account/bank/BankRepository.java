package dev.archie.landscapeservice.account.bank;

import dev.archie.landscapeservice.stat.BankStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByName(String name);

    @Query("""
            select new dev.archie.landscapeservice.stat.BankStat(a.bank.name, min(u.createdAt), max(u.createdAt))
            from Account a join User u on a.handymanUser.email = u.email
            group by a.bank.name
            """)
    List<BankStat> findAllBankStat();
}