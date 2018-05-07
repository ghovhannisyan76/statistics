package com.ghovhannisyan.statistics.entities.mappers;

import com.ghovhannisyan.statistics.dtos.TransactionRequestDto;
import com.ghovhannisyan.statistics.entities.TransactionEntity;
import org.springframework.stereotype.Component;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
@Component
public class TransactionEntityMapper extends AbstractEntityMapper<TransactionEntity, TransactionRequestDto> {

    @Override
    public TransactionEntity createEntity(TransactionRequestDto transactionRequestDto) {
        return new TransactionEntity(transactionRequestDto.getAmount(), transactionRequestDto.getTimestamp());
    }
}
