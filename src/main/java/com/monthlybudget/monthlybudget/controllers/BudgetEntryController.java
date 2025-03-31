package com.monthlybudget.monthlybudget.controllers;

import com.monthlybudget.monthlybudget.datatransferobjects.BudgetEntriesByMonthDTO;
import com.monthlybudget.monthlybudget.services.BudgetEntryService;
import com.monthlybudget.monthlybudget.services.UserService;
import com.monthlybudget.monthlybudget.models.User;
import com.monthlybudget.monthlybudget.models.BudgetEntry.EntryType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            Model model) {
        // get logged in user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username

        // Fetch the user from the database
        User loggedInUser = userService.findByUsername(username);
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect if user is not found
        }
        model.addAttribute("username", loggedInUser.getUsername() + "'s budget page");
        // get entries based on params and add to model
        LocalDate todaysDate = LocalDate.now();// used to set default value for date picker
        DateTimeFormatter dateFormatting = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("todaysdate", todaysDate.format(dateFormatting));
        List<BudgetEntriesByMonthDTO> entries = budgetEntryService.getBudgetEntriesGrouped(loggedInUser.getId());
        model.addAttribute("entries", entries);
        // Get values needed for ease of display
        List<Integer> years = budgetEntryService.getYearsList(loggedInUser.getId());
        String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        model.addAttribute("monthnames", monthNames);
        model.addAttribute("years", years);
        // Get cumulative totals
        Map<Integer, Map<Integer, Float>> cumulativeTotals = new HashMap<>();
        ;
        for (BudgetEntriesByMonthDTO yearData : entries) {
            cumulativeTotals.put(yearData.getYear(), yearData.getCumulativeTotals());
        }
        model.addAttribute("cumulativeTotals", cumulativeTotals);
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
        if (loggedInUser == null) {
            return "redirect/login";
        }
        if (budgetEntryService.save(date, description, amount, entryType, loggedInUser.getId())) {
            redirectAttributes.addFlashAttribute("message", "Entry added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to add entry. Please check your input.");
        }
        ;
        return "redirect:/budgetpage";
    }

    @PostMapping("budgetpage/{id}")
    public String deleteEntry(
            @PathVariable("id") Long id) {
        budgetEntryService.deleteById(id);
        return "redirect:/budgetpage";
    }
}
