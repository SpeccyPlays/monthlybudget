package com.monthlybudget.monthlybudget.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.monthlybudget.monthlybudget.models.BudgetEntry;

@Repository

public interface BudgetEntryRepo extends JpaRepository<BudgetEntry, Long> {
    //BudgetEntry findByUserId(String userid);
}
