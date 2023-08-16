package tracker.model;

import java.util.Objects;

/**
 * Represents an income transaction.
 */
public class ExpenseTransaction {

    /**
     * Constant representing income type.
     */
    public static final int INCOME = 0;

    /**
     * Constant representing expense type.
     */
    public static final int EXPENSE = 1;

    private String title;
    private  double amount;
    private  int type;
    private  int day;

    /**
     * Constructs a new IncomeTransaction with the specified title, amount, type, and date.
     *
     * @param title the title of the transaction
     * @param amount the amount of the transaction
     * @param type the type of the transaction (INCOME or EXPENSE)
     * @param day the date of the transaction
     */
    public ExpenseTransaction(String title, double amount, int type, int day) {
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.day = day;
    }

    /**
     * Returns the title of the transaction.
     *
     * @return the title of the transaction
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the transaction.
     *
     * @param title the title of the transaction
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the amount of the transaction.
     *
     * @return the amount of the transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the type of the transaction.
     *
     * @return the type of the transaction (INCOME or EXPENSE)
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the date of the transaction.
     *
     * @return the date of the transaction
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the amount of the transaction.
     *
     * @param amount the amount of the transaction
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Sets the type of the transaction.
     *
     * @param type the type of the transaction (INCOME or EXPENSE)
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Sets the date of the transaction.
     *
     * @param day the date of the transaction
     */
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseTransaction that = (ExpenseTransaction) o;
        return Double.compare(that.amount, amount) == 0 && type == that.type && day == that.day && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, amount, type, day);
    }
}
