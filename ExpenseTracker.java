package tmp;

import java.util.*;

public class ExpenseTracker {
    private ArrayList<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void deleteExpense(int index) {
        expenses.remove(index);
    }

    public void editExpense(int index, Expense expense) {
        expenses.set(index, expense);
    }

    public double getSumOfExpenses() {
        double sum = 0;
        for (Expense expense : expenses) {
            sum += expense.getAmount();
        }
        return sum;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
}

