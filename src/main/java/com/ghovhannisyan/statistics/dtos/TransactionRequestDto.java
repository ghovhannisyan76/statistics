package com.ghovhannisyan.statistics.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description="Transaction")
public class TransactionRequestDto implements Serializable {

    private static final long serialVersionUID = -679950164134910639L;

    @ApiModelProperty(value = "Transaction amount.", required = true)
    @NotNull(message = "amount cannot be null")
    private final Double amount;

    @ApiModelProperty(value = "Transaction time in epoch in millis in UTC (this is not the current timestamp).", required = true)
    @NotNull(message = "timestamp cannot be null")
    private final Long timestamp;

    @JsonCreator
    public TransactionRequestDto(@JsonProperty("amount") Double amount,
                                 @JsonProperty("timestamp") Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRequestDto that = (TransactionRequestDto) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, timestamp);
    }

    public Double getAmount() {
        return amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
