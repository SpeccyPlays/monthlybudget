package com.monthlybudget.monthlybudget.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "budget_entries")
public class BudgetEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Using SERIAL type in PostgreSQL
    private Long id;

    @NotNull
    @Column(name = "daily_budget_id", nullable = false)
    private Long daily_budget_id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "amount", nullable = false)
    private float amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDaily_budget_id() {
        return daily_budget_id;
    }

    public void setDaily_budget_id(Long daily_budget_id) {
        this.daily_budget_id = daily_budget_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
