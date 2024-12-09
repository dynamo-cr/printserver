package com.printserver.service;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynPrintServerApplication {

	public static void main(String[] args) throws IOException {
		long time = Long.parseLong(PrintServerProcess.configuration("dyn.time"));
		Logger logger = LogManager.getLogger(com.printserver.service.DynPrintServerApplication.class);
		SpringApplication.run(com.printserver.service.DynPrintServerApplication.class, args);
		logger.info("Iniciando Print Server");
		System.out.println("Iniciando Print Server");
		PrintServerProcess printserver = new PrintServerProcess();
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate((Runnable)printserver, 0L, time, TimeUnit.SECONDS);
	}
}
