package com.spring.wealthManagement.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.wealthManagement.dao.ExpenseRepository;
import com.spring.wealthManagement.dao.UserRepository;
import com.spring.wealthManagement.models.Expenses;
import com.spring.wealthManagement.models.User;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "Login");
		return "login";
	}	
	
	@RequestMapping(value="/registration", method= RequestMethod.GET)
	public String signUp(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@RequestMapping(value="/registration", method= RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, Model model) {
		System.out.println("User"+ user.toString());
		user.setEnabled(true);
		user.setImageUrl(null);
		user.setBalance(user.getIncome());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
		model.addAttribute("user", user);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/home", method= RequestMethod.GET)
	public String home(Model model, Principal principal) {
		String userName = principal.getName();
		model.addAttribute("expenses", new Expenses());
		System.out.println("Username:"+ userName);
		return "home";
	}
	
	@RequestMapping(value="/home", method= RequestMethod.POST)
	public String addexpense(@ModelAttribute("expenses") Expenses expenses, Model model, Principal principal ) {
		String email = principal.getName();
		
		User user = userRepository.findByEmail(email);
		System.out.println("Expense:"+ expenses.toString());
		System.out.println("see"+ (user.getIncome() * 10 )/100);
		if(user.getStage().equals("Married_With_Children")) { // 75% for expense others for investment and savings
			expenses.setInvestment(Double.valueOf((user.getIncome() * 10 )/100));
			expenses.setSavings(Double.valueOf((user.getIncome() * 5 )/100));
			expenses.setLiquidity(Double.valueOf((user.getIncome() * 15 )/100));
		} else {
			expenses.setInvestment(Double.valueOf((user.getIncome() * 10 )/100));
			expenses.setSavings(Double.valueOf((user.getIncome() * 10 )/100));
			expenses.setLiquidity(Double.valueOf((user.getIncome() * 20 )/100));
		}
		user.setBalance(user.getBalance() - expenses.getExpense());
		userRepository.save(user);
		
		expenses.setBalance(user.getBalance());
		expenses.setUser(user);
		
		expenseRepository.save(expenses);
		model.addAttribute("expense", expenses);
		return "redirect:/view";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Us");
		return "about";
	}
	
	@GetMapping("/view")
	public String View(Model model, Principal principal) {
		String email = principal.getName();
		User user = userRepository.findByEmail(email);
		List<Expenses> e = user.getExpenses();
		model.addAttribute("expenses", e);
		model.addAttribute("title", "View contact");
		return "view";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteExpense(@PathVariable("id") Long id, Model model, HttpSession session) {
		
		Optional<Expenses> expenseOptional = expenseRepository.findById(id);
		Expenses expense = expenseOptional.get();
		expense.setUser(null);
		
		expenseRepository.delete(expense);
		session.setAttribute("message", "Deleted succesfully");
		return "redirect:/view";
	}
	
	@RequestMapping("/chart")
    public String getChart(Model model,Principal principal) {

		String email = principal.getName();
		User user = userRepository.findByEmail(email);
		List<Expenses> e = user.getExpenses();
		System.out.println("Check"+ e);
		
        Map<String, Double> graphData = new TreeMap<>();
        graphData.put("E1", e.get(1).getExpense());
        graphData.put("E2", e.get(2).getExpense());
        return "chart";
    }
	
	
}
