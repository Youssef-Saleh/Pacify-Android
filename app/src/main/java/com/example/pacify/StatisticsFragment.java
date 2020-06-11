package com.example.pacify;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adham Mahmoud
 * @version 1
 * This class (fragment) shows bar chart that include (likes last day/month/year and
 * listeners last day/month/year), Likes and Listeners arrays hold the data to be shown
 * in the order { last day, last month , last year}.
 * The graph has a white layout and green bars.
 * This class is currently not in use as the graph is implemented inside Artist Home fragment.
 */
public class StatisticsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        BarChart barChart = view.findViewById(R.id.bar_chart);


        int[] Likes = {20,60,100};
        int[] Listeners = {40,80,125};

        final String[] labels = new String[] {"Last Day", "Last Month", "Last Year"};

        // Adjusting the y-axis
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setTextColor(Color.WHITE);
        yAxisLeft.setTextSize(12);
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.setTextSize(12);


        // Adjusting the x-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);
        xAxis.setGranularity(0f);
        xAxis.setGridColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(3f);
        xAxis.setLabelCount(3);
        xAxis.setCenterAxisLabels(true);


        // Adjusting description
        Description description = barChart.getDescription();
        description.setEnabled(true);
        description.setText("Stats");
        description.setTextSize(18);
        description.setTextColor(Color.WHITE);
        description.setPosition(230, 60);


        // Filling the lists
        List<BarEntry> entriesGroup1 = new ArrayList<>();
        entriesGroup1.add(new BarEntry(0f, Likes[0]));
        entriesGroup1.add(new BarEntry(1f, Likes[1]));
        entriesGroup1.add(new BarEntry(2f, Likes[2]));

        List<BarEntry> entriesGroup2 = new ArrayList<>();
        entriesGroup2.add(new BarEntry(0f, Listeners[0]));
        entriesGroup2.add(new BarEntry(1f, Listeners[1]));
        entriesGroup2.add(new BarEntry(2f, Listeners[2]));


        // Adjusting the lists
        BarDataSet likesSet = new BarDataSet(entriesGroup1, "Likes");
        likesSet.setColor(Color.rgb(102,205,0));
        BarDataSet ListenersSet = new BarDataSet(entriesGroup2, "Listeners");
        ListenersSet.setColor(Color.rgb(69,110,0));


        // Adjusting the sizing
        float groupSpace = 0.10f;
        float barSpace = 0.03f; // x2 dataset
        float barWidth = 0.42f; // x2 dataset
        // (0.03 + 0.42) * 2 + 0.10 = 1.00 -> interval per "group"
        BarData data = new BarData(likesSet, ListenersSet);
        data.setBarWidth(barWidth); // set the width of each bar
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(12);
        barChart.setData(data);
        barChart.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping

        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.getLegend().setTextSize(12);
        barChart.animateY(1000); // set animation
        barChart.invalidate(); // refresh


        return view;
    }
}