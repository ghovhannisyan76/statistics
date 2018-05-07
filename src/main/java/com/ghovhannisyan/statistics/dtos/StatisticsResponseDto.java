package com.ghovhannisyan.statistics.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by ghovhannisyan on 5/6/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description="Statistics")
public class StatisticsResponseDto implements Serializable {

    private static final long serialVersionUID = 8721696968690744750L;

    @ApiModelProperty(value = "Sum of transaction value.")
    private final Double sum;

    @ApiModelProperty(value = "Average amount of transaction value.")
    private final Double avg;

    @ApiModelProperty(value = "The highest transaction value.")
    private final Double max;

    @ApiModelProperty(value = "The lowers transaction value.")
    private final Double min;

    @ApiModelProperty(value = "Total number of transactions.")
    private final Integer count;

    @JsonCreator
    public StatisticsResponseDto(@JsonProperty("sum") Double sum,
                                 @JsonProperty("avg") Double avg,
                                 @JsonProperty("max") Double max,
                                 @JsonProperty("min") Double min,
                                 @JsonProperty("count") Integer count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public Double getAvg() {
        return avg;
    }

    public Double getMax() {
        return max;
    }

    public Double getMin() {
        return min;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsResponseDto that = (StatisticsResponseDto) o;
        return Objects.equals(sum, that.sum) &&
                Objects.equals(avg, that.avg) &&
                Objects.equals(max, that.max) &&
                Objects.equals(min, that.min) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }
}
