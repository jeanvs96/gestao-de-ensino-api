package br.com.dbccompany.time7.gestaodeensino.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConexaoBancoDeDados {
    @Value("${jdbc-url}")
    private String url;
    @Value("${jdbc-schema}")
    private String user;
    @Value("${jdbc-username}")
    private String pass;
    @Value(("${jdbc-password}"))
    private String schema;

    @Bean
    @RequestScope
    public Connection getConnection() throws SQLException {

        Connection con = DriverManager.getConnection(url, user, pass);

        con.createStatement().execute("alter session set current_schema=" + schema);

        return con;
    }
}
