package de.ichibati.officebutler;

import java.io.File;
import java.nio.file.Path;

public class NoInvoiceException extends Exception{

    private Path filePath;
    private Path filename;

    public NoInvoiceException(Path filePath, String reason){
        super(reason);
        this.filePath = filePath;
        filename = filePath.getFileName();
    }

    public NoInvoiceException(File filePath, String reason){
        this(filePath.toPath(), reason);
    }

    public String getMessage(){
        return filename.toString() + " file is not a valid invoice";
    }

    public String fileName(){
        return this.filename.toString();
    }

}
