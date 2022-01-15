package com.tui.proof.configuration;

import com.tui.proof.converters.AddressToAddressBeanConverter;
import com.tui.proof.converters.OrderToOrderBeanConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderToOrderBeanConverter());
        registry.addConverter(new AddressToAddressBeanConverter());
    }


}
