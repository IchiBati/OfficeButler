package de.ichibati.officebutler;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Invoices {

    private PDDocument pdfFile;
    private List<PDDocument> pagesOfInputInvoice;
    private int numberOfPages;
    private Path invoiceFilePath;
    public String monthOfInvoice;


    public Invoices(Path inputFile) throws NoInvoiceException {


        try{
            invoiceFilePath = inputFile;
            pdfFile = Loader.loadPDF(invoiceFilePath.toFile());
            numberOfPages = pdfFile.getNumberOfPages();

        }catch (IOException e){
            throw new NoInvoiceException(invoiceFilePath, e.getMessage());
        }

    }

    public PDDocument getPdfFile() {
        return pdfFile;
    }

    public List<PDDocument> splitInPages() throws IOException{

        pagesOfInputInvoice = new Splitter().split(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        PageExtractor extractor = new PageExtractor(pdfFile);
        List<PDDocument> mergedPagesOfEmployees = new ArrayList<>();

        for (int i = 0 ; i < numberOfPages;){

            String pageText = stripper.getText(pagesOfInputInvoice.get(i));
            Pattern pattern = Pattern.compile("Seite 1 von ([1-9]+)");
            Matcher matcher = pattern.matcher(pageText);

            if (matcher.find()){

                extractor.setStartPage(i + 1);
                extractor.setEndPage(Integer.parseInt(matcher.group(1)) + i);
                i = extractor.getEndPage();
                PDDocument page = extractor.extract();
                mergedPagesOfEmployees.add(page);
            }
        }

        return mergedPagesOfEmployees;
    }


    public String getMonthOfInvoice() {
        return monthOfInvoice;
    }
}
