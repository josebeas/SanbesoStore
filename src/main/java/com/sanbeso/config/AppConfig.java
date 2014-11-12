package com.sanbeso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

import com.sanbeso.bean.HelloBean;

@Configuration
@ComponentScan(basePackages = {"com.sanbeso"})
public class AppConfig {
	
    @Value("${example.label}")
    private String label;
	
    @Bean
    public HelloBean createHello() {
        return new HelloBean("Hello Foo, " + label);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

    @Bean
    public static BeanFactoryPostProcessor getPropertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer p = new PropertyPlaceholderConfigurer();
        p.setLocation(new ClassPathResource("app.properties"));
        return p;
    }

    /*@Bean
    public UrlBasedViewResolver getUrlBasedViewResolver() {
        UrlBasedViewResolver u = new UrlBasedViewResolver();
        u.setPrefix("/WEB-INF/jsp/");
        u.setSuffix(".jsp");
        u.setViewClass(JstlView.class);
        return u;
    }*/
}
