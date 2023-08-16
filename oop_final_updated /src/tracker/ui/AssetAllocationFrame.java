package tracker.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import tracker.model.TrackerSystem;

import javax.swing.*;
import java.util.Map;

/**
 * Represents a JFrame that displays asset allocation information.
 */
public class AssetAllocationFrame extends JFrame {

    private TrackerSystem trackerSystem;

    /**
     * Constructs an AssetAllocationFrame with the given TrackerSystem.
     *
     * @param trackerSystem the TrackerSystem to display asset allocation information for
     */
    public AssetAllocationFrame(TrackerSystem trackerSystem) {
        this.trackerSystem = trackerSystem;

        JSplitPane splitPane = new JSplitPane();
        add(splitPane);

        splitPane.setLeftComponent(createTotalAssetChartPanel());
        splitPane.setRightComponent(createStockAssetChartPanel());
        splitPane.setContinuousLayout(true);


        setTitle("Asset Allocation");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        splitPane.setDividerLocation(0.5);

    }

    /**
     * Creates a ChartPanel displaying the total asset allocation.
     *
     * @return the ChartPanel displaying the total asset allocation
     */
    private ChartPanel createTotalAssetChartPanel() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Stock", trackerSystem.totalStockPrice());
        dataset.setValue("Cash", trackerSystem.netCash());

        JFreeChart chart = ChartFactory.createPieChart("Total AssetAllocation", dataset);
        return new ChartPanel(chart);
    }


    /**
     * Creates a ChartPanel displaying the stock asset allocation.
     *
     * @return the ChartPanel displaying the stock asset allocation
     */
    private ChartPanel createStockAssetChartPanel() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        Map<String, Double> stockTotals = trackerSystem.getStockTotalPrices();
        for (Map.Entry<String, Double> entry : stockTotals.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart("Stock Asset Allocation", dataset);
        return new ChartPanel(chart);
    }


}
