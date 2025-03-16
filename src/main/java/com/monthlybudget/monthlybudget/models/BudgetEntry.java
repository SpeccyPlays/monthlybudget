package com.monthlybudget.monthlybudget.models;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "amount", nullable = false)
    private float amount;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAmount(String amount) {
        this.amount = Float.parseFloat(amount);
    }
}
