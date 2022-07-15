package br.com.dbccompany.time7.gestaodeensino.repository;

import models.Colaborador;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProfessorRepository implements Repositorio<Integer, Colaborador> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT VEMSER_JEAN.SEQ_PROFESSOR.nextval mysequence FROM DUAL";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery((sql));

            if (res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        }
    }

    public Integer getProximoRegistroTrabalho(Connection connection) throws SQLException {
        try {
            String sql = "SELECT VEMSER_JEAN.SEQ_REGISTRO_TRABALHO.nextval mysequence FROM DUAL";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery((sql));

            if (res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        }
    }

    @Override
    public Colaborador adicionar(Colaborador colaborador) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            Integer proximoRT = this.getProximoRegistroTrabalho(con);
            colaborador.setIdColaborador(proximoID);
            colaborador.setRegistroTrabalho(proximoRT);

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO PROFESSOR (ID_PROFESSOR, NOME, TELEFONE, EMAIL, REGISTRO_TRABALHO, CARGO, SALÁRIO");
            if (colaborador.getEndereco().getIdEndereco() != null) {
                sql.append(",ID_ENDERECO) \nVALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            } else {
                sql.append(")\nVALUES (?, ?, ?, ?, ?, ?, ?)");
            }

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setInt(1, colaborador.getIdColaborador());
            statement.setString(2, colaborador.getNome());
            statement.setString(3, colaborador.getTelefone());
            statement.setString(4, colaborador.getEmail());
            statement.setInt(5, colaborador.getRegistroTrabalho());
            statement.setString(6, colaborador.getCargo());
            statement.setDouble(7, colaborador.getSalario());
            if (colaborador.getEndereco().getIdEndereco() != null) {
                statement.setInt(8, colaborador.getEndereco().getIdEndereco());
            }

            statement.executeUpdate();

            return colaborador;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM PROFESSOR WHERE ID_PROFESSOR = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.getCause();
            }
        }
    }


    @Override
    public boolean editar(Integer id, Colaborador colaborador) throws SQLException {
        Connection con = null;
        int index = 1;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE PROFESSOR SET " +
                    "NOME = ?, TELEFONE = ?, EMAIL = ?, SALÁRIO = ?");
            if (colaborador.getIdEndereco() != null) {
                sql.append(", ID_ENDERECO = ?");
            }
            sql.append(" WHERE ID_PROFESSOR = ?");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setString(index++, colaborador.getNome());
            statement.setString(index++, colaborador.getTelefone());
            statement.setString(index++, colaborador.getEmail());
            statement.setDouble(index++, colaborador.getSalario());
            if (colaborador.getIdEndereco() != null) {
                statement.setInt(index++, colaborador.getIdEndereco());
            }
            statement.setInt(index++, id);

            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Colaborador> listar() throws SQLException {
        List<Colaborador> colaboradores = new ArrayList<>();

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM PROFESSOR";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                colaboradores.add(getColaboradorFromResultSet(res));
            }
            List<Colaborador> colaboradoresOrdenadosPorNome = colaboradores.stream()
                    .sorted(Comparator.comparing(Colaborador::getNome)).toList();
            return colaboradoresOrdenadosPorNome;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Colaborador getColaboradorFromResultSet(ResultSet res) throws SQLException {
        Colaborador colaborador = new Colaborador(res.getString("NOME"));
        colaborador.setIdColaborador(res.getInt("ID_PROFESSOR"));
        colaborador.setTelefone(res.getString("TELEFONE"));
        colaborador.setEmail(res.getString("EMAIL"));
        colaborador.setRegistroTrabalho(res.getInt("REGISTRO_TRABALHO"));
        colaborador.setCargo(res.getString("CARGO"));
        colaborador.setSalario(res.getDouble("SALÁRIO"));
        colaborador.setIdEndereco(res.getInt("ID_ENDERECO"));
        return colaborador;
    }

    public List<Colaborador> conferirColaboradoresComIdEndereco(Integer id) throws SQLException{
        List<Colaborador> quantidadeColaboradores = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT *" +
                    "FROM PROFESSOR \n" +
                    "WHERE PROFESSOR.ID_ENDERECO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                quantidadeColaboradores.add(getColaboradorFromResultSet(res));
            }
            return quantidadeColaboradores;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Colaborador professorPorId(Integer id) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM PROFESSOR WHERE ID_PROFESSOR = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            res.next();

            return getColaboradorFromResultSet(res);
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.getCause();
            }
        }
    }
}
