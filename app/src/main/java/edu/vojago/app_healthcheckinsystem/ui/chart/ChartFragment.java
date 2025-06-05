package edu.vojago.app_healthcheckinsystem.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.vojago.app_healthcheckinsystem.R;

public class ChartFragment extends Fragment {
    private LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        initChart(view);
        loadSampleData();
        return view;
    }

    private void initChart(View view) {
        lineChart = view.findViewById(R.id.lineChart);
        TextView tvEmpty = view.findViewById(R.id.tvEmpty);

        // 基础配置
        lineChart.setNoDataText("加载中...");
        lineChart.setNoDataTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        // X轴配置
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnSurface));
        xAxis.setValueFormatter(new DayAxisFormatter());
        xAxis.setGranularity(1f);

        // Y轴配置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnSurface));
        leftAxis.setAxisMinimum(35f);
        leftAxis.setAxisMaximum(42f);
        leftAxis.setGranularity(0.5f);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
    }

    private void loadSampleData() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 36.5f));
        entries.add(new Entry(1, 36.8f));
        entries.add(new Entry(2, 37.1f));
        entries.add(new Entry(3, 36.9f));
        entries.add(new Entry(4, 37.2f));

        LineDataSet dataSet = new LineDataSet(entries, "体温记录");
        dataSet.setColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary));
        dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.colorLifeGreen));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(ContextCompat.getColor(requireContext(), R.color.colorOnSurface));
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // 平滑曲线

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private static class DayAxisFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return String.format(Locale.getDefault(), "第%.0f天", value);
        }
    }
}



