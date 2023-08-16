package tracker.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * The TrackerSystem class represents a system for tracking income and stock transactions.
 * It provides methods for adding, removing, and retrieving transactions, as well as
 * calculating various financial metrics.
 */
public class TrackerSystem {
    private List<ExpenseTransaction> expenseTransactionList;
    private List<StockTransaction> stockTransactionList;

    private int currentDay;

    /**
     * Constructs a new TrackerSystem object.
     */
    public TrackerSystem() {
        expenseTransactionList = new ArrayList<>();
        stockTransactionList = new ArrayList<>();
    }

    /**
     * Adds an income transaction to the system.
     *
     * @param expenseTransaction the income transaction to be added
     */
    public void addIncomeTransaction(ExpenseTransaction expenseTransaction) {
        expenseTransactionList.add(expenseTransaction);
    }

    /**
     * Adds a stock transaction to the system.
     *
     * @param stockTransaction the stock transaction to be added
     */

    public void addStockTransaction(StockTransaction stockTransaction) {
        stockTransactionList.add(stockTransaction);
    }

    /**
     * Removes an income transaction from the system.
     *
     * @param expenseTransaction the income transaction to be removed
     */

    public void removeIncomeTransaction(ExpenseTransaction expenseTransaction) {
        expenseTransactionList.remove(expenseTransaction);
    }

    /**
     * Removes a stock transaction from the system.
     *
     * @param stockTransaction the stock transaction to be removed
     */

    public void removeStockTransaction(StockTransaction stockTransaction) {
        stockTransactionList.remove(stockTransaction);
    }

    /**
     * Retrieves a list of income transactions for a specific day.
     *
     * @param day the day for which to retrieve income transactions
     * @return a list of income transactions for the specified day
     */
    public List<ExpenseTransaction> getIncomeTransactionsByDay(int day) {
        List<ExpenseTransaction> filtered = new ArrayList<>();
        for (ExpenseTransaction t : expenseTransactionList) {
            if (t.getDay() == day) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Retrieves a list of stock transactions for a specific day.
     *
     * @param day the day for which to retrieve stock transactions
     * @return a list of stock transactions for the specified day
     */
    public List<StockTransaction> getStockTransactionsByDay(int day) {
        List<StockTransaction> filtered = new ArrayList<>();
        for (StockTransaction t : stockTransactionList) {
            if (t.getDay() == day) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Advances the system to the next day.
     */
    public void nextDay() {
        currentDay++;
    }

    /**
     * Retrieves the current day of the system.
     *
     * @return the current day
     */
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * Calculates the total income of the system.
     *
     * @return the total income
     */
    public double totalIncome() {
        double total = 0;
        for (ExpenseTransaction t : expenseTransactionList) {
            if (t.getType() == ExpenseTransaction.INCOME) {
                total += t.getAmount();
            }
        }
        return total;
    }

    /**
     * Calculates the total expense of the system.
     *
     * @return the total expense
     */
    public double totalExpense() {
        double total = 0;
        for (ExpenseTransaction t : expenseTransactionList) {
            if (t.getType() == ExpenseTransaction.EXPENSE) {
                total += t.getAmount();
            }
        }
        return total;
    }

    /**
     * Calculates the net cash of the system.
     *
     * @return the net cash
     */
    public double netCash() {
        return totalIncome() - totalExpense() + totalStockPrice();
    }

    /**
     * Calculates the total price of all stocks in the system.
     *
     * @return the total stock price
     */
    public double totalStockPrice() {
        return totalStockBuyPrice() - totalStockSellPrice();
    }

    /**
     * Calculates the total buy price of all stocks in the system.
     *
     * @return the total stock buy price
     */
    public double totalStockBuyPrice() {
        double total = 0;
        for (StockTransaction t : stockTransactionList) {
            if (t.getType() == StockTransaction.BUY) {
                total += t.getPrice() * t.getAmount();
            }
        }
        return total;
    }

    /**
     * Calculates the total sell price of all stocks in the system.
     *
     * @return the total stock sell price
     */
    public double totalStockSellPrice() {
        double total = 0;
        for (StockTransaction t : stockTransactionList) {
            if (t.getType() == StockTransaction.SELL) {
                total += t.getPrice() * t.getAmount();
            }
        }
        return total;
    }

    /**
     * Retrieves a map of stock codes and their total prices in the system.
     *
     * @return a map of stock codes and their total prices
     */
    public Map<String, Double> getStockTotalPrices() {
        Map<String, Double> stockTotals = new HashMap<>();
        for (StockTransaction t : stockTransactionList) {

            String code = t.getCode();
            double price = t.getPrice();
            int amount = t.getAmount();

            if (t.getType() == StockTransaction.BUY) {
                stockTotals.put(code, stockTotals.getOrDefault(code, 0.0) + amount * price);
            } else if (t.getType() == StockTransaction.SELL) {
                stockTotals.put(code, stockTotals.getOrDefault(code, 0.0) - amount * price);
            }
        }
        return stockTotals;
    }

    /**
     * Calculates the average buy price of a stock within a specified day range.
     *
     * @param code the stock code
     * @param start the start day of the range
     * @param end the end day of the range
     * @return the average buy price of the stock within the specified day range
     */
    private double calcAverageBuyPrice(String code, int start, int end) {

        double totalCost = 0;
        int totalAmount = 0;

        for (int i = start; i <= end; i++) {
            List<StockTransaction> transactions = getStockTransactionsByDay(i);

            for (StockTransaction t : transactions) {
                if (t.getCode().equals(code) && t.getType() == StockTransaction.BUY) {
                    totalCost += t.getPrice() * t.getAmount();
                    totalAmount += t.getAmount();
                }
            }
        }

        if (totalAmount == 0) {
            return 0;
        }

        return totalCost / totalAmount;

    }

    /**
     * Calculates the investment gain of a stock within a specified day range.
     *
     * @param code the stock code
     * @param start the start day of the range
     * @param end the end day of the range
     * @return the investment gain of the stock within the specified day range
     */
    public double getInvestmentGain(String code, int start, int end) {
        double buyCost = calcAverageBuyPrice(code, start, end);
        double gain = 0;
        for (int i = start; i <= end; i++) {
            List<StockTransaction> transactions = getStockTransactionsByDay(i);
            for (StockTransaction t : transactions) {
                if (t.getCode().equals(code)) {
                    if (t.getType() == StockTransaction.SELL) {
                        double sellValue = t.getPrice() * t.getAmount();
                        double buyValue = buyCost * t.getAmount();
                        gain += sellValue - buyValue;
                    }
                }
            }
        }
        return gain;
    }

    /**
     * Retrieves a list of stock codes that were traded within a specified day range.
     *
     * @param start the start day of the range
     * @param end the end day of the range
     * @return a list of stock codes that were traded within the specified day range
     */
    public List<String> getTradeStockCodes(int start, int end) {
        List<String> codes = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<StockTransaction> transactions = getStockTransactionsByDay(i);
            for (StockTransaction t : transactions) {
                if (t.getType() == StockTransaction.SELL) {
                    if (!codes.contains(t.getCode())) {
                        codes.add(t.getCode());
                    }
                }
            }
        }
        codes.sort(null);
        return codes;
    }

    /**
     * Saves the income transactions to a CSV file.
     *
     * @param csvFile the path of the CSV file to save to
     */
    public void saveIncomeTransactionsToCsv(String csvFile) {
        try (PrintWriter writer = new PrintWriter(csvFile)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Title");
            sb.append(",");
            sb.append("Amount");
            sb.append(",");
            sb.append("Type");
            sb.append(",");
            sb.append("Day");
            sb.append("\n");

            for (ExpenseTransaction t : expenseTransactionList) {
                sb.append(t.getTitle());
                sb.append(",");
                sb.append(t.getAmount());
                sb.append(",");
                sb.append(t.getType());
                sb.append(",");
                sb.append(t.getDay());
                sb.append("\n");
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Loads the income transactions from a CSV file.
     *
     * @param csvFile the path of the CSV file to load from
     */
    public void loadIncomeTransactionsFromCsv(String csvFile) {
        expenseTransactionList.clear();

        try (Scanner scanner = new Scanner(new File(csvFile))) {
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                String title = values[0];
                double amount = Double.parseDouble(values[1]);
                int type = Integer.parseInt(values[2]);
                int day = Integer.parseInt(values[3]);

                ExpenseTransaction t = new ExpenseTransaction(title, amount, type, day);
                expenseTransactionList.add(t);
            }

        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Saves the stock transactions to a CSV file.
     *
     * @param csvFile the path of the CSV file to save to
     */
    public void saveStockTransactionsToCsv(String csvFile) {

        try (PrintWriter writer = new PrintWriter(csvFile)) {

            StringBuilder sb = new StringBuilder();
            sb.append("Code");
            sb.append(",");
            sb.append("Amount");
            sb.append(",");
            sb.append("Price");
            sb.append(",");
            sb.append("Type");
            sb.append(",");
            sb.append("Day");
            sb.append("\n");

            for (StockTransaction t : stockTransactionList) {
                sb.append(t.getCode());
                sb.append(",");
                sb.append(t.getAmount());
                sb.append(",");
                sb.append(t.getPrice());
                sb.append(",");
                sb.append(t.getType());
                sb.append(",");
                sb.append(t.getDay());
                sb.append("\n");
            }

            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
        }

    }

    /**
     * Loads the stock transactions from a CSV file.
     *
     * @param csvFile the path of the CSV file to load from
     */
    public void loadStockTransactionsFromCsv(String csvFile) {

        stockTransactionList.clear();

        try (Scanner scanner = new Scanner(new File(csvFile))) {

            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                String code = values[0];
                int amount = Integer.parseInt(values[1]);
                double price = Double.parseDouble(values[2]);
                int type = Integer.parseInt(values[3]);
                int day = Integer.parseInt(values[4]);

                StockTransaction t = new StockTransaction(code, amount, price, type, day);
                stockTransactionList.add(t);
            }

        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Saves the current day to a file.
     *
     * @param dayFile the path of the file to save to
     */
    public void saveDay(String dayFile) {
        try (PrintWriter writer = new PrintWriter(dayFile)) {
            writer.println(currentDay);
        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Loads the current day from a file.
     *
     * @param dayFile the path of the file to load from
     */
    public void loadDay(String dayFile) {
        currentDay = 0;
        try (Scanner scanner = new Scanner(new File(dayFile))) {
            currentDay = scanner.nextInt();
        } catch (Exception e) {
        }
    }

    /**
     * Saves the system data to a file.
     */
    public void save() {
        saveIncomeTransactionsToCsv("incomes.csv");
        saveStockTransactionsToCsv("stocks.csv");
        saveDay("day.txt");
    }

    /**
     * Loads the system data from a file.
     */
    public void load() {
        loadIncomeTransactionsFromCsv("incomes.csv");
        loadStockTransactionsFromCsv("stocks.csv");
        loadDay("day.txt");
    }

    /**
     * Sets the current day of the system.
     *
     * @param day the new current day
     */
    public void setCurrentDay(int day) {
        currentDay = day;
    }
}
