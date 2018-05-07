package com.ghovhannisyan.statistics.entities;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by ghovhannisyan on 5/6/18.
 */
public class DiscreteStatisticsEntity {

    private long timestampInSeconds = 0L;

    private BigDecimal sum = new BigDecimal("0.0", MathContext.DECIMAL64);

    private BigDecimal min = new BigDecimal("0.0", MathContext.DECIMAL64);

    private BigDecimal max = new BigDecimal("0.0", MathContext.DECIMAL64);

    private int count = 0;

    public DiscreteStatisticsEntity() {

    }

    public DiscreteStatisticsEntity(long timestampInSeconds, BigDecimal sum, BigDecimal max, BigDecimal min, int count) {
        this.timestampInSeconds = timestampInSeconds;
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public long getTimestampInSeconds() {
        return timestampInSeconds;
    }

    public void setTimestampInSeconds(long timestampInSeconds) {
        this.timestampInSeconds = timestampInSeconds;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
