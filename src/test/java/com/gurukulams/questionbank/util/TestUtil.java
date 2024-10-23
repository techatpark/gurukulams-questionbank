package com.gurukulams.questionbank.util;

import com.gurukulams.questionbank.DataManager;
import org.postgresql.ds.PGSimpleDataSource;
public class TestUtil {
    public static DataManager dataManager() {
        PGSimpleDataSource ds = new PGSimpleDataSource() ;
        ds.setURL( "jdbc:postgresql://localhost:5432/questionbank" );
        ds.setUser( "questionbank" );
        ds.setPassword( "questionbank" );
        return DataManager.getManager(ds);
    }

}
