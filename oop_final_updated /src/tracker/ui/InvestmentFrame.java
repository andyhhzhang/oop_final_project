package tracker.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import tracker.model.TrackerSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * InvestmentFrame is a JFrame that displays investment information and charts.
 */
public class InvestmentFrame extends JFrame {

    private TrackerSystem trackerSystem;

    private JSpinner startSpinner;
    private JSpinner endSpinner;

    private JButton showButton;

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private JLabel totalProfitLabel;

    /**
     * Constructs an InvestmentFrame with the given TrackerSystem.
     *
     * @param trackerSystem the TrackerSystem to use for retrieving investment data
     */
    public InvestmentFrame(TrackerSystem trackerSystem) {
        this.trackerSystem = trackerSystem;

        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        toolbar.add(new JLabel("Start Day(Inclusive):"));
        startSpinner = new JSpinner();
        startSpinner.setPreferredSize(new Dimension(100, 30));
        toolbar.add(startSpinner);

        toolbar.add(new JLabel("End Day(Inclusive):"));
        endSpinner = new JSpinner();
        endSpinner.setPreferredSize(new Dimension(100, 30));
        toolbar.add(endSpinner);

        showButton = new JButton("Show");
        toolbar.add(showButton);

        add(toolbar, BorderLayout.NORTH);

        chart = ChartFactory.createBarChart("Investment Gain", "Stock", "Gain", null);
        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        totalProfitLabel = new JLabel();
        add(totalProfitLabel, BorderLayout.SOUTH);


        showButton.addActionListener(e -> {
            updateChart();
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        updateChart();
    }

    /**
     * Updates the chart with the selected date range.
     */
    private void updateChart() {
        int start = (int) startSpinner.getValue();
        int end = (int) endSpinner.getValue();
        List<String> codes = trackerSystem.getTradeStockCodes(start, end);
        List<Double> gains = new ArrayList<>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double totalProfit = 0;

        for (String code: codes) {
            double gain = trackerSystem.getInvestmentGain(code, start, end);
            gains.add(gain);
            dataset.addValue(gain, code, "value");
            totalProfit += gain;
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Investment portfolio",
                "Category", "Value",
                dataset,
                PlotOrientation.HORIZONTAL,
                true, true, false);
        chartPanel.setChart(chart);
        totalProfitLabel.setText("Total profit: " + totalProfit);
    }

}
