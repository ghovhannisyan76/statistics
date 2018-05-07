package com.ghovhannisyan.statistics.controllers;

import com.ghovhannisyan.statistics.advices.InternalErrorEnum;
import com.ghovhannisyan.statistics.dtos.StatisticsResponseDto;
import com.ghovhannisyan.statistics.dtos.TransactionRequestDto;
import com.ghovhannisyan.statistics.dtos.mappers.StatisticsResponseDtoMapper;
import com.ghovhannisyan.statistics.entities.TransactionEntity;
import com.ghovhannisyan.statistics.entities.mappers.TransactionEntityMapper;
import com.ghovhannisyan.statistics.exceptions.BadRequestException;
import com.ghovhannisyan.statistics.exceptions.OldTransactionException;
import com.ghovhannisyan.statistics.services.StatisticsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ghovhannisyan.statistics.AppConstants.STATISTICS_AGE;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
@RestController
public class StatisticsController {

    @Autowired
    private TransactionEntityMapper transactionEntityMapper;

    @Autowired
    private StatisticsResponseDtoMapper statisticsResponseDtoMapper;

    @Autowired
    private StatisticsService statisticsService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(
            value = "/transaction",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Registers the transaction for the further statistics calculation.",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void transaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto) {

        long currentTime = System.currentTimeMillis();
        if(currentTime < transactionRequestDto.getTimestamp()) {
            throw new BadRequestException(InternalErrorEnum.TRANSACTION_TIMESTAMP_CANNOT_NE_IN_FUTURE,
                    InternalErrorEnum.TRANSACTION_TIMESTAMP_CANNOT_NE_IN_FUTURE.getDescription());
        }

        if(currentTime - transactionRequestDto.getTimestamp() > STATISTICS_AGE) {
            throw new OldTransactionException(InternalErrorEnum.TRANSACTION_IS_OLD,
                    InternalErrorEnum.TRANSACTION_IS_OLD.getDescription());

        }

        TransactionEntity transactionEntity = transactionEntityMapper.createEntity(transactionRequestDto);

        statisticsService.registerTransaction(transactionEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/statistics",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Returns the statistic based on the transactions which happen in the last 60 seconds.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticsResponseDto getStatistics() {
        return statisticsResponseDtoMapper.createDto(statisticsService.getStatistics());
    }
}
