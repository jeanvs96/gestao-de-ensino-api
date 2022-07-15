package br.com.dbccompany.time7.gestaodeensino.exceptions;

import java.sql.SQLException;

public class DBException extends SQLException {
    public DBException(Throwable msg){
        super(msg);
    }
}