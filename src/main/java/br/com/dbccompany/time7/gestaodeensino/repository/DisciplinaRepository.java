package br.com.dbccompany.time7.gestaodeensino.repository;

import models.Disciplina;
import models.DisciplinaXCurso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaRepository implements Repositorio<Integer, Disciplina> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT VEMSER_JEAN.SEQ_DISCIPLINA.nextval mysequence FROM DUAL";
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
    public Disciplina adicionar(Disciplina disciplina) throws SQLException {
        Connection con = null;
        int posicao = 0;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            disciplina.setIdDisciplina(proximoID);

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO DISCIPLINA (ID_DISCIPLINA, NOME");
            if (disciplina.getIdProfessor() != null) {
                sql.append(", ID_PROFESSOR) \n VALUES (?, ?, ?)");
            } else {
                sql.append(") \n VALUES (?, ?)");
            }

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setInt(1, disciplina.getIdDisciplina());
            statement.setString(2, disciplina.getNome());
            if (disciplina.getIdProfessor() != null) {
                statement.setInt(3, disciplina.getIdProfessor());
            }

            statement.executeUpdate();
            return disciplina;
        } catch (SQLException e) {
            e.printStackTrace();
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

            String sql = "DELETE FROM DISCIPLINA WHERE ID_DISCIPLINA = ?";

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
    public boolean editar(Integer id, Disciplina disciplina) throws SQLException {
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();
            int index = 1;
            int res = 0;
            con = ConexaoBancoDeDados.getConnection();

            sql.append("UPDATE DISCIPLINA \nSET");

            if (disciplina.getNome() != null) {
                sql.append(" NOME = ?");
            }

            if (disciplina.getIdProfessor() != null) {
                sql.append(", ID_PROFESSOR = ? \n");
            }

            sql.append(" WHERE ID_DISCIPLINA = ? ");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            if (disciplina.getNome() != null) {
                statement.setString(index++, disciplina.getNome());
            }

            if (disciplina.getIdProfessor() != null) {
                statement.setInt(index++, disciplina.getIdProfessor());
            }

            statement.setInt(index++, disciplina.getIdDisciplina());

            res = statement.executeUpdate();

            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
    public List<Disciplina> listar() throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM DISCIPLINA";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                disciplinas.add(getDisciplinaFromResultSet(res));
            }

            return disciplinas;
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

    private Disciplina getDisciplinaFromResultSet(ResultSet res) throws SQLException {
        Disciplina disciplina = new Disciplina();
        disciplina.setIdDisciplina(res.getInt("ID_DISCIPLINA"));
        disciplina.setNome(res.getString("NOME"));
        disciplina.setIdProfessor(res.getInt("ID_PROFESSOR"));
        return disciplina;
    }

    public Boolean conferirIdDisciplina(Integer id) throws SQLException {
        Connection con = null;
        Boolean controle = false;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM DISCIPLINA WHERE ID_DISCIPLINA = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            controle = res.next();
            return controle;
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

    public void removerProfessor(Integer idProfessor) throws SQLException {
        Connection con = null;
        try {

            con = ConexaoBancoDeDados.getConnection();

            String sql = ("UPDATE DISCIPLINA SET ID_PROFESSOR = NULL WHERE ID_PROFESSOR = ?");

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, idProfessor);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<Disciplina> listarPorId(List<DisciplinaXCurso> disciplinaXCurso) throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            for (DisciplinaXCurso objeto : disciplinaXCurso) {
                String sql = "SELECT * FROM DISCIPLINA WHERE ID_DISCIPLINA = ?";

                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, objeto.getIdDisciplina());
                ResultSet res = statement.executeQuery();
                while (res.next()) {
                    disciplinas.add(getDisciplinaFromResultSet(res));
                }
            }

            return disciplinas;
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
}