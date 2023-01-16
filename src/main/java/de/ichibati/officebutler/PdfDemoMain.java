package de.ichibati.officebutler;

import org.apache.pdfbox.pdmodel.PDDocument;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PdfDemoMain {

    public static void main(String[] args) throws IOException {

        try{
            File pdf = Path.of(System.getProperty("user.home"), "Documents", "Abrechnung.pdf").toFile();
            Invoices file = new Invoices(pdf);

            for (PDDocument files : file.splitInvoices()){
                EmployeeFactory.createEmployee(files);
            }

            SharedEmployeeDatabase.getInstance().getDatabase().forEach(Employee::invoicesToFile);
            file.getPdfFile().close();



        }catch (IOException e){
            e.printStackTrace();
        }


        List<Employee> databse =  SharedEmployeeDatabase.getInstance().getDatabase();
        System.out.println("Done.");










    }
}
