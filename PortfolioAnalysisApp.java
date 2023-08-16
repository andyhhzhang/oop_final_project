package tmp;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PortfolioAnalysisApp {
    private ExpenseTracker expenseTracker;
    private ManagePortfolio managePortfolio;
    private JFrame frame;

    public PortfolioAnalysisApp(Point location, ExpenseTracker expenseTracker, ManagePortfolio managePortfolio) {
        this.expenseTracker = expenseTracker;
        this.managePortfolio = managePortfolio;

        frame = new JFrame("Portfolio Analysis");
        frame.setSize(500, 800);
        frame.setLocation(location);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel changesPanel = new JPanel();
        changesPanel.setLayout(new BoxLayout(changesPanel, BoxLayout.Y_AXIS));
        
        int netChange = 0;
        boolean anyChange = false;

        for (Map.Entry<String, Integer> entry : managePortfolio.getChangedStocks().entrySet()) {
            int change = entry.getValue();
            netChange += change;

            if (change != 0) {
                anyChange = true;
                JLabel changeLabel = new JLabel(entry.getKey() + ": " + (change > 0 ? "INCREASED by " : "DECREASED by ") + Math.abs(change));
                changesPanel.add(changeLabel);
            }
        }

        JLabel netChangeLabel = new JLabel("Net Change in Stock Holdings: " + (netChange >= 0 ? "+" : "") + netChange);
        netChangeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        if (!anyChange) {
            JLabel noChangeLabel = new JLabel("No changes in your portfolio!");
            noChangeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            changesPanel.add(noChangeLabel);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            managePortfolio.saveCurrentState();
            setVisible(false);
            new MainApp(frame.getLocation(), expenseTracker, managePortfolio);  // pass appropriate ExpenseTracker instance if needed
        });

        frame.add(netChangeLabel, BorderLayout.NORTH);
        frame.add(changesPanel, BorderLayout.CENTER);
        frame.add(backButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
