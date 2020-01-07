package com.williamdsw.springbootessentials.adapter;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author William
 */

@Configuration
public class SpringBootEssentialsAdapter implements WebMvcConfigurer
{
    @Override
    public void addArgumentResolvers (List<HandlerMethodArgumentResolver> resolvers)
    {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver ();
        resolver.setFallbackPageable (PageRequest.of (0, 5));
        resolvers.add (resolver);
    }    
}