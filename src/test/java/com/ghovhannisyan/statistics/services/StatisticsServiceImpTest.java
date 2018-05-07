package com.ghovhannisyan.statistics.services;

import com.ghovhannisyan.statistics.entities.StatisticsEntity;
import com.ghovhannisyan.statistics.entities.TransactionEntity;
import com.ghovhannisyan.statistics.services.impl.StatisticsServiceImp;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by ghovhannisyan on 5/6/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class StatisticsServiceImpTest {

    private StatisticsService statisticsService = new StatisticsServiceImp();

    @After
    public void tearDown() {
        statisticsService.resetStatistics();
    }

    @Test
    public void testGetStatisticsWithEmptyTransactions() throws Exception {
        StatisticsEntity statisticsEntity = statisticsService.getStatistics();

        assertEquals(new Double(0.0d), statisticsEntity.getSum());
        assertEquals(new Double(0.0d), statisticsEntity.getMax());
        assertEquals(new Double(0.0d), statisticsEntity.getMin());
        assertEquals(new Double(0.0d), statisticsEntity.getAvg());
        assertEquals(new Integer(0), statisticsEntity.getCount());
    }

    @Test
    public void testGetStatisticsFirstCallAllAreWithin60SecSecondCallAllAreNotWithin60sec() throws Exception {

        long currentTimestamp = System.currentTimeMillis();
        long timestamp = currentTimestamp - 50L;

        TransactionEntity transactionEntity = new TransactionEntity(9.5d, timestamp);
        statisticsService.registerTransaction(transactionEntity);

        transactionEntity = new TransactionEntity(12.5d, timestamp);
        statisticsService.registerTransaction(transactionEntity);

        timestamp = currentTimestamp - 57*1000L;
        transactionEntity = new TransactionEntity(14.0d, timestamp);
        statisticsService.registerTransaction(transactionEntity);

        StatisticsEntity statisticsEntity = statisticsService.getStatistics();

        assertEquals(new Double(36.0d), statisticsEntity.getSum());
        assertEquals(new Double(14.0d), statisticsEntity.getMax());
        assertEquals(new Double(9.5d), statisticsEntity.getMin());
        assertEquals(new Double(12.0d), statisticsEntity.getAvg());
        assertEquals(new Integer(3), statisticsEntity.getCount());

        Thread.sleep(5000);

        statisticsEntity = statisticsService.getStatistics();

        assertEquals(new Double(22.0d), statisticsEntity.getSum());
        assertEquals(new Double(12.5d), statisticsEntity.getMax());
        assertEquals(new Double(9.5d), statisticsEntity.getMin());
        assertEquals(new Double(11.0d), statisticsEntity.getAvg());
        assertEquals(new Integer(2), statisticsEntity.getCount());
    }

    @Test
    public void testRegisterTransactionAllAreWithin60sec() throws Exception {

        long currentTimestamp = System.currentTimeMillis();
        long timestamp = currentTimestamp - 50L;

        TransactionEntity transactionEntity = new TransactionEntity(12.5d, timestamp);
        statisticsService.registerTransaction(transactionEntity);

        transactionEntity = new TransactionEntity(12.5d, timestamp);
        statisticsService.registerTransaction(transactionEntity);

        timestamp = currentTimestamp - 100L;
        transactionEntity = new TransactionEntity(14.0d, timestamp);
        statisticsService.registerTransaction(transactionEntity);

        StatisticsEntity statisticsEntity = statisticsService.getStatistics();

        assertEquals(new Double(39.0d), statisticsEntity.getSum());
        assertEquals(new Double(14.0d), statisticsEntity.getMax());
        assertEquals(new Double(12.5d), statisticsEntity.getMin());
        assertEquals(new Double(13.0d), statisticsEntity.getAvg());
        assertEquals(new Integer(3), statisticsEntity.getCount());
    }
}
