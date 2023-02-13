package de.ichibati.officebutler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Invoice {

    private static final int CORRECTION_DATE = 1;
    private static final int INVOICE_DATE = 0;

    private PDDocument invoiceAsPDF;
    private final LocalDate dateOfInvoice;
    private LocalDate dateOfCorrection;
    private boolean isCorrection;
    private String filename;

    public Invoice(PDDocument employeeInvoicePDF) throws IOException {

        InvoiceDataExtractor extractor = new InvoiceDataExtractor(employeeInvoicePDF);

        invoiceAsPDF = employeeInvoicePDF;

        dateOfInvoice = extractor.getDateOfInvoice()[INVOICE_DATE];


        StringBuilder filenameBuilder = new StringBuilder().append(extractor.getNameofEmployee())
                .append(" Abrechnung ")
                .append(dateOfInvoice.getMonthOfYear())
                .append(".")
                .append(dateOfInvoice.getYear());


        if(extractor.getIsCorrection()){

            isCorrection = true;
            dateOfCorrection = extractor.getDateOfInvoice()[CORRECTION_DATE];

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

    public String getMonthAsString(){
        return dateOfInvoice.toString("MMMM yyyy");
    }







}
