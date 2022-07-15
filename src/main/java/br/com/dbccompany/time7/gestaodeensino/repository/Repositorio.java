package br.com.dbccompany.time7.gestaodeensino.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<CHAVE, OBJETO> {
    Integer getProximoId(Connection connection) throws SQLException;

    OBJETO adicionar(OBJETO object) throws SQLException;

    boolean remover(CHAVE id) throws SQLException;

    boolean editar(CHAVE id, OBJETO endereco) throws SQLException;

    List<OBJETO> listar() throws SQLException;
}
