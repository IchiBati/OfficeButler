package de.ichibati.officebutler;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Employee {

    private final Integer ID;
    private String firstname;
    private String lastname;
    private LocalDate dayOfBirth;
    private Address address;
    private List<Invoice> invoicesOfEmployee;


    public Employee(PDDocument invoice, Integer id) throws IOException {
       InvoiceDataExtractor extractor = new InvoiceDataExtractor(invoice);
       invoicesOfEmployee = new ArrayList<>();
       invoicesOfEmployee.add(new Invoice(invoice));
       this.ID = extractor.getEmployeeID();
       this.lastname = extractor.getNameofEmployee().replaceFirst("^.+ ", "");
       this.firstname = extractor.getNameofEmployee().replace("\s.+$", "");
    }

    public Employee(PDDocument invoice) throws IOException {
        this(invoice, null);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addInvoice(Invoice invoice){
        invoicesOfEmployee.add(invoice);
    }

    public Integer getID() {
        return ID;
    }

    public List<Invoice> getInvoicesOfEmployee() {
        return invoicesOfEmployee;
    }

    public boolean invoicesToFile(Path path){
        String fileFolderName = MyApp.inputFile.getFileName().toString().replaceFirst("[.][^.]+$", "");
        for (Invoice invoice : invoicesOfEmployee){
            try {
                Path outputFolder = Path.of(path.getParent().toString() + File.separator + fileFolderName);
                Path outputFile = Path.of(outputFolder.toString() + File.separator + invoice.getFilename());
                Files.createDirectories(outputFolder);
                invoice.getInvoiceAsPDF().save(outputFile.toFile());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }




    @Override
    public String toString() {
        return "Employee{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dayOfBirth=" + dayOfBirth +
                ", adress=" + address +
                ", ID=" + ID +
                '}';
    }
}
