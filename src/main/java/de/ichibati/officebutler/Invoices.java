package de.ichibati.officebutler;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Invoices {

    private PDDocument pdfFile;
    private List<PDDocument> splittedInvoices;
    private int numberOfPages;


    public Invoices(File file) throws IOException {

        pdfFile = Loader.loadPDF(file);
        numberOfPages = pdfFile.getNumberOfPages();

    }

    public PDDocument getPdfFile() {
        return pdfFile;
    }

    public List<PDDocument> splitInvoices() throws IOException{

        splittedInvoices = new Splitter().split(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        PageExtractor extractor = new PageExtractor(pdfFile);
        List<PDDocument> mergedPagesOfEmployees = new ArrayList<>();

        for (int i = 0 ; i < numberOfPages; ){

            String pageText = stripper.getText(splittedInvoices.get(i));
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









}
