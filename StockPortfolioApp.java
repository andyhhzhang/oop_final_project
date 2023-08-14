package tmp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StockPortfolioApp {
    private ExpenseTracker expenseTracker;
    private ManagePortfolio managePortfolio;

    private JFrame frame;
    private JLabel portfolioValueLabel;
    private DecimalFormat df = new DecimalFormat("0");

    public StockPortfolioApp(Point location, ExpenseTracker expenseTracker, ManagePortfolio managePortfolio) {
        this.expenseTracker = expenseTracker;
        this.managePortfolio = managePortfolio;

        frame = new JFrame("Stock Portfolio");
        frame.setSize(500, 800);
        frame.setLocation(location);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Portfolio Value Label
        portfolioValueLabel = new JLabel(" # Stocks Owned: " + df.format(managePortfolio.getPortfolioValue()));
        portfolioValueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Export Button
        JButton exportButton = new JButton("Export");
        exportButton.addActionListener((ActionEvent e) -> exportToCSV());
        exportButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        exportButton.setFont(new Font("Arial", Font.PLAIN, 12));
        exportButton.setBackground(new Color(135, 206, 250));
        exportButton.setPreferredSize(new Dimension(100, 30));

        // Create a panel for the header that includes the portfolio value label and the export button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(portfolioValueLabel, BorderLayout.WEST);
        headerPanel.add(exportButton, BorderLayout.EAST);

        // Buy & Sell Options
        JPanel stockPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        int row = 0;
        for (String stockName : managePortfolio.getStockNames()) {
            JButton buyButton = new JButton("Buy " + stockName);
            JButton sellButton = new JButton("Sell " + stockName);
            JLabel quantityLabel = new JLabel("# of " + stockName + " owned: " + managePortfolio.getStockQuantity(stockName));

            // Set preferred size for buttons
            buyButton.setPreferredSize(new Dimension(135, 35));
            sellButton.setPreferredSize(new Dimension(135, 35));

            // Set font and background color for buttons
            buyButton.setFont(new Font("Arial", Font.PLAIN, 14));
            sellButton.setFont(new Font("Arial", Font.PLAIN, 14));
            buyButton.setBackground(new Color(173, 216, 230)); // Light blue color
            sellButton.setBackground(new Color(173, 216, 230)); // Light blue color

            // Action Listeners
            buyButton.addActionListener((ActionEvent e) -> {
                managePortfolio.buyStock(stockName);
                updatePortfolioValue(managePortfolio.getPortfolioValue());
                quantityLabel.setText(String.valueOf("# of " + stockName + " owned: " + managePortfolio.getStockQuantity(stockName)));
            });

            sellButton.addActionListener((ActionEvent e) -> {
                managePortfolio.sellStock(stockName);
                updatePortfolioValue(managePortfolio.getPortfolioValue());
                quantityLabel.setText(String.valueOf("# of " + stockName + " owned: " + managePortfolio.getStockQuantity(stockName)));
            });

            gbc.gridx = 0;
            gbc.gridy = row;
            stockPanel.add(buyButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = row;
            stockPanel.add(sellButton, gbc);

            gbc.gridx = 2;
            gbc.gridy = row;
            stockPanel.add(quantityLabel, gbc);

            row++;
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setBackground(new Color(135, 206, 250));
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener((ActionEvent e) -> {
            setVisible(false);
            new MainApp(frame.getLocation(), expenseTracker, managePortfolio);
        });
        
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(stockPanel, BorderLayout.WEST);
        frame.add(backButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void updatePortfolioValue(double portfolioValue) {
        portfolioValueLabel.setText(" # Stocks Owned: " + df.format(portfolioValue));
    }
    
    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setSelectedFile(new File("portfolio.csv")); // Default file name
        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Write the total portfolio value
                writer.append("Total # of stocks owned" + df.format(managePortfolio.getPortfolioValue()) + "\n");

                // Write the header for the stocks
                writer.append("Stock Name,Quantity\n");

                // Write the number of each stock currently owned
                for (String stockName : managePortfolio.getStockNames()) {
                    writer.append(stockName + "," + managePortfolio.getStockQuantity(stockName) + "\n");
                }

                // Inform the user that the export was successful
                JOptionPane.showMessageDialog(frame, "Exported successfully to " + fileToSave.getAbsolutePath() + "!");
            } catch (IOException e) {
                // Handle any errors during writing
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "An error occurred while exporting the data.");
            }
        }
    }


}
