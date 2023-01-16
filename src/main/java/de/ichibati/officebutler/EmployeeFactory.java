package de.ichibati.officebutler;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public final class EmployeeFactory {

    private static final List<Employee> employees = SharedEmployeeDatabase.getInstance().getDatabase();

    public static void createEmployee(PDDocument pdf) throws IOException {
        Invoice invoice = new Invoice(pdf);
        InvoiceDataExtractor extractor = new InvoiceDataExtractor(pdf);
        int id = extractor.getEmployeeID();
        Stream<Employee> employeeStream = employees.stream();
        Optional<Employee> employeeOptional = employeeStream.filter(e -> e.getID() == id).findFirst();

        if(employeeOptional.isPresent()){
            employeeOptional.get().addInvoice(invoice);
        }else{
            employees.add(new Employee(pdf));
        }










    }
}
