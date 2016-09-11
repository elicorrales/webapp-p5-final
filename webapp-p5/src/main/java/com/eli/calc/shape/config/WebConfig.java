package com.eli.calc.shape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
//@Configuration
@ComponentScan(basePackages="com.eli.calc.shape")
public class WebConfig extends WebMvcConfigurerAdapter {

/*
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		System.err.println("\n\n\n\nELI: Web Config addViewControllers\n\n\n\n");

		registry.addViewController("/").setViewName("/index");
		registry.addViewController("/index").setViewName("index");
		//registry.addViewController("/foo").setViewName("foo");
		//registry.addViewController("/newreq").setViewName("newreq");
		//registry.addViewController("/pending").setViewName("pending");
		//registry.addViewController("/results").setViewName("results");
	}
*/


	@Bean
	public InternalResourceViewResolver viewResolver() {

        System.err.println("\n\n\n\nELI: Web Config @Bean ViewResolvr\n\n\n\n");

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}



	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		System.err.println("\n\n\n\nELI: Web Config addResourceHandlers\n\n\n\n");

		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}


}
