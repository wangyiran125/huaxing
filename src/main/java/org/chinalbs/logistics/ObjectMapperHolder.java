package org.chinalbs.logistics;

import org.chinalbs.logistics.common.utils.ProxyExclusionPropertyFilter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ObjectMapperHolder {
    private static ObjectMapperHolder instance = new ObjectMapperHolder();
    private ObjectMapper mapper;
    private ObjectMapperHolder(){
         mapper = createMapper();
    }

    public static ObjectMapperHolder getInstance() {
        return instance;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public ObjectMapper getNewMapper() {
        //对于Spring, 单例无法工作
        return createMapper();
    }

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("proxyExclusionFilter", new ProxyExclusionPropertyFilter());
        mapper.setFilters(filterProvider);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        return mapper;
    }
}
