package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.models.BudgetEntry;
import com.monthlybudget.monthlybudget.services.BudgetEntryService;
import com.monthlybudget.monthlybudget.services.UserService;
import com.monthlybudget.monthlybudget.models.User;
import com.monthlybudget.monthlybudget.models.BudgetEntry.EntryType;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class BudgetEntryController {
    @Autowired
    private BudgetEntryService budgetEntryService;

     @Autowired
    private UserService userService;

    @GetMapping("/budgetpage")
    public String showBudgetPage(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {
        //get logged in user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username

        // Fetch the user from the database
        User loggedInUser = userService.findByUsername(username);
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect if user is not found
        }
        model.addAttribute("username", loggedInUser.getUsername() + "'s budget page");
        //get entries based on params and add to model
        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter dateFormatting = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("todaysdate", todaysDate.format(dateFormatting));
        Iterable<BudgetEntry> entries = budgetEntryService.getBudgetEntries(year, month, loggedInUser.getId());
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
            @RequestParam("entrytype") EntryType entryType,
            RedirectAttributes redirectAttributes) {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username

        // Fetch the user from the database
        User loggedInUser = userService.findByUsername(username);
        if (loggedInUser == null){
            return "redirect/login";
        }
        if (budgetEntryService.save(date, description, amount, entryType, loggedInUser.getId())){
            redirectAttributes.addFlashAttribute("message", "Entry added successfully!");
        } 
        else {
            redirectAttributes.addFlashAttribute("error", "Failed to add entry. Please check your input.");
        };
        return "redirect:/budgetpage";
    }
}
