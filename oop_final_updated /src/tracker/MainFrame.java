package tracker;

import tracker.model.TrackerSystem;
import tracker.ui.AssetAllocationFrame;
import tracker.ui.ExpenseTrackerFrame;
import tracker.ui.InvestmentFrame;
import tracker.ui.StockTrackerFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The MainFrame class represents the main user interface for the Tracker application.
 * It provides buttons and panels for accessing different features of the application.
 */
public class MainFrame extends JFrame {

    private JButton incomeButton;
    private JButton stockButton;
    private JButton assetButton;
    private JButton investmentButton;
    private JButton nextDayButton;
    private JLabel dayLabel;


    private JPanel dayPanel;
    private JSpinner daySpinner;
    private JButton daySetButton;


    private TrackerSystem system;

    /**
     * Creates a new instance of MainFrame.
     */
    public MainFrame() {

        // Initialize the TrackerSystem
        system = new TrackerSystem();
        system.load();

        // Set the layout of the frame to BorderLayout
        setLayout(new BorderLayout());

        // Create a label for displaying the current date
        dayLabel = new JLabel();

        // Create buttons for different functionalities
        incomeButton = new JButton("Expense");
        stockButton = new JButton("Stock");
        assetButton = new JButton("Asset");
        investmentButton = new JButton("Investment");
        nextDayButton = new JButton("Next Day");

        // Create a spinner and a button for setting the date
        daySpinner = new JSpinner();
        daySetButton = new JButton("Set");

        // Set the layout of the frame to GridLayout with 6 rows and 1 column
        setLayout(new GridLayout(0, 1, 0, 10));

        // Add components to the frame
        add(dayLabel);
        add(incomeButton);
        add(stockButton);
        add(assetButton);
        add(investmentButton);
        add(nextDayButton);

        // Create a panel for the date spinner and set button
        dayPanel = new JPanel();
        dayPanel.add(new JLabel("Set Day: "));
        dayPanel.add(daySpinner);
        dayPanel.add(daySetButton);
        add(dayPanel);

        // Set the preferred size of the buttons
        incomeButton.setPreferredSize(new Dimension(100, 40));
        stockButton.setPreferredSize(new Dimension(100, 40));
        assetButton.setPreferredSize(new Dimension(100, 40));
        investmentButton.setPreferredSize(new Dimension(100, 40));
        nextDayButton.setPreferredSize(new Dimension(100, 40));

        // Add action listeners to the buttons
        incomeButton.addActionListener(e -> {
            ExpenseTrackerFrame incomeFrame = new ExpenseTrackerFrame(system);
            incomeFrame.setTitle("Expense Tracker - Current Day: " + system.getCurrentDay());
            incomeFrame.setVisible(true);
        });

        stockButton.addActionListener(e -> {
            StockTrackerFrame stockFrame = new StockTrackerFrame(system);
            stockFrame.setTitle("Stock Tracker - Current Day: " + system.getCurrentDay());
            stockFrame.setVisible(true);
        });

        assetButton.addActionListener(e -> {
            AssetAllocationFrame assetFrame = new AssetAllocationFrame(system);
            assetFrame.setTitle("Asset allocation charts - Current Day: " + system.getCurrentDay());
            assetFrame.setVisible(true);
        });

        investmentButton.addActionListener(e -> {
            InvestmentFrame investmentFrame = new InvestmentFrame(system);
            investmentFrame.setTitle("Stock investment charts - Current Day: " + system.getCurrentDay());
            investmentFrame.setVisible(true);
        });

        nextDayButton.addActionListener(e -> {
            system.nextDay();
            dayLabel.setText(getCurrentDayString(system.getCurrentDay()));
        });

        // Set the initial text of the date label
        dayLabel.setText(getCurrentDayString(system.getCurrentDay()));

        // Set the size of the frame
        setSize(new Dimension(200, 400));

        // Set the default close operation of the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add a window listener to the frame to save the system when the frame is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                system.save();
            }
        });

        // Add an action listener to the date set button
        daySetButton.addActionListener(e -> {
            int day = (int) daySpinner.getValue();
            system.setCurrentDay(day);
            updateDayLabel();
        });

    }

    /**
     * Returns the current date as a formatted string.
     *
     * @param currentDay The current date.
     * @return The current date as a formatted string.
     */
    private String getCurrentDayString(int currentDay) {
        return "Current Day: " + currentDay;
    }

    /**
     * Updates the date label with the current date.
     */
    private void updateDayLabel() {
        dayLabel.setText(getCurrentDayString(system.getCurrentDay()));
    }

    /**
     * The main method that starts the Tracker application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

}
