package me.ez0ne.ouring.ui;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.ez0ne.ouring.R;
import me.ez0ne.ouring.model.BankCard;
import me.ez0ne.ouring.model.ChartData;

public class ChartFragment extends Fragment {

    float in_sum = 0.0f;
    float out_sum = 0.0f;
    float all_sum = 0.0f;
    private PieChart mChart;
    private TextView targetTime;
    private TextView mTv_in_sum;
    private TextView mTv_out_sum;
    private ImageButton ibBeforeDay, ibAfterDay;
    private int targetMonth , targetYear;
    private String sTargetMonth, sTargetYear;
    private Calendar now = Calendar.getInstance();
    private SharedPreferences sharedPreferences;
    private List<BankCard> toShowData;
    private List<ChartData> dataToShow = new ArrayList<>();
    private String listDate;
    private float listFmoney;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chart, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //boolean isDbReady = sharedPreferences.getBoolean("db_ready", false);

        mChart = (PieChart) view.findViewById(R.id.bc_Monthly);
        targetTime = (TextView) view.findViewById(R.id.tv_Month);
        mTv_in_sum = (TextView) view.findViewById(R.id.tv_month_sum_in);
        mTv_out_sum = (TextView) view.findViewById(R.id.tv_month_sum_out);
        ibBeforeDay = (ImageButton) view.findViewById(R.id.ib_BeforeMonth);
        ibAfterDay = (ImageButton) view.findViewById(R.id.ib_AfterMonth);

        targetYear = now.get(Calendar.YEAR);
        sTargetYear = Integer.toString(targetYear);
        Log.e("DEBUG...", String.valueOf(targetYear));
        Log.e("DEBUG...", sTargetYear);
        targetMonth = now.get(Calendar.MONTH) + 1;
        Log.e("DEBUG...", String.valueOf(targetMonth));
        sTargetMonth = Integer.toString(targetMonth);


        targetTime.setText(sTargetYear + "-" + sTargetMonth);

//        if (isDbReady) {
//            intiChart();
//        }

        intiChart();
        mTv_in_sum.setText(String.valueOf(in_sum));
        mTv_out_sum.setText(String.valueOf(out_sum));

        setDateChangeListener();

    }

    private void intiChart() {
        Log.e("DEBUG", "intiChart-------");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        setDataFromDB(sTargetYear, sTargetMonth);
        Log.e("DEBUG", "year +  month" + sTargetYear + "::" + sTargetMonth);

    }



//    private void intiChart() {
//        Log.e("DEBUG", "intiChart-------");
//        mChart.setDrawValueAboveBar(true);
//
//        mChart.setDescription("BarChart");
//        mChart.setPinchZoom(false);
//        mChart.setDrawGridBackground(true);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setSpaceBetweenLabels(1);
//        xAxis.setTextColor(Color.LTGRAY);
//        xAxis.setTextSize(12f);
//
//        YAxis left = mChart.getAxisLeft();
//        left.setDrawLabels(false);
//        left.setStartAtZero(false);
//        left.setSpaceTop(20f);
//        left.setSpaceBottom(20f);
//        left.setDrawAxisLine(false);
//        left.setDrawGridLines(false);
//        left.setDrawZeroLine(true);
//        left.setZeroLineColor(Color.GRAY);
//        left.setZeroLineWidth(0.7f);
//
//        mChart.getAxisRight().setEnabled(false);
//        mChart.getLegend().setEnabled(false);
//
//        setDataFromDB(sTargetYear, sTargetMonth);
//        Log.e("DEBUG", "year +  month" + sTargetYear + "::" + sTargetMonth);
//
//    }

    private void setDateChangeListener() {
        ibBeforeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTargetDate(false);
            }
        });
        ibAfterDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTargetDate(true);
            }
        });

    }

    private void changeTargetDate(boolean isAdd) {
        int month = Integer.parseInt(sTargetMonth);
        int year = Integer.parseInt(sTargetYear);
            if (isAdd) {
                if(year < targetYear || month < targetMonth ){
                    month += 1;
                    if (month == 13) {
                        month = 1;
                        year += 1;
                    }
                }

            } else {
                month -= 1;
                if (month == 0) {
                    month = 12;
                    year -= 1;
                }
            }
            if (month <= 9) {
                sTargetMonth = "0" + month;
            } else sTargetMonth = month + "";
            sTargetYear = year + "";
            targetTime.setText(year + "-" + month);
        Log.e("DEBUG", "month------" + sTargetMonth);
        //刷新图表
        intiChart();
        mTv_in_sum.setText(String.valueOf(in_sum));
        mTv_out_sum.setText(String.valueOf(out_sum));

    }

    private void setDataFromDB(String sYear, String sMonth) {
        in_sum = 0.0f;
        out_sum = 0.0f;//收入、支出值归零
        toShowData = DataSupport.where("year = ?", sYear).where("month = ?", sMonth).order("id desc").find(BankCard.class);
        dataToShow.clear(); //将之前内容清空
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if (toShowData.size() > 0) {
            for (int m = 0; m < toShowData.size(); m++) {
                Log.e("DEBUG", sYear + "-" + sMonth + "共计" + toShowData.size() + "条数据");
                listFmoney = toShowData.get(m).getFmoney();
                Log.e("DEBUG", "listFmoney-----" + listFmoney);
                if (listFmoney > 0) {
                    in_sum = in_sum + listFmoney;
                } else {
                    out_sum = out_sum + listFmoney;
                }
                /**
                 * all_sum=out_sum+listmoney
                 */
                all_sum = out_sum + listFmoney;
                listDate = toShowData.get(m).getMonth() + "-" + toShowData.get(m).getDay();

                Log.e("DEBUG", "第" + m + "个数组内容提取完成");
                Log.e("Debgu", out_sum+"" );
            }

            entries.add(new PieEntry(in_sum, "收入"));
            entries.add(new PieEntry(-out_sum, "支出"));
            Log.e("DEBUG","dataToShow大小：------" + dataToShow.size());

        }

        setData(entries);
    }


    public void setData(List<PieEntry> entries) {
       PieDataSet dataSet=new PieDataSet(entries,"");
        mChart.clear();//绘图前先清空视图信息
        Log.e("DEBUG", "setData-------------");

//数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.rgb(139,101,8));
        data.setValueTextSize(16);
        mChart.setData(data);
        mChart.highlightValues(null);
        //刷新
        mChart.invalidate();







    }





//    public void setData(List<ChartData> dataList) {
//        mChart.clear();//绘图前先清空视图信息
//        Log.e("DEBUG", "setData-------------");
//        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
//        String[] dates = new String[dataList.size()];
//        List<Integer> colors = new ArrayList<Integer>();
//
//        int green = Color.rgb(17, 205, 110);
//        int red = Color.rgb(235, 79, 56);
//
//        for (int i = 0; i < dataList.size(); i++) {
//
//            ChartData d = dataList.get(i);
//            BarEntry entry = new BarEntry(d.yValue, d.xIndex);
//            values.add(entry);
//
//            dates[i] = dataList.get(i).xAxisValue;
//
//            // specific colors
//            if (d.yValue >= 0)
//                colors.add(green);
//            else
//                colors.add(red);
//        }
//
//        BarDataSet set;
//
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set = (BarDataSet) mChart.getData().getDataSetByIndex(0);
//            //set.setYVals(values);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//
//            set = new BarDataSet(values, "Values");
//            set.setBarSpacePercent(20f);
//            set.setColors(colors);
//            set.setValueTextColors(colors);
//
//            BarData data = new BarData(dates, set);
//            data.setValueTextSize(10f);
//            data.setValueFormatter(new ValueFormatter());
//
//            mChart.setData(data);
//            mChart.notifyDataSetChanged();
//        }
//    }


//    private class ValueFormatter implements com.github.mikephil.charting.formatter.ValueFormatter {
//
//        private DecimalFormat mFormat;
//
//        public ValueFormatter() {
//            mFormat = new DecimalFormat("######.0");
//        }
//
//        @Override
//        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//            return mFormat.format(value);
//        }
//    }
}
