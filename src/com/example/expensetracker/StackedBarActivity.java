
package com.example.expensetracker;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;
import com.example.expensetracker.DemoBase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StackedBarActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private BarChart mChart;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bar_chart);
        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        // enable the drawing of values
        mChart.setDrawYValues(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);
        
        MyValueFormatter customFormatter = new MyValueFormatter();
     //   
        // set a custom formatter for the values inside the chart
        mChart.setValueFormatter(customFormatter);
        
        // if false values are only drawn for the stack sum, else each value is drawn
        mChart.setDrawValuesForWholeStack(true);

        // disable 3D
        mChart.set3DEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        // change the position of the y-labels
        YLabels yLabels = mChart.getYLabels();
        yLabels.setPosition(YLabelPosition.BOTH_SIDED);
        yLabels.setLabelCount(5);
        yLabels.setFormatter(customFormatter);

        XLabels xLabels = mChart.getXLabels();
        xLabels.setPosition(XLabelPosition.TOP);
        xLabels.setCenterXLabelText(true);

        // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);

        // setting data

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_RIGHT);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        // mChart.setDrawLegend(false);
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            xVals.add(mMonths[i % mMonths.length]);
        }
//mSeekBarX.getProgress()+1
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 5; i++) {
            float mult = (10);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            float val3 = (float) (Math.random() * mult) + mult / 3;

            yVals1.add(new BarEntry(new float[] {
                     val1, val2, val3
            }, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Statistics Vienna 2014");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setStackLabels(new String[] {
                "Births", "Divorces", "Marriages"
        });

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);

        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }
}
