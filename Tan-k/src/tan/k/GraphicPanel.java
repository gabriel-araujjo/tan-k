/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tan.k;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Calendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Teles
 */
public class GraphicPanel extends JPanel {

    private ChartPanel pnlChart;
    private TimeSeries timeSeries;
    private long maximunVisiblePeriod;
    private FixedMillisecond oldestPoint;
    private Lock lock = new ReentrantLock();
    private static long DEFAULT_MAXIMUN_VISIBLE_PERIOD = 10 * 1000;
//    private static long DEFAULT_MAXIMUN_VISIBLE_PERIOD = 10 * 60 * 1000;

    public GraphicPanel() {
        this("", "", "", DEFAULT_MAXIMUN_VISIBLE_PERIOD);
    }

    public GraphicPanel(long maximunVisibleWindow) {
        this("", "", "", maximunVisibleWindow);
    }

    public GraphicPanel(String title, String timeAxisLabel, String valueAxisLabel, long maximunVisibleWindow) {
        this.maximunVisiblePeriod = maximunVisibleWindow;

        setLayout(new BorderLayout());
        timeSeries = new TimeSeries("tt");
        timeSeries.removeAgedItems(true);

        JFreeChart jfreechart = createChart(new TimeSeriesCollection(timeSeries), title, timeAxisLabel, valueAxisLabel);
        pnlChart = new ChartPanel(jfreechart);
        pnlChart.setBackground(new Color(247, 247, 247));
        pnlChart.setVisible(true);
        add(pnlChart, BorderLayout.CENTER);
    }

    public void addValue(float value) {
        try {
            lock.lock();
            FixedMillisecond currentTime = new FixedMillisecond(Calendar.getInstance().getTimeInMillis());
            addValue(value, currentTime);
        } finally {
            lock.unlock();
        }
    }

    public void addValue(float value, FixedMillisecond time) {
        try {
            lock.lock();
            TimeSeriesDataItem newTimeSeriesDataItem = new TimeSeriesDataItem(time, value);

            timeSeries.addOrUpdate(newTimeSeriesDataItem);
            pnlChart.revalidate();
            pnlChart.repaint();

            if (oldestPoint == null) {
                oldestPoint = time;
            }

            new SwingWorker<Object, Object>() {
                @Override
                protected Object doInBackground() throws Exception {
                    return "";
                }

                @Override
                protected void done() {
                    removeAgedPoints();
                }
            }.execute();
        } finally {
            lock.unlock();
        }
    }

    private synchronized void removeAgedPoints() {
        try {
            lock.lock();
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - oldestPoint.getTime().getTime() > maximunVisiblePeriod) {
                final long oldestValidTime = (currentTime - maximunVisiblePeriod);

                for (int i = 0; i < timeSeries.getItemCount(); i++) {
                    if (timeSeries.getTimePeriod(i).getStart().getTime() < oldestValidTime) {
                        timeSeries.delete(0, i);
                        --i;
                    } else {
                        break;
                    }
                }


                if (timeSeries.getItemCount() > 0) {
                    oldestPoint = new FixedMillisecond(timeSeries.getTimePeriod(0).getStart());
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private JFreeChart createChart(XYDataset xydataset, String title, String timeAxisLabel, String valueAxisLabel) {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, timeAxisLabel, valueAxisLabel, xydataset, false, true, false);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();

        xyplot.setBackgroundPaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.gray);
        xyplot.setDomainGridlinePaint(Color.gray);
        xyplot.setOutlineVisible(false);
        xyplot.setInsets(new RectangleInsets(0, 5, 5, 0));

        xyplot.setDomainPannable(true);
        xyplot.setRangePannable(false);
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);

        jfreechart.setBackgroundPaint(Color.WHITE);

        return jfreechart;
    }

    public long getMaximunVisiblePeriod() {
        return maximunVisiblePeriod;
    }

    public void setMaximunVisiblePeriod(long maximunVisiblePeriod) {
        this.maximunVisiblePeriod = maximunVisiblePeriod;
    }
}
