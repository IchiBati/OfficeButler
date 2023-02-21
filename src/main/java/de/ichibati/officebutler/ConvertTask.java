package de.ichibati.officebutler;

import javafx.concurrent.Task;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.nio.file.Path;
import java.util.List;

public class ConvertTask extends Task<String> {

    private final Path inputFile;

    private final Invoices invoices;
    private final List<Employee> employees = SharedEmployeeDatabase.getInstance().getDatabase();
    public ConvertTask(Path inputFile, Invoices invoices){
        this.inputFile = inputFile;
        this.invoices = invoices;
    }

    @Override
    protected String call() throws Exception {
        int i = 1;

        for(PDDocument files : invoices.splitInPages()){
            EmployeeFactory.createEmployee(files);
        }

        for (Employee e : employees){
            e.invoicesToFile(inputFile);
            updateProgress(i++, employees.size());
        }

        return "Status: Converting Invoices succeed";
    }
}
