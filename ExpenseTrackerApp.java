package tmp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.*;
import java.io.*;

public class ExpenseTrackerApp extends JFrame{    
    private ExpenseTracker expenseTracker;
    private ManagePortfolio managePortfolio;
    
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JLabel sumLabel;
    private DecimalFormat df = new DecimalFormat("0.00");
    private int rowHeight = 30;
    private Color backgroundColor = new Color(210, 232, 240);

    public ExpenseTrackerApp(Point location, ExpenseTracker sharedExpenseTracker, ManagePortfolio managePortfolio) {
    	this.expenseTracker = sharedExpenseTracker;
    	this.managePortfolio = managePortfolio;
    	// INITIAL JFRAME SETTINGS
        frame = new JFrame("Expense Tracker");
        frame.setSize(500, 800);
        frame.setLocation(location);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setBackground(backgroundColor);

        tableModel = new DefaultTableModel(new String[]{"Company", "Description", "$ Amount", "Date"}, 0);
        JTable table = new JTable(tableModel);

        // GUI INTERFACE
        JButton addButton = new JButton("Add Expense");
        JButton deleteButton = new JButton("Delete Expense");
        JButton editButton = new JButton("Edit Expense");
        JButton backButton = new JButton("Back");
        sumLabel = new JLabel("  Total: $0");
        JButton exportButton = new JButton("Export");
                
        // BUTTON ACTION LISTENERS
        addButton.addActionListener(e -> {
            addExpense();
            updateTable();
        });

        deleteButton.addActionListener(e -> {
            deleteExpense(table.getSelectedRow());
            updateTable();
        });

        editButton.addActionListener(e -> {
            editExpense(table.getSelectedRow());
            updateTable();
        });
        backButton.addActionListener(e -> goBackToMainApp());
        exportButton.addActionListener(e -> exportToCSV());
        
        // REFRESH TABLE; ENSURE IT IS UP TO DATE
        updateTable();

        // DIFFERENT PANELS
        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new BoxLayout(bottomButtonPanel, BoxLayout.LINE_AXIS));
        bottomButtonPanel.add(backButton);
        bottomButtonPanel.add(addButton);
        bottomButtonPanel.add(Box.createHorizontalGlue());
        bottomButtonPanel.add(deleteButton);
        bottomButtonPanel.add(Box.createHorizontalGlue());
        bottomButtonPanel.add(editButton);
        bottomButtonPanel.setBackground(backgroundColor);
        
        JPanel sumPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sumPanel.add(sumLabel);
        sumPanel.setBackground(backgroundColor);
        
        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exportPanel.add(exportButton);
        exportPanel.setBackground(backgroundColor);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(sumPanel, BorderLayout.WEST); // Add the sumPanel to the left
        topPanel.add(exportPanel, BorderLayout.EAST);
        topPanel.setBackground(backgroundColor);

        // PUTTING IT ALL TOGETHER ON JFRAME
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(bottomButtonPanel, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);
        
        // BUTTON STYLING
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 128, 0));

        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(139, 0, 0));

        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        editButton.setForeground(Color.WHITE);
        editButton.setBackground(Color.ORANGE);
        
        exportButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportButton.setForeground(Color.WHITE);
        exportButton.setBackground(new Color(30, 144, 255));
        
        backButton.setBackground(new Color(173, 216, 230));

        // TABLE STYLING
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.setRowHeight(rowHeight);
        
        // LABEL STYLING
        sumLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sumLabel.setForeground(Color.BLUE);
    }
    
    private void exportToCSV() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            fileChooser.setSelectedFile(new File("expenses.csv"));
            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                FileWriter writer = new FileWriter(fileToSave.getAbsolutePath() + ".csv");
                
                // Write the header
                writer.write("Company,Description,$ Amount,Date\n");

                // Write the data
                for (Expense expense : expenseTracker.getExpenses()) {
                    writer.write(expense.getCompany() + "," +
                                 expense.getDescription() + "," +
                                 df.format(expense.getAmount()) + "," +
                                 expense.getDate() + "\n");
                }
                writer.close();

                JOptionPane.showMessageDialog(frame, "Data exported successfully!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while exporting data!");
        }
    }

    
    private void goBackToMainApp() {
        frame.setVisible(false);
        new MainApp(frame.getLocation(), expenseTracker, managePortfolio);
    }

    private void addExpense() {
        JTextField companyField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Object[] message = {
                "Company:", companyField,
                "Description:", descriptionField,
                "$ Amount:", amountField,
                "Date:", dateField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Add Expense", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String company = companyField.getText();
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();
            expenseTracker.addExpense(new Expense(company, description, amount, date));
        }
    }

    private void deleteExpense(int index) {
        if (index >= 0) {
            expenseTracker.deleteExpense(index);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an expense to delete.");
        }
    }

    private void editExpense(int index) {
        if (index >= 0) {
            Expense expense = expenseTracker.getExpenses().get(index);
            JTextField companyField = new JTextField(expense.getCompany());
            JTextField descriptionField = new JTextField(expense.getDescription());
            JTextField amountField = new JTextField(Double.toString(expense.getAmount()));
            JTextField dateField = new JTextField(expense.getDate());
            Object[] message = {
                    "Company:", companyField,
                    "Description:", descriptionField,
                    "Amount ($):", amountField,
                    "Date:", dateField
            };
            int option = JOptionPane.showConfirmDialog(null, message, "Edit Expense", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String company = companyField.getText();
                String description = descriptionField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String date = dateField.getText();
                expenseTracker.editExpense(index, new Expense(company, description, amount, date));
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an expense to edit.");
        }
    }

    public void updateTable() {
        tableModel.setRowCount(0);
        double totalAmount = 0;
        for (Expense expense : expenseTracker.getExpenses()) {
            tableModel.addRow(new Object[]{expense.getCompany(), expense.getDescription(), df.format(expense.getAmount()), expense.getDate()});
            totalAmount += expense.getAmount();
        }
        sumLabel.setText("  Total: $" + df.format(totalAmount));
    }

}
