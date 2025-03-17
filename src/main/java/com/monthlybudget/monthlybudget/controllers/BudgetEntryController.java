package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.models.BudgetEntry;
import com.monthlybudget.monthlybudget.repos.BudgetEntryRepo;

import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BudgetEntryController {
    private final BudgetEntryRepo budgetEntryRepo;

    public BudgetEntryController(BudgetEntryRepo budgetEntryRepo) {
        this.budgetEntryRepo = budgetEntryRepo;
    }

    @GetMapping("/budgetpage")
    public String showBudgetPage(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {

        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter dateFormatting = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("todaysdate", todaysDate.format(dateFormatting));

        Iterable<BudgetEntry> entries;
        Integer yearParam = todaysDate.getYear();
        Integer monthParam = todaysDate.getMonthValue();

        if (year != null) {
            yearParam = year;
        }
        if (month != null) {
            monthParam = month;
        }
        entries = budgetEntryRepo.getByYearAndMonth(yearParam, monthParam);
        model.addAttribute("entries", entries);
        return "budgetpage";
    }

    @PostMapping("/budgetpage")
    public String saveEntry(
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("amount") String amount,
            RedirectAttributes redirectAttributes) {

        BudgetEntry entry = new BudgetEntry();
        entry.setDate(date);
        entry.setDescription(description);
        entry.setAmount(amount);

        budgetEntryRepo.save(entry);
        redirectAttributes.addFlashAttribute("message", "Entry added successfully!");

        return "redirect:/budgetpage";

    }
}
