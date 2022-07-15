package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<CHAVE, OBJETO> {
    Integer getProximoId(Connection connection) throws SQLException, RegraDeNegocioException;

    OBJETO adicionar(OBJETO object) throws SQLException, RegraDeNegocioException;

    boolean remover(CHAVE id) throws SQLException, RegraDeNegocioException;

    boolean editar(CHAVE id, OBJETO endereco) throws SQLException, RegraDeNegocioException;

    List<OBJETO> listar() throws SQLException, RegraDeNegocioException;
}
