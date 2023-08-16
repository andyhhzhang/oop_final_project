package tmp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainApp {
    private JFrame frame;
    private ExpenseTracker expenseTracker;
    private ManagePortfolio managePortfolio;

    public MainApp(Point location, ExpenseTracker expenseTracker, ManagePortfolio managePortfolio) {
        this.expenseTracker = expenseTracker;
        this.managePortfolio = managePortfolio;
        
        // MainGUI frame
        frame = new JFrame();
        frame.setTitle("Personal Finance App");
        frame.setSize(500, 800);
        frame.setLocation(location);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(210, 232, 240));
        JLabel titleLabel = new JLabel("Personal Finance App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(50, 60, 70));

        // Track Expenses
        JButton expenseTrackerButton = new JButton("Expense Tracker");
        expenseTrackerButton.setBackground(new Color(0, 123, 255));
        expenseTrackerButton.setForeground(Color.WHITE);
        expenseTrackerButton.setFont(new Font("Arial", Font.PLAIN, 20));

        // View Stock Portfolio
        JButton stockPortfolioButton = new JButton("Stock Portfolio");
        stockPortfolioButton.setBackground(new Color(30, 144, 255));
        stockPortfolioButton.setForeground(Color.WHITE);
        stockPortfolioButton.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // Portfolio Analysis
        JButton portfolioAnalysisButton = new JButton("Portfolio Analysis");
        portfolioAnalysisButton.setBackground(new Color(70, 130, 180)); // Yet another shade of blue
        portfolioAnalysisButton.setForeground(Color.WHITE);
        portfolioAnalysisButton.setFont(new Font("Arial", Font.PLAIN, 20));

        // Event listener for Expense
        expenseTrackerButton.addActionListener((ActionEvent e) -> {
            frame.setVisible(false);
            new ExpenseTrackerApp(frame.getLocation(), expenseTracker, managePortfolio).setVisible(true);
        });
        
        // Event listener for Stock Portfolio
        stockPortfolioButton.addActionListener((ActionEvent e) -> {
        	frame.setVisible(false);
        	new StockPortfolioApp(frame.getLocation(), expenseTracker, managePortfolio).setVisible(true);
        });
        // Event listner for Portfolio Analysis
        portfolioAnalysisButton.addActionListener(e -> {
            frame.setVisible(false);
            new PortfolioAnalysisApp(frame.getLocation(), expenseTracker, managePortfolio).setVisible(true);
        });

        // Button alignment
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(30, 0, 30, 0);
        frame.add(titleLabel, constraints);
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 10, 0);
        frame.add(expenseTrackerButton, constraints);
        constraints.gridy = 2;
        frame.add(stockPortfolioButton, constraints);
        constraints.gridy = 3;
        frame.add(portfolioAnalysisButton, constraints);

        frame.setVisible(true);
    }
    


    public static void main(String[] args) {
        new MainApp(new Point(650, 200), new ExpenseTracker(), new ManagePortfolio());
    }
}
