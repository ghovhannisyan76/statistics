package com.ghovhannisyan.statistics.dtos.mappers;

import com.ghovhannisyan.statistics.dtos.StatisticsResponseDto;
import com.ghovhannisyan.statistics.entities.StatisticsEntity;
import org.springframework.stereotype.Component;

/**
 * Created by ghovhannisyan on 5/6/18.
 */
@Component
public class StatisticsResponseDtoMapper extends AbstractDtoMapper<StatisticsResponseDto, StatisticsEntity> {

    @Override
    public StatisticsResponseDto createDto(StatisticsEntity statisticsEntity) {
        return new StatisticsResponseDto(statisticsEntity.getSum(), statisticsEntity.getAvg(),
                statisticsEntity.getMax(), statisticsEntity.getMin(), statisticsEntity.getCount());
    }
}
