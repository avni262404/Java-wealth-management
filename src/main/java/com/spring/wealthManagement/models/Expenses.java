package com.spring.wealthManagement.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Expenses")
public class Expenses {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	  	private Double investment;
	    private Double savings;
		private Double expense;
	    @Column(length = 500)
	    private String expenseDesc;
	    private Double liquidity;
	    private Double balance;
	    @ManyToOne
	    private User user;
	    
	    public Expenses(Double investment, Double savings, Double expense, String expenseDesc, Double liquidity,
				Double balance, User user) {
			super();
			this.investment = investment;
			this.savings = savings;
			this.expense = expense;
			this.expenseDesc = expenseDesc;
			this.liquidity = liquidity;
			this.balance = balance;
			this.user = user;
		}
	    
	public Expenses() {
			super();
		}
	public User getUser() {
			return user;
		}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getInvestment() {
		return investment;
	}
	public void setInvestment(Double investment) {
		this.investment = investment;
	}
	public Double getSavings() {
		return savings;
	}
	public void setSavings(Double savings) {
		this.savings = savings;
	}
	public Double getExpense() {
		return expense;
	}
	public void setExpense(Double expense) {
		this.expense = expense;
	}
	public String getExpenseDesc() {
		return expenseDesc;
	}
	public void setExpenseDesc(String expenseDesc) {
		this.expenseDesc = expenseDesc;
	}
	public Double getLiquidity() {
		return liquidity;
	}
	public void setLiquidity(Double liquidity) {
		this.liquidity = liquidity;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	    
}
