package tan.k.view;

import java.awt.BorderLayout;
import java.util.Calendar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;

/**
 *
 * @author ricardo
 */
public class GraphPanel extends javax.swing.JPanel {

    String title = null;
    String xAxisLabel = null;
    String yAxisLabel = null;
    private final String s1Name = "Tank 1";
    private final String s2Name = "Tank 2";
    private final String s3Name = "Sinal";
//    private final String s4Name = null;
    private XYSeries s1 = new XYSeries(s1Name);
    private XYSeries s2 = new XYSeries(s2Name);
    private XYSeries s3 = new XYSeries(s3Name);
//    private XYSeries s4 = new XYSeries(s4Name);
//    TimeSeries s1 = new TimeSeries("s1");
//    TimeSeries s2 = new TimeSeries("s2");
//    TimeSeries s3 = new TimeSeries("s3");
    private FixedMillisecond iniTime;
//    TimeSeriesCollection collection = new TimeSeriesCollection();
    XYSeriesCollection collection = new XYSeriesCollection();
    JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, collection, PlotOrientation.VERTICAL, true, true, true);
    ChartPanel chartPanel = new ChartPanel(chart);
    LegendTitle legend = chart.getLegend();

    public GraphPanel() {
        setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        legend.setPosition(RectangleEdge.RIGHT);
        add(chartPanel);
        this.setVisible(true);
        this.revalidate();
    }

    public void addValue(String serie, double x, double y) {
        switch (serie) {
            case s1Name:
                s1.addOrUpdate(x, y);
                break;
            case s2Name:
                s2.addOrUpdate(x, y);
                break;
            case s3Name:
                s3.addOrUpdate(x, y);
                break;
//            case s4Name:
//                s4.addOrUpdate(x, y);
//                break;
            default:
                break;
        }
    }

    public void addValue(String serie, float value) {
        FixedMillisecond currentTime = new FixedMillisecond(Calendar.getInstance().getTimeInMillis());
        double time = currentTime.getFirstMillisecond() - iniTime.getFirstMillisecond(); //tempo em milisegundo
        time = time / 1000; // tempo em segundo
        addValue(serie, time, value);
    }

    public void addSerie(String serie) {
        switch (serie) {
            case s1Name:
                s1 = new XYSeries(s1Name);
                collection.addSeries(s1);
                break;
            case s2Name:
                s2 = new XYSeries(s2Name);
                collection.addSeries(s2);
                break;
            case s3Name:
                s3 = new XYSeries(s3Name);
                collection.addSeries(s3);
                break;
//            case s4Name:
//                s4 = new XYSeries(s4Name);
//                collection.addSeries(s4);
//                break;
            default:
                break;
        }
    }

    public void removeSerie(String serie) {
        switch (serie) {
            case s1Name:
                collection.removeSeries(s1);
                break;
            case s2Name:
                collection.removeSeries(s2);
                break;
            case s3Name:
                collection.removeSeries(s3);
                break;
//            case s4Name:
//                collection.removeSeries(s4);
//                break;
            default:
                break;
        }
    }

    public void startTime() {
        iniTime = new FixedMillisecond(Calendar.getInstance().getTimeInMillis());
    }

    public void setTitle(String title) {
        this.title = title;
        remove(chartPanel);
        chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, collection, PlotOrientation.VERTICAL, true, true, true);
        chartPanel = new ChartPanel(chart);
        legend = chart.getLegend();
        //GraphPanel()
        setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        legend.setPosition(RectangleEdge.RIGHT);
        add(chartPanel);
        this.setVisible(true);
        this.revalidate();
    }

    public void setxAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
        remove(chartPanel);
        chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, collection, PlotOrientation.VERTICAL, true, true, true);
        chartPanel = new ChartPanel(chart);
        legend = chart.getLegend();
        //GraphPanel()
        setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        legend.setPosition(RectangleEdge.RIGHT);
        add(chartPanel);
        this.setVisible(true);
        this.revalidate();
    }

    public void setyAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
        remove(chartPanel);
        chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, collection, PlotOrientation.VERTICAL, true, true, true);
        chartPanel = new ChartPanel(chart);
        legend = chart.getLegend();
        //GraphPanel()
        setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        legend.setPosition(RectangleEdge.RIGHT);
        add(chartPanel);
        this.setVisible(true);
        this.revalidate();
    }
}