package com.sanbeso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.test.controller"})
public class WebAppConfig {

    /*@Bean
    public UrlBasedViewResolver getUrlBasedViewResolver() {
        UrlBasedViewResolver u = new UrlBasedViewResolver();
        u.setPrefix("/WEB-INF/jsp/");
        u.setSuffix(".jsp");
        u.setViewClass(JstlView.class);
        return u;
    }*/
    
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver(){
    	InternalResourceViewResolver ivr = new InternalResourceViewResolver();
    	ivr.setPrefix("/WEB-INF/jsp/");
    	ivr.setSuffix(".jsp");
    	return ivr;
    }
 
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor 
           = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        return localeChangeInterceptor;
    }

    @Bean
    public LocaleResolver getLocaleResolver() {
        return new CookieLocaleResolver();
    }
}
