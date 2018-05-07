package com.ghovhannisyan.statistics.entities.mappers;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
public abstract class AbstractEntityMapper<Entity, DTO> {
    public abstract Entity createEntity(DTO dto);
}
