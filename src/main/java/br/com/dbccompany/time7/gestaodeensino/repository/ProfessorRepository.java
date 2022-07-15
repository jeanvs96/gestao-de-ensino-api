package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.entity.Professor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@AllArgsConstructor
public class ProfessorRepository implements Repositorio<Integer, Professor> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
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
    public Professor adicionar(Professor professor) throws SQLException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            Integer proximoRT = this.getProximoRegistroTrabalho(con);
            professor.setIdProfessor(proximoID);
            professor.setRegistroTrabalho(proximoRT);

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO PROFESSOR (ID_PROFESSOR, NOME, TELEFONE, EMAIL, REGISTRO_TRABALHO, CARGO, SALÁRIO");
            if (professor.getEndereco().getIdEndereco() != null) {
                sql.append(",ID_ENDERECO) \nVALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            } else {
                sql.append(")\nVALUES (?, ?, ?, ?, ?, ?, ?)");
            }

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setInt(1, professor.getIdProfessor());
            statement.setString(2, professor.getNome());
            statement.setString(3, professor.getTelefone());
            statement.setString(4, professor.getEmail());
            statement.setInt(5, professor.getRegistroTrabalho());
            statement.setString(6, professor.getCargo());
            statement.setDouble(7, professor.getSalario());
            if (professor.getEndereco().getIdEndereco() != null) {
                statement.setInt(8, professor.getEndereco().getIdEndereco());
            }

            statement.executeUpdate();

            return professor;
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
            con = conexaoBancoDeDados.getConnection();

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
    public boolean editar(Integer id, Professor professor) throws SQLException {
        Connection con = null;
        int index = 1;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE PROFESSOR SET " +
                    "NOME = ?, TELEFONE = ?, EMAIL = ?, SALÁRIO = ?");
            if (professor.getIdEndereco() != null) {
                sql.append(", ID_ENDERECO = ?");
            }
            sql.append(" WHERE ID_PROFESSOR = ?");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setString(index++, professor.getNome());
            statement.setString(index++, professor.getTelefone());
            statement.setString(index++, professor.getEmail());
            statement.setDouble(index++, professor.getSalario());
            if (professor.getIdEndereco() != null) {
                statement.setInt(index++, professor.getIdEndereco());
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
    public List<Professor> listar() throws SQLException {
        List<Professor> colaboradores = new ArrayList<>();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM PROFESSOR";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                colaboradores.add(getColaboradorFromResultSet(res));
            }
            List<Professor> colaboradoresOrdenadosPorNome = colaboradores.stream()
                    .sorted(Comparator.comparing(Professor::getNome)).toList();
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

    private Professor getColaboradorFromResultSet(ResultSet res) throws SQLException {
        Professor professor = new Professor(res.getString("NOME"));
        professor.setIdColaborador(res.getInt("ID_PROFESSOR"));
        professor.setTelefone(res.getString("TELEFONE"));
        professor.setEmail(res.getString("EMAIL"));
        professor.setRegistroTrabalho(res.getInt("REGISTRO_TRABALHO"));
        professor.setCargo(res.getString("CARGO"));
        professor.setSalario(res.getDouble("SALÁRIO"));
        professor.setIdEndereco(res.getInt("ID_ENDERECO"));
        return professor;
    }

    public List<Professor> conferirColaboradoresComIdEndereco(Integer id) throws SQLException{
        List<Professor> quantidadeColaboradores = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

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

    public Professor professorPorId(Integer id) throws SQLException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

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
