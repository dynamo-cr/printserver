package com.printserver.service;

import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.SimpleDoc;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class WSService extends DynPrintServerApplication {
    @GetMapping({"/print"})
    public String processString(@RequestBody String inputString, String printerName) {
        String response = "";
        String label = inputString;
        PrintService defaultPrinter = null;
        String zebra = printerName;
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        for (PrintService printService : printServices) {
            if (printService.getName().equals(zebra)) {
                defaultPrinter = printService;
                try {
                    byte[] bytes = label.getBytes();
                    DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                    Doc doc = new SimpleDoc(bytes, flavor, null);
                    defaultPrinter.createPrintJob().print(doc, null);
                    LocalDateTime today = LocalDateTime.now();
                    response = "Imprimiendo " + today.toString();
                } catch (Exception e) {
                    response = "Error al imprimir: " + e.getMessage();
                }
            }
        }
        return response;
    }
}
