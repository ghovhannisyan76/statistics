package com.ghovhannisyan.statistics.entities;

import java.util.Objects;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public class TransactionEntity implements Comparable<TransactionEntity> {

    private Double amount;

    private Long timestamp;

    public TransactionEntity() {

    }

    public TransactionEntity(Double amount, Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, timestamp);
    }

    @Override
    public int compareTo(TransactionEntity transactionEntity) {
        if(transactionEntity == null) {
            throw new NullPointerException();
        }

        int retVal = 0;

        if(!this.equals(transactionEntity)) {

            if (timestamp < transactionEntity.timestamp) {
                retVal = -1;
            } else if (timestamp > transactionEntity.timestamp) {
                retVal = 1;
            } else if (amount < transactionEntity.amount) {
                retVal = -1;
            } else {
                retVal = 1;
            }
        }

        return retVal;
    }
}
