package com.eli.calc.shape.listeners.impl;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

//@WebListener
@Component
public class ShapeCalcServletContextListener implements ServletContextListener {


	private ApplicationContext ctx;
	
	private DataSource dataSource;

	private ExecutorService executor;


	public ShapeCalcServletContextListener(ApplicationContext ctx) { this.ctx = ctx; }
			

	@Override
	public void contextDestroyed(ServletContextEvent event) {

		System.err.println("\n\n\nContext is being Destroyed.....SHUTTING DOWN!!!!\n\n\n");
		
		//shutdown any tasks using the data source
		try { executor.shutdownNow(); }
		catch (Exception e) { System.err.println("\n\n\nError attempting to call executor.shutdownNow() : "+e.getMessage()); }
		try { executor.awaitTermination(10, TimeUnit.SECONDS); }
		catch (Exception e) { System.err.println("\n\n\nError executor.awaitingTermination() : "+e.getMessage()); }


		//shutdown the datasource itself
		// this probably not needed....  it's been done prior to this point
		try { dataSource.getConnection().close(); }
		catch (Exception e) { System.err.println("\n\n\nError datasource.getconnection.close() : "+e.getMessage()); }
		
		//unregister the driver

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            if (driver.getClass().getClassLoader() == cl) {

                try {
                    System.err.println("Shutting down if MySql JDBC driver "+ driver);
                	if (driver.getClass().getName().toLowerCase().contains("mysql")) {
                		com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();
                	}
                } catch (Exception ex) {
                    System.err.println("ERROR Shutting down MySql JDBC driver "+ driver + " : " + ex);
                }

                try {
                    System.err.println("Deregistering JDBC driver "+ driver);
                    DriverManager.deregisterDriver(driver);

                } catch (Exception ex) {
                    System.err.println("ERROR Deregistering JDBC driver "+ driver + " : " + ex);
                }

            } else {
                System.err.println(
                		"Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader");
            }
        }

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		System.err.println("\n\n\nELI:Context is Initializing...STARTING UP!!!!\n\n\n");

		dataSource = ctx.getBean(DataSource.class);
		executor = ctx.getBean(ExecutorService.class);

	}

}
