package tan.k.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleEdge;


/**
 *
 * @author ricardo
 */
public class GraphPanel extends javax.swing.JPanel {

    private TimeSeriesCollection collection;
    private List<String> seriesNames;
    private ChartPanel chartPanel;

    public GraphPanel() {

        setLayout(new BorderLayout());
        //Create collection of Series
        collection = new TimeSeriesCollection();
        seriesNames = new ArrayList<>();
        //create chart Panel to add Chart and add it to Panel
        setPanelBoundaries(-5, 5);
    }
    
    public final void setPanelBoundaries(double y_min, double y_max){
      chartPanel = new ChartPanel(_defaultChart(y_max, y_min));
      _getChart().getLegend().setPosition(RectangleEdge.TOP);
      _getChart().setAntiAlias(true);
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

      chartPanel.getChart().getXYPlot().setDomainCrosshairVisible(true);
      chartPanel.getChart().getXYPlot().setRangeCrosshairVisible(true);
      
      chartPanel.getChart().getXYPlot().setBackgroundPaint(Color.white);
      chartPanel.getChart().getXYPlot().setRangeGridlinePaint(Color.gray);
      chartPanel.getChart().getXYPlot().setDomainGridlinePaint(Color.gray);

      add(chartPanel);
    }
    
    private JFreeChart _defaultChart(double y_max, double y_min) {
      final JFreeChart result = ChartFactory.createTimeSeriesChart("", "", "", collection, true, true, false);
      final XYPlot plot = result.getXYPlot();
      ValueAxis axis = plot.getDomainAxis();
      axis.setAutoRange(true);
      axis.setFixedAutoRange(150000.0);
      axis.setVisible(true);
      axis = plot.getRangeAxis();
      axis.setRange(y_min, y_max); 
      return result;
    }
    
    private JFreeChart _getChart() {
        return chartPanel.getChart();
    }

    private TimeSeries _getSerie(String serie) {
        return collection.getSeries(seriesNames.indexOf(serie));
    }

    public void addSerie(String serie) {
        seriesNames.add(serie);
        collection.addSeries(new TimeSeries(serie));
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

    public void addValue(String serie, Millisecond t, double y) {
        if (!seriesExists(serie)) addSerie(serie);
        try{
          _getSerie(serie).add(t, y);//addOrUpdate(x, y);
        }catch(Exception e){}
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