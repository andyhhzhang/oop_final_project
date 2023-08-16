package tracker.ui;

import tracker.model.StockTransaction;
import tracker.model.TrackerSystem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the main frame of the Stock Tracker application.
 */
public class StockTrackerFrame extends JFrame {

    private TrackerSystem trackerSystem;

    private JTable transactionTable;
    private JLabel codeLabel;
    private JTextField codeField;

    private JLabel amountLabel;
    private JSpinner amountField;

    private JLabel priceLabel;
    private JSpinner priceField;

    private JComboBox<String> typeCombo;

    private JButton submitButton;
    private JButton deleteButton;

    private TransactionTableModel tableModel;

    /**
     * Creates a new StockTrackerFrame with the specified TracerSystem.
     *
     * @param trackerSystem the TrackerSystem to use
     */
    public StockTrackerFrame(TrackerSystem trackerSystem) {

        this.trackerSystem = trackerSystem;

        transactionTable = new JTable();
        tableModel = new TransactionTableModel();
        transactionTable.setModel(tableModel);

        codeLabel = new JLabel("Stock Code:");
        codeField = new JTextField(10);

        amountLabel = new JLabel("Amount:");
        amountField = new JSpinner();
        amountField.setModel(new SpinnerNumberModel(0, 0, 1000, 1));

        priceLabel = new JLabel("Price:");
        priceField = new JSpinner();
        priceField.setModel(new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.1));

        String[] types = {"Buy", "Sell"};
        typeCombo = new JComboBox<>(types);

        submitButton = new JButton("Add Transaction");
        deleteButton = new JButton("Delete Transaction");

        JPanel inputPanel = new JPanel();
        inputPanel.add(codeLabel);
        inputPanel.add(codeField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeCombo);
        inputPanel.add(submitButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);

        submitButton.addActionListener(e -> {
            String code = codeField.getText();
            int amount = (int) amountField.getValue();
            double price = (double) priceField.getValue();
            int type = typeCombo.getSelectedIndex();

            StockTransaction t = new StockTransaction(code, amount, price, type, trackerSystem.getCurrentDay());

            trackerSystem.addStockTransaction(t);

            updateTableContent();
            transactionTable.updateUI();
        });

        deleteButton.addActionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row >= 0) {
                StockTransaction t = tableModel.get(row);
                trackerSystem.removeStockTransaction(t);

                updateTableContent();
            }
        });

        setTitle("Stock Tracker");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        updateTableContent();

    }

    /**
     * Represents the table model for the transaction table.
     */
    private class TransactionTableModel extends AbstractTableModel {

        private java.util.List<StockTransaction> transactions;

        /**
         * Creates a new TransactionTableModel.
         */
        public TransactionTableModel() {
            this.transactions = new ArrayList<>();
        }

        /**
         * Sets the transactions to be displayed in the table.
         *
         * @param transactions the transactions to be displayed
         */
        public void setTransactions(java.util.List<StockTransaction> transactions) {
            this.transactions = transactions;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return transactions.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0: return "Stock Code";
                case 1: return "Amount";
                case 2: return "Price";
                case 3: return "Type";
                default: return "";
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            StockTransaction transaction = transactions.get(rowIndex);

            switch(columnIndex) {
                case 0: return transaction.getCode();
                case 1: return transaction.getAmount();
                case 2: return transaction.getPrice();
                case 3: return transaction.getType() == StockTransaction.BUY ? "Buy" : "Sell";
                default: return "";
            }
        }

        /**
         * Returns the StockTransaction at the specified index.
         *
         * @param index the index of the StockTransaction
         * @return the StockTransaction at the specified index
         */
        public StockTransaction get(int index) {
            return transactions.get(index);
        }

    }

    /**
     * Updates the content of the transaction table.
     */
    private void updateTableContent() {
        tableModel.setTransactions(trackerSystem.getStockTransactionsByDay(trackerSystem.getCurrentDay()));
        transactionTable.updateUI();
    }

}
