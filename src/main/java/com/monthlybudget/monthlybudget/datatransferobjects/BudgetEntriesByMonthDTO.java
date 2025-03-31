package com.monthlybudget.monthlybudget.datatransferobjects;

import com.monthlybudget.monthlybudget.models.BudgetEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
/*
 * Makes it easier to group data and then use the front end to filter/sort as required
 */

public class BudgetEntriesByMonthDTO {
    private int year;
    private Map<Integer, List<BudgetEntry>> monthlyEntries;

    public BudgetEntriesByMonthDTO(int year) {
        this.year = year;
        this.monthlyEntries = new HashMap<>();
    }

    public void addEntry(int month, BudgetEntry entry) {
        monthlyEntries.putIfAbsent(month, new ArrayList<>());
        monthlyEntries.get(month).add(entry);
    }

    public int getYear() {
        return year;
    }

    public Map<Integer, List<BudgetEntry>> getMonthlyEntries() {
        return monthlyEntries;
    }

    public Map<Integer, Float> getCumulativeTotals() {
        Map<Integer, Float> cumulativeTotals = new TreeMap<>(); // Ensures months are sorted
        float runningTotal = 0f;

        for (Map.Entry<Integer, List<BudgetEntry>> entry : new TreeMap<>(monthlyEntries).entrySet()) {
            int month = entry.getKey();
            float monthTotal = entry.getValue().stream()
                    .map(BudgetEntry::getAmount)
                    .reduce(0f, Float::sum);

            runningTotal += monthTotal;
            cumulativeTotals.put(month, runningTotal);
        }
        return cumulativeTotals;
    }

    @Override
    public String toString() {
        return "Year: " + year + ", MonthlyEntries: " + monthlyEntries;
    }
}