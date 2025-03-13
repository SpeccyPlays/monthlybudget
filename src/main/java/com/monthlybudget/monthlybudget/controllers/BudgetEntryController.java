package com.monthlybudget.monthlybudget.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monthlybudget.monthlybudget.models.BudgetEntry;
import com.monthlybudget.monthlybudget.repos.BudgetEntryRepo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;

@Controller
public class BudgetEntryController {
    private final BudgetEntryRepo budgetEntryRepo;

    public BudgetEntryController(BudgetEntryRepo budgetEntryRepo) {
        this.budgetEntryRepo = budgetEntryRepo;
    }

    @GetMapping("/budgetpage")
    public String getAllEntries() {
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
