package com.ghovhannisyan.statistics.dtos.mappers;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public abstract class AbstractDtoMapper<DTO, Entity> {

    public abstract DTO createDto(Entity entity);
}
