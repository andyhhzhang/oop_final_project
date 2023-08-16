package tracker.ui;

import tracker.model.ExpenseTransaction;
import tracker.model.TrackerSystem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the user interface for managing income transactions in the Tracker system.
 */
public class ExpenseTrackerFrame extends JFrame {

    private TrackerSystem trackerSystem;
    private JTable transactionTable;
    private JLabel titleLabel;
    private JTextField titleField;

    private JLabel amountLabel;
    private JSpinner amountField;
    private JComboBox<String> typeCombo;

    private JButton submitButton;
    private JButton deleteButton;
    private TransactionTableModel tableModel;

    /**
     * Constructs an IncomeTrackerFrame with the specified TracerSystem.
     *
     * @param trackerSystem the TrackerSystem to be used
     */
    public ExpenseTrackerFrame(TrackerSystem trackerSystem) {
        this.trackerSystem = trackerSystem;

        // Create components
        transactionTable = new JTable();
        tableModel = new TransactionTableModel();
        transactionTable.setModel(tableModel);
        titleLabel = new JLabel("Title: ");
        titleField = new JTextField(10);
        amountLabel = new JLabel("Amount: ");
        amountField = new JSpinner();
        SpinnerNumberModel amountModel = new SpinnerNumberModel();
        amountField.setModel(amountModel);
        amountField.setPreferredSize(new Dimension(100, 30));
        amountModel.setMinimum(0);
        amountModel.setMaximum(1000000);
        String[] types = {"Income", "Expense"};
        typeCombo = new JComboBox<>(types);
        submitButton = new JButton("Add Transaction");
        deleteButton = new JButton("Delete Transaction");

        // Layout components
        JPanel inputPanel = new JPanel();
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Type: "));
        inputPanel.add(typeCombo);
        inputPanel.add(submitButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);


        // Event handling
        submitButton.addActionListener(e -> {
            // Get input values
            String title = titleField.getText();
            int amount = Integer.parseInt(amountField.getValue().toString());
            int type = typeCombo.getSelectedIndex();
            // Create transaction
            ExpenseTransaction t = new ExpenseTransaction(title, amount, type, trackerSystem.getCurrentDay());
            // Add to system
            trackerSystem.addIncomeTransaction(t);
            // Update table
            updateTableContent();
            transactionTable.updateUI();
        });

        deleteButton.addActionListener(e -> {
            // Get selected row
            int row = transactionTable.getSelectedRow();
            if (row >= 0) {
                // Remove transaction
                ExpenseTransaction item = tableModel.get(row);
                trackerSystem.removeIncomeTransaction(item);
                // Update table
                updateTableContent();
                transactionTable.updateUI();
            }
        });
        updateTableContent();

        // Configure frame
        setTitle("Expense Tracker");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Updates the content of the transaction table.
     */
    public void updateTableContent() {
        tableModel.setTransactions(trackerSystem.getIncomeTransactionsByDay(trackerSystem.getCurrentDay()));
        transactionTable.setModel(tableModel);

    }

    /**
     * Represents the table model for the transaction table.
     */
    class TransactionTableModel extends AbstractTableModel {

        private java.util.List<ExpenseTransaction> transactions;

        /**
         * Constructs a TransactionTableModel.
         */
        public TransactionTableModel() {
            this.transactions = new ArrayList<>();
        }

        /**
         * Sets the list of income transactions.
         *
         * @param transactions the list of income transactions
         */
        public void setTransactions(java.util.List<ExpenseTransaction> transactions) {
            this.transactions = transactions;
            fireTableDataChanged();
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Title";
            } else if (column == 1) {
                return "Type";
            } else if (column == 2) {
                return "Amount";
            }
            return "";
        }

        /**
         * Returns the income transaction at the specified index.
         *
         * @param index the index of the income transaction
         * @return the income transaction at the specified index
         */
        public ExpenseTransaction get(int index) {
            if (index >= 0 && index < transactions.size()) {
                return transactions.get(index);
            } else {
                return null;
            }
        }

        @Override
        public int getRowCount() {
            return transactions.size();
        }
        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex >= 0 && rowIndex < getRowCount()) {
                if (columnIndex >= 0 && columnIndex < getColumnCount()) {
                    if (columnIndex == 0) {
                        return transactions.get(rowIndex).getTitle();
                    } else if (columnIndex == 1) {
                        if (transactions.get(rowIndex).getType() == ExpenseTransaction.INCOME) {
                            return "Income";
                        } else {
                            return "Expense";
                        }
                    } else if (columnIndex == 2) {
                        return transactions.get(rowIndex).getAmount();

                    }
                }
            }
            return null;
        }
    }


}

