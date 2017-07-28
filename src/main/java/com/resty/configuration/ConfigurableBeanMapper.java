package com.resty.configuration;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Configuration for the orika mapper
 *
 * Created by damianhagge on 5/16/17.
 */
@Component
public class ConfigurableBeanMapper extends ConfigurableMapper {

    @Autowired
    private ApplicationContext applicationContext;

    public ConfigurableBeanMapper() {
        super(false);
    }

    @PostConstruct
    public void init() {
        super.init();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure(MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDateTime.class));
        factory.getConverterFactory().registerConverter(new PassThroughConverter(LocalDate.class));

        applicationContext.getBeansOfType(Converter.class).values().stream()
                .forEach(factory.getConverterFactory()::registerConverter);
        applicationContext.getBeansOfType(Mapper.class).values().stream()
                .forEach(mapper -> factory
                        .classMap(mapper.getAType(), mapper.getBType())
                        .customize(mapper)
                        .byDefault()
                        .register());
    }
}
