package tan.k.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;


/**
 *
 * @author ricardo
 */
public class GraphPanel extends javax.swing.JPanel {

    private XYSeriesCollection collection;
    private List<String> seriesNames;
    private ChartPanel chartPanel;

    public GraphPanel() {

        setLayout(new BorderLayout());
        //Create collection of Series
        collection = new XYSeriesCollection();
        seriesNames = new ArrayList<>();
        //create chart Panel to add Chart and add it to Panel
        chartPanel = new ChartPanel(_defaultChart());
        _getChart().getLegend().setPosition(RectangleEdge.TOP);
        _getChart().setAntiAlias(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        
        chartPanel.getChart().getXYPlot().setDomainCrosshairVisible(true);
        chartPanel.getChart().getXYPlot().setRangeCrosshairVisible(true);
        
        add(chartPanel);
    }

    private JFreeChart _defaultChart() {
        return ChartFactory.createXYLineChart("", "", "", collection, PlotOrientation.VERTICAL, true, true, true);
    }

    private JFreeChart _getChart() {
        return chartPanel.getChart();
    }

    private XYSeries _getSerie(String serie) {
        return collection.getSeries(seriesNames.indexOf(serie));
    }

    public void addSerie(String serie) {
        seriesNames.add(serie);
        collection.addSeries(new XYSeries(serie));
    }

    public void addSerieIfInexistent(String serie) {
        if (!seriesExists(serie)) {
            addSerie(serie);
        }
    }

    public void removeSerie(String serie) {
        if (seriesExists(serie)) {
            collection.removeSeries(seriesNames.indexOf(serie));
            seriesNames.remove(serie);
        }
    }

    public void clean(String serie) {
        collection.getSeries(seriesNames.indexOf(serie)).clear();
    }

    public void clearAll() {
        for (int i = 0; i < seriesNames.size(); i++) {
            collection.getSeries(i).clear();
        }
    }

    public void removeAllSeries() {
        seriesNames.removeAll(seriesNames);
        collection.removeAllSeries();
    }

    public boolean seriesExists(String serie) {
        return seriesNames.indexOf(serie) >= 0;
    }

    public void addValue(String serie, double x, double y) {
        if (!seriesExists(serie)) addSerie(serie);
        _getSerie(serie).addOrUpdate(x, y);
    }

    public void setTitle(String title) {
        _getChart().setTitle(title);
    }

    public void setxAxisLabel(String xAxisLabel) {
        ((XYPlot) _getChart().getPlot()).getDomainAxis().setLabel(xAxisLabel);
    }

    public void setyAxisLabel(String yAxisLabel) {
        ((XYPlot) _getChart().getPlot()).getRangeAxis().setLabel(yAxisLabel);
    }

    public void disableLegend() {
        _getChart().getLegend().setVisible(false);
    }

    public void enableLegend() {
        _getChart().getLegend().setVisible(false);
    }
}