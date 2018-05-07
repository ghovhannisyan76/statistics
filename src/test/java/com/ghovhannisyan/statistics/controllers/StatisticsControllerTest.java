package com.ghovhannisyan.statistics.controllers;

import com.ghovhannisyan.statistics.dtos.StatisticsResponseDto;
import com.ghovhannisyan.statistics.dtos.TransactionRequestDto;
import com.ghovhannisyan.statistics.entities.StatisticsEntity;
import com.ghovhannisyan.statistics.entities.TransactionEntity;
import com.ghovhannisyan.statistics.services.StatisticsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public class StatisticsControllerTest extends AbstractControllerTest {

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testTransaction() throws Exception {
        long timestamp = System.currentTimeMillis() - 50L;
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(12.5d, timestamp);

        TransactionEntity transactionEntity = new TransactionEntity(12.5d, timestamp);

        String payload = mapToJson(transactionRequestDto);

        doNothing().when(statisticsService).registerTransaction(eq(transactionEntity));

        mockMvc.perform(post("/transaction").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(payload))
                .andExpect(status().isCreated());

        verify(statisticsService).registerTransaction(eq(transactionEntity));

    }

    @Test
    public void negativeTestTransactionTimestampInFuture() throws Exception {
        long timestamp = System.currentTimeMillis() + 60 * 1000L;
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(12.5d, timestamp);

        String payload = mapToJson(transactionRequestDto);

        mockMvc.perform(post("/transaction").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(payload))
                .andExpect(status().isBadRequest());

        verify(statisticsService, times(0)).registerTransaction(any());

    }

    @Test
    public void testTransactionNoContent() throws Exception {
        long timestamp = System.currentTimeMillis() - 61 * 1000L;
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(12.5d, timestamp);

        String payload = mapToJson(transactionRequestDto);

        mockMvc.perform(post("/transaction").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(payload))
                .andExpect(status().isNoContent());

        verify(statisticsService, times(0)).registerTransaction(any());

    }

    @Test
    public void negativeTestTransactionPayloadValidationFail() throws Exception {
        long timestamp = System.currentTimeMillis() + 60 * 1000L;
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(null, timestamp);

        String payload = mapToJson(transactionRequestDto);

        mockMvc.perform(post("/transaction").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(payload))
                .andExpect(status().isBadRequest());

        verify(statisticsService, times(0)).registerTransaction(any());

    }

    @Test
    public void testGetStatistics() throws Exception {
        StatisticsEntity statisticsEntity = new StatisticsEntity(1000.0d, 100.0d, 200.0d, 50.0d, 10);

        when(statisticsService.getStatistics()).thenReturn(statisticsEntity);

        MvcResult mvcResult = mockMvc.perform(get("/statistics").
                contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        verify(statisticsService).getStatistics();

        StatisticsResponseDto responseDto = mapFromJson(mvcResult.getResponse().getContentAsString(),
                StatisticsResponseDto.class);

        assertEquals(statisticsEntity.getSum(), responseDto.getSum());
        assertEquals(statisticsEntity.getAvg(), responseDto.getAvg());
        assertEquals(statisticsEntity.getMax(), responseDto.getMax());
        assertEquals(statisticsEntity.getMin(), responseDto.getMin());
        assertEquals(statisticsEntity.getCount(), responseDto.getCount());
    }
}
