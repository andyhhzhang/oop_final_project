package tracker.model;

/**
 * Represents a stock transaction.
 */
import java.util.Objects;

public class StockTransaction {

    /**
     * Constant representing a buy transaction.
     */
    public static final int BUY = 0;

    /**
     * Constant representing a sell transaction.
     */
    public static final int SELL = 1;

    private  String code;
    private  int amount;
    private  double price;
    private  int type;
    private  int day;

    /**
     * Constructs a new StockTransaction object.
     *
     * @param code   the code of the stock
     * @param amount the amount of stock
     * @param price  the price of the stock
     * @param type   the type of transaction (BUY or SELL)
     * @param day   the date of the transaction
     */
    public StockTransaction(String code, int amount, double price, int type, int day) {
        this.code = code;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.day = day;
    }

    /**
     * Returns the code of the stock.
     *
     * @return the code of the stock
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the stock.
     *
     * @param code the code of the stock
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Returns the amount of stock.
     *
     * @return the amount of stock
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the price of the stock.
     *
     * @return the price of the stock
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the type of transaction.
     *
     * @return the type of transaction (BUY or SELL)
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
     * Sets the amount of stock.
     *
     * @param amount the amount of stock
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Sets the price of the stock.
     *
     * @param price the price of the stock
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the type of transaction.
     *
     * @param type the type of transaction (BUY or SELL)
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
        StockTransaction that = (StockTransaction) o;
        return amount == that.amount && Double.compare(that.price, price) == 0 && type == that.type && day == that.day && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, amount, price, type, day);
    }
}
