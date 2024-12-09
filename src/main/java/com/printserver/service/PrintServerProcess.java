package com.printserver.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrintServerProcess implements Runnable {
    private static final Logger logger = LogManager.getLogger(PrintServerProcess.class);

    private String printer = configuration("dyn.printer");

    private String ws = configuration("dyn.externalWebServiceUrl");

    public void run() {
        try {
            String response = GetLabelsFromPowerAutomate.getfromPowerAutomate(this.printer, this.ws).body().string();
            if (response.length() <= 2) {
                logger.info("No labels found for " + this.printer);
                System.out.println("No labels found for " + this.printer);
            } else {
                WSService service = new WSService();
                if (response.contains("error"))
                    logger.error("Response: " + response + ", Printer: " + this.printer);
                logger.info("Response: " + response + ", Printer: " + this.printer);
                service.processString(response, this.printer);
                System.out.println("Response: " + response + ", Printer: " + this.printer);
            }
        } catch (IOException e) {
            logger.error("IOException occurred while getting labels from Power Automate", e);
            e.printStackTrace();
        }
    }

    public static String configuration(String var) {
        Properties prop = new Properties();
        try {
            FileInputStream input = new FileInputStream("C:/PrintServer/config.properties");
            try {
                prop.load(input);
                String str = prop.getProperty(var);
                input.close();
                return str;
            } catch (Throwable throwable) {
                try {
                    input.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        } catch (IOException ex) {
            logger.error("Error loading configuration for " + var, ex);
            ex.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        PrintServerProcess process = new PrintServerProcess();
        Thread thread = new Thread(process);
        thread.start();
    }
}

