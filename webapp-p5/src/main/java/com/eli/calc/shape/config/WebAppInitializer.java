package com.eli.calc.shape.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.eli.calc.shape.listeners.impl.ShapeCalcServletContextListener;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		System.err.println("\n\n\n\nELI: Web AppInitializer onStartup() \n\n\n\n");

		AnnotationConfigWebApplicationContext rootCtx =
								new AnnotationConfigWebApplicationContext();
		rootCtx.register(WebConfig.class);
		
		System.err.println("\n\n\n\nELI: Web AppInitializer root context loader listener.. \n\n\n\n");
		ContextLoaderListener loaderListener = new ContextLoaderListener(rootCtx);

		System.err.println("\n\n\n\nELI: Web AppInitializer add root context loader listener to servletcontext..... \n\n\n\n");
		servletContext.addListener(loaderListener);

		System.err.println("\n\n\n\nELI: Web AppInitializer add shape calc servlet context listener to servletcontext..... \n\n\n\n");
		ShapeCalcServletContextListener myServletContextListener = new ShapeCalcServletContextListener(rootCtx);
		servletContext.addListener(myServletContextListener);

		System.err.println("\n\n\n\nELI: Web AppInitializer created dispatcher servlet passing root ctx..... \n\n\n\n");
		DispatcherServlet dispatcherServlet = new DispatcherServlet(rootCtx);

		System.err.println("\n\n\n\nELI: Web AppInitializer register dispatcher servlet with servlet context..... \n\n\n\n");
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcherServlet",dispatcherServlet);
		
		registration.setLoadOnStartup(1);
		registration.addMapping("/");

		System.err.println("\n\n\n\nELI: Web AppInitializer onStartup() done\n\n\n\n");

	}

}
