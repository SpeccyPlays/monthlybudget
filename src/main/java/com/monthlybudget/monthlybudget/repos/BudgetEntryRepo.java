package com.monthlybudget.monthlybudget.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Repository;
import com.monthlybudget.monthlybudget.models.BudgetEntry;


@Repository

public interface BudgetEntryRepo extends JpaRepository<BudgetEntry, Long> {
    @Query("SELECT b FROM BudgetEntry b WHERE EXTRACT(YEAR FROM b.date) = :yearValue AND EXTRACT(MONTH from b.date) = :monthValue")
    Iterable<BudgetEntry> getByYearAndMonth(@Param("yearValue") Integer yearValue, @Param("monthValue") Integer monthValue);
}
