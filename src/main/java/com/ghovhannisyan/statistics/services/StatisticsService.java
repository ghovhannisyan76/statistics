package com.ghovhannisyan.statistics.services;

import com.ghovhannisyan.statistics.entities.StatisticsEntity;
import com.ghovhannisyan.statistics.entities.TransactionEntity;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public interface StatisticsService {

    /**
     * Registers the transaction
     * @param transactionEntity
     */
    void registerTransaction(TransactionEntity transactionEntity);

    /**
     * Calculates and returns the statistics based on the transactions which happen in the last 60 seconds
     * @return
     */
    StatisticsEntity getStatistics();

    /**
     * This methods resets the statistics. It is mostly needed for testing purposes.
     */
    void resetStatistics();
}
