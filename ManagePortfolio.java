package tmp;

import java.util.HashMap;
import java.util.Map;

public class ManagePortfolio {
    private final Map<String, Integer> stocks;
    private double portfolioValue = 0.0;

    public ManagePortfolio() {
        stocks = new HashMap<>();
        stocks.put("Apple", 0);
        stocks.put("Google", 0);
        stocks.put("Microsoft", 0);
        stocks.put("Amazon", 0);
        stocks.put("Meta", 0);
        stocks.put("Nvidia", 0);
        stocks.put("Tesla", 0);
        stocks.put("IBM", 0);
        stocks.put("Intel", 0);
        stocks.put("Oracle", 0);
        stocks.put("Salesforce", 0);
        stocks.put("Adobe", 0);
        stocks.put("Netflix", 0);
        stocks.put("Uber", 0);
        stocks.put("Paypal", 0);
        stocks.put("Snowflake", 0);
    }

    public String[] getStockNames() {
        return stocks.keySet().toArray(new String[0]);
    }

    public void buyStock(String stockName) {
        stocks.put(stockName, stocks.get(stockName) + 1);
        portfolioValue += 1;
    }

    public void sellStock(String stockName) {
        int currentQuantity = stocks.get(stockName);
        if (currentQuantity > 0) {
            stocks.put(stockName, currentQuantity - 1);
            portfolioValue -= 1;
        }
    }

    public int getStockQuantity(String stockName) {
        return stocks.get(stockName);
    }
    
    public double getPortfolioValue() {
        return portfolioValue;
    }
}
