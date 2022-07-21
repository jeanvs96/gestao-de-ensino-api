package br.com.dbccompany.time7.gestaodeensino.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConexaoBancoDeDados {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String pass;
    @Value(("${spring.datasource.hikari.connection-init-sql}"))
    private String schema;

    public Connection getConnection() throws SQLException {

        Connection con = DriverManager.getConnection(url, user, pass);

        con.createStatement().execute(schema);

        return con;
    }
}
