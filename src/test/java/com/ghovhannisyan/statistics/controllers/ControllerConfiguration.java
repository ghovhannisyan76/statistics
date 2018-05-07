package com.ghovhannisyan.statistics.controllers;

import com.ghovhannisyan.statistics.services.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.mock;

/**
 * Created by ghovhannisyan on 5/5/18.
 */
@ComponentScan({
        "com.ghovhannisyan.statistics.controllers",
        "com.ghovhannisyan.statistics.advices",
        "com.ghovhannisyan.statistics.dtos.mappers",
        "com.ghovhannisyan.statistics.entities.mappers"})
@EnableWebMvc
@Configuration
@EnableSpringDataWebSupport
public class ControllerConfiguration {

        @Bean
        public StatisticsService statisticsService() {
                return mock(StatisticsService.class);
        }
}
