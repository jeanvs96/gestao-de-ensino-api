package br.com.dbccompany.time7.gestaodeensino.repository;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {
    @Value("${spring.datasource.url}")
    private static String url;
    @Value("${spring.datasource.username}")
    private static String user;
    @Value("${spring.datasource.password}")
    private static String pass;
    @Value(("$spring.sql.init.schema-locations"))
    private static String schema;

    public static Connection getConnection() throws SQLException {

        Connection con = DriverManager.getConnection(url, user, pass);

        con.createStatement().execute("alter session set current_schema=" + schema);

        return con;
    }
}

