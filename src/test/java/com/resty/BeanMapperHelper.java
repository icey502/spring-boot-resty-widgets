package com.resty;

import com.google.common.collect.ImmutableMap;
import com.resty.configuration.ConfigurableBeanMapper;
import ma.glasnost.orika.*;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Methods for use in unit tests to init with bean mapping
 *
 * Created by damianhagge on 6/9/17.
 */
public class BeanMapperHelper {

    public static MapperFacade getDefaultMapperFacade() {
        MapperFactory mapperFactory = getDefaultMapperFactory();
        return mapperFactory.getMapperFacade();
    }

    private static MapperFactory getDefaultMapperFactory() {
        ConfigurableBeanMapper beanMapper = new ConfigurableBeanMapper();
        ApplicationContext appContext = mock(ApplicationContext.class);
        ReflectionTestUtils.setField(beanMapper, "applicationContext", appContext);

        when(appContext.getBeansOfType(Converter.class)).thenReturn(
                new ImmutableMap.Builder<String, Converter>().build());

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        beanMapper.configure(mapperFactory);
        return mapperFactory;
    }
}
