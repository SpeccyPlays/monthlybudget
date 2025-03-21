package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.models.BudgetEntry;
import com.monthlybudget.monthlybudget.services.BudgetEntryService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BudgetEntryController {
    @Autowired
    private BudgetEntryService budgetEntryService;

    @GetMapping("/budgetpage")
    public String showBudgetPage(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {
        //get entries based on params and add to model
        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter dateFormatting = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("todaysdate", todaysDate.format(dateFormatting));
        Iterable<BudgetEntry> entries = budgetEntryService.getBudgetEntries(year, month);
        model.addAttribute("entries", entries);
        List<Integer> years = budgetEntryService.getYearsList();
        model.addAttribute("years", years);
        return "budgetpage";
    }

    @PostMapping("/budgetpage")
    public String saveEntry(
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("amount") String amount,
            RedirectAttributes redirectAttributes) {

        if (budgetEntryService.save(date, description, amount)){
            redirectAttributes.addFlashAttribute("message", "Entry added successfully!");
        } 
        else {
            redirectAttributes.addFlashAttribute("error", "Failed to add entry. Please check your input.");
        };
        return "redirect:/budgetpage";
    }
}
