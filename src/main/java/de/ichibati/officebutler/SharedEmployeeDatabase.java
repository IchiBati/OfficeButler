package de.ichibati.officebutler;

import java.util.ArrayList;
import java.util.List;

public class SharedEmployeeDatabase {
    private List<Employee> database;
    private static SharedEmployeeDatabase singleton = new SharedEmployeeDatabase();

    private SharedEmployeeDatabase(){
        database = new ArrayList<>();
    }

    public static SharedEmployeeDatabase getInstance(){
        return singleton;
    }

    public List<Employee> getDatabase(){
        return database;
    }
}
