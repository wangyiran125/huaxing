package org.chinalbs.logistics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.chinalbs.logistics.Application;
import org.chinalbs.logistics.ObjectMapperHolder;
import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableAspectJAutoProxy
//@EnableSwagger
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = Application.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthenticationInterceptor authenticationInterceptor;

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(resourceConverter());
    	converters.add(jacksonConverter());
        converters.add(stringConverter());
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
    	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    	ObjectMapper mapper = ObjectMapperHolder.getInstance().getNewMapper();
    	converter.setObjectMapper(mapper);
        return converter;
    }

    @Bean
    StringHttpMessageConverter stringConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        return converter;
    }

    @Bean
    ResourceHttpMessageConverter resourceConverter() {
    	ResourceHttpMessageConverter converter = new ResourceHttpMessageConverter();
    	return converter;
    }
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(authenticationInterceptor);
	}

	@Bean
    MultipartResolver multipartResolver() {
    	return new StandardServletMultipartResolver();
    }
    /**
     * Handles favicon.ico requests assuring no <code>404 Not Found</code> error is returned.
     */
    @Controller
    static class FaviconController {
    	@OperationDefinition(name = "favicon", anonymous = true)
        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/resources/images/favicon.ico";
        }
    }
    
	@Bean
	public static ControllerFinder controllerFinder() {
		return new ControllerFinder();
	}
}
