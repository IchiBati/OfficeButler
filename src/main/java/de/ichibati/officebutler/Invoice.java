package de.ichibati.officebutler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.joda.time.LocalDate;

import java.io.IOException;

public class Invoice {

    private PDDocument invoiceAsPDF;
    private final LocalDate dateOfInvoice;
    private LocalDate dateOfCorrection;
    private  boolean isCorrection;
    private String filename;

    public Invoice(PDDocument employeeInvoicePDF) throws IOException {

        InvoiceDataExtractor extractor = new InvoiceDataExtractor(employeeInvoicePDF);

        invoiceAsPDF = employeeInvoicePDF;

        dateOfInvoice = extractor.getDateOfInvoice()[0];

        StringBuilder filenameBuilder = new StringBuilder().append(extractor.getNameofEmployee())
                .append(" Abrechnung ")
                .append(dateOfInvoice.getMonthOfYear())
                .append(".")
                .append(dateOfInvoice.getYear());


        if(extractor.getIsCorrection()){

            isCorrection = true;
            dateOfCorrection = extractor.getDateOfInvoice()[1];

            filenameBuilder
                    .append(" (Korrektur ")
                    .append(dateOfCorrection.getMonthOfYear())
                    .append(".")
                    .append(dateOfCorrection.getYear())
                    .append(")");

        }

        filename = filenameBuilder.append(" .pdf").toString();


    }

    public PDDocument getInvoiceAsPDF() {
        return invoiceAsPDF;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }







}
