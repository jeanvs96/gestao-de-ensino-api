package br.com.dbccompany.time7.gestaodeensino.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
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

        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@vemser-dbc.dbccompany.com.br:25000:xe", "EQUIPE_1", "Xh7iHXHUpA");

        con.createStatement().execute("alter session set current_schema=EQUIPE_1");

        return con;
    }
}

