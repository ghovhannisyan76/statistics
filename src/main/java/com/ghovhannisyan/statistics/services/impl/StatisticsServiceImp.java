package com.ghovhannisyan.statistics.services.impl;

import com.ghovhannisyan.statistics.entities.DiscreteStatisticsEntity;
import com.ghovhannisyan.statistics.entities.StatisticsEntity;
import com.ghovhannisyan.statistics.entities.TransactionEntity;
import com.ghovhannisyan.statistics.services.StatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
@Service
public class StatisticsServiceImp implements StatisticsService {

    private DiscreteStatisticsEntity [] discreteStatisticsEntities = new DiscreteStatisticsEntity[60];

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void registerTransaction(TransactionEntity transactionEntity) {
        long currentTimestamp = System.currentTimeMillis();
        long transactionTimeSecs = transactionEntity.getTimestamp() / 1000;
        int index = 59 - (int)((currentTimestamp - transactionEntity.getTimestamp()) / 1000);

        BigDecimal amount = new BigDecimal(transactionEntity.getAmount(), MathContext.DECIMAL64);
        if(discreteStatisticsEntities[index] == null) {
            discreteStatisticsEntities[index] = new DiscreteStatisticsEntity(transactionTimeSecs, amount, amount, amount, 1);
        } else if(discreteStatisticsEntities[index].getTimestampInSeconds() != transactionTimeSecs) {
            discreteStatisticsEntities[index].setTimestampInSeconds(transactionTimeSecs);
            discreteStatisticsEntities[index].setSum(amount);
            discreteStatisticsEntities[index].setMax(amount);
            discreteStatisticsEntities[index].setMin(amount);
            discreteStatisticsEntities[index].setCount(1);
        } else {
            BigDecimal sum = discreteStatisticsEntities[index].getSum().add(amount, MathContext.DECIMAL64);
            discreteStatisticsEntities[index].setSum(sum);

            if(discreteStatisticsEntities[index].getMax().compareTo(amount) < 0) {
                discreteStatisticsEntities[index].setMax(amount);
            }
            if(discreteStatisticsEntities[index].getMin().compareTo(amount) > 0) {
                discreteStatisticsEntities[index].setMin(amount);
            }

            discreteStatisticsEntities[index].setCount(discreteStatisticsEntities[index].getCount() + 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized StatisticsEntity getStatistics() {
        long currentTimestampSecs = System.currentTimeMillis() / 1000;

        int count = 0;
        BigDecimal min = new BigDecimal(Double.MAX_VALUE, MathContext.DECIMAL64);
        BigDecimal max = new BigDecimal(Double.MIN_VALUE, MathContext.DECIMAL64);;
        BigDecimal sum = new BigDecimal("0.0", MathContext.DECIMAL64);

        for(DiscreteStatisticsEntity discreteStatisticsEntity : discreteStatisticsEntities) {
            if(discreteStatisticsEntity != null &&
                    (currentTimestampSecs - discreteStatisticsEntity.getTimestampInSeconds() < 60)) {

                if(min.compareTo(discreteStatisticsEntity.getMin()) > 0) {
                    min = discreteStatisticsEntity.getMin();
                }
                if(max.compareTo(discreteStatisticsEntity.getMax()) < 0) {
                    max = discreteStatisticsEntity.getMax();
                }

                sum = sum.add(discreteStatisticsEntity.getSum(), MathContext.DECIMAL64);

                count += discreteStatisticsEntity.getCount();
            }
        }

        StatisticsEntity statisticsEntity;
        if(count == 0) {
            statisticsEntity = new StatisticsEntity(0.0d, 0.0d, 0.0d, 0.0d, 0);
        } else if(count == 1) {
            statisticsEntity = new StatisticsEntity(sum.doubleValue(), sum.doubleValue(), max.doubleValue(),
                    min.doubleValue(), 1);
        } else {

            BigDecimal avg = sum.divide(new BigDecimal(count, MathContext.DECIMAL64), MathContext.DECIMAL64);
            statisticsEntity = new StatisticsEntity(sum.doubleValue(), avg.doubleValue(), max.doubleValue(),
                    min.doubleValue(), count);
        }

        return statisticsEntity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void resetStatistics() {
        for(int i = 0; i < discreteStatisticsEntities.length; i++) {
            discreteStatisticsEntities[i] = null;
        }
    }
}
