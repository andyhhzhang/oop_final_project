package tmp;

public class Expense {
    private String company;
    private String description;
    private double amount;
    private String date;

    public Expense(String company, String description, double amount, String date) {
        this.company = company;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
