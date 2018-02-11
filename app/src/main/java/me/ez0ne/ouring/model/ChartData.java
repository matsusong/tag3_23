package me.ez0ne.ouring.model;

/**
 * Created by Ezreal.Wan on 2016/5/4.
 */
public class ChartData {
    public String xAxisValue;
    public float yValue;
    public int xIndex;

    public ChartData(int xIndex, float yValue, String xAxisValue) {
        this.xAxisValue = xAxisValue;
        this.yValue = yValue;
        this.xIndex = xIndex;
    }
}
