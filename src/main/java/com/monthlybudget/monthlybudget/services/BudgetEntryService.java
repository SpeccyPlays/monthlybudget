package com.monthlybudget.monthlybudget.services;

import com.monthlybudget.monthlybudget.models.BudgetEntry;
import com.monthlybudget.monthlybudget.models.BudgetEntry.EntryType;
import com.monthlybudget.monthlybudget.repos.BudgetEntryRepo;
import com.monthlybudget.monthlybudget.datatransferobjects.BudgetEntriesByMonthDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BudgetEntryService {

    @Autowired
    private BudgetEntryRepo budgetEntryRepo;

    public List<BudgetEntriesByMonthDTO> getBudgetEntriesGrouped(Long userId){
        //Get all the data for this user
            List<BudgetEntry> budgetEntries = (List<BudgetEntry>) budgetEntryRepo.getByUserId(userId);
            Map<Integer, BudgetEntriesByMonthDTO> groupedData = new HashMap<>();
            //loop through the entries and sort
            for (BudgetEntry entry : budgetEntries){
                LocalDate date = LocalDate.ofInstant(entry.getDate().toInstant(), ZoneId.systemDefault());
                int year = date.getYear();
                int month = date.getMonthValue();
                if (groupedData.get(year) == null){
                    groupedData.put(year, new BudgetEntriesByMonthDTO(year));
                }
                groupedData.get(year).addEntry(month, entry);
            }
        return new ArrayList<>(groupedData.values());
    }

    public Iterable<BudgetEntry> getBudgetEntries(Integer year, Integer month, Long userId) {
        LocalDate today = LocalDate.now();
        if (year == null && month == null) {
            return budgetEntryRepo.getByUserId(userId);
        }
        Integer yearParam = (year != null) ? year : today.getYear();
        if (month == null) {
            System.out.println("Fetching entries for year: " + yearParam);
            return budgetEntryRepo.getByYear(yearParam, userId);
        }
        Integer monthParam = (month != null) ? month : today.getMonthValue();
        return budgetEntryRepo.getByYearAndMonth(yearParam, monthParam, userId);
    }

    public Iterable<BudgetEntry> getAllEntries() {
        return budgetEntryRepo.findAll();
    }

    public List<Integer> getYearsList(Long userId) {
        // Prepare year selection values based on the years in the entries
        // Get all the entries (I don't like this for scaleability but it's easier)
        Iterable<BudgetEntry> allEntries = budgetEntryRepo.getByUserId(userId);
        List<Integer> years = new ArrayList<Integer>();
        allEntries.forEach((entry) -> {
            // convert date used in postgres to localdate
            LocalDate date = LocalDate.ofInstant(entry.getDate().toInstant(), ZoneId.systemDefault());
            int entryYear = date.getYear();
            if (!years.contains(entryYear)) {
                years.add(entryYear);
            }
        });
        return years;
    }

    public boolean save(String date, String description, String amount, EntryType entryType, Long userId) {
        try {
            BudgetEntry entry = new BudgetEntry();
            entry.setDate(date);
            entry.setDescription(description);
            entry.setEntrytype(entryType);
            entry.setAmount(amount);
            entry.setUserid(userId);
            budgetEntryRepo.save(entry);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving entry: " + e.getMessage());
            return false;
        }
    }

    public void deleteById(Long id) {
        budgetEntryRepo.deleteById(id);
    }
}
