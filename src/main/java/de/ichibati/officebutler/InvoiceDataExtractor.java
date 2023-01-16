package de.ichibati.officebutler;

import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.joda.time.LocalDate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InvoiceDataExtractor {


    private final PDDocument invoice;
    private final String[] invoiceData;
    private final Map<String, Integer> monthNameToNumber;

    public InvoiceDataExtractor(PDDocument invoice) throws IOException {

        this.invoice = invoice;
        invoiceData = new PDFTextStripper().getText(new PageExtractor(invoice, 1, 1).extract()).split("\n");


        monthNameToNumber = new HashMap<>();
        monthNameToNumber.put("Januar", 1);
        monthNameToNumber.put("Februar", 2);
        monthNameToNumber.put("März", 3);
        monthNameToNumber.put("April", 4);
        monthNameToNumber.put("Mai", 5);
        monthNameToNumber.put("Juni", 6);
        monthNameToNumber.put("Juli", 7);
        monthNameToNumber.put("August", 8);
        monthNameToNumber.put("September", 9);
        monthNameToNumber.put("Oktober", 10);
        monthNameToNumber.put("November", 11);
        monthNameToNumber.put("Dezember", 12);
    }

    public LocalDate[] getDateOfInvoice() throws IOException {

        String dateOfInvoice = invoiceData[7].trim();

        Pattern pattern = Pattern.compile("(.+) (20[0-9][0-9])(?:(?: Korrektur in )(0[1-9]|1[0-2]).(20[0-9][0-9]))?");
        Matcher matcher = pattern.matcher(dateOfInvoice);

        int month, year;
        int correctionMonth, correctionYear;
        LocalDate[] dates = new LocalDate[2];

        if(matcher.matches()) {
            month = monthNameToNumber.get(matcher.group(1));
            year = Integer.parseInt(matcher.group(2));
            dates[0] = new LocalDate(year, month, 1);
        }

        if(getIsCorrection()){
            correctionMonth = Integer.parseInt(matcher.group(3));
            correctionYear = Integer.parseInt(matcher.group(4));
            dates[1] = new LocalDate(correctionYear, correctionMonth, 1);
        }

        // LocalDate[0] == Dates of Invoice
        // LocalDate[1] == Dates of Correction
        return dates;


    }

    public boolean getIsCorrection(){
        String dateOfInvoice = invoiceData[7].trim();
        Pattern pattern = Pattern.compile("Korrektur");
        Matcher matcher = pattern.matcher(dateOfInvoice);
        return matcher.find();

    }

    public String getEmployeeSalutation(){
        return invoiceData[3].trim().equals("Herrn") ? "Herr" : "Frau";
    }

    public String getNameofEmployee(){
        return (invoiceData[4].trim());
    }

    public int getEmployeeID(){
        for(int i = 0 ; i < invoiceData.length ; i++){
            if (invoiceData[i].trim().equals("Personal-Nr.")){
                return Integer.parseInt(invoiceData[i + 1].trim().replaceFirst("^0+(?!$)", ""));
            }
        }
        return 1;
    }




}
