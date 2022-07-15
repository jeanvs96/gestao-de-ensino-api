package br.com.dbccompany.time7.gestaodeensino.repository;

import models.Nota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT VEMSER_JEAN.SEQ_NOTAS.nextval mysequence FROM DUAL";
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

    public void adicionerNotasAluno(Nota nota) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            nota.setIdNota(proximoID);

            String sql = "INSERT INTO NOTAS (ID_NOTAS, N1, N2, N3, N4, MEDIA, ID_DISCIPLINA, ID_ALUNO)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, nota.getIdNota());
            statement.setDouble(2, nota.getNota1());
            statement.setDouble(3, nota.getNota2());
            statement.setDouble(4, nota.getNota3());
            statement.setDouble(5, nota.getNota4());
            statement.setDouble(6, nota.getMedia());
            statement.setInt(7, nota.getIdDisciplina());
            statement.setInt(8, nota.getIdAluno());

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

    public boolean atualizarNotasDisciplina(Integer id, Nota nota) throws SQLException {
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();
            int index = 1;
            int res = 0;
            con = ConexaoBancoDeDados.getConnection();

            sql.append("UPDATE NOTAS \nSET");

            if (nota.getNota1() != null) {
                sql.append(" N1 = ?");
            }

            if (nota.getNota2() != null) {
                sql.append(", N2 = ? \n");
            }

            if (nota.getNota3() != null) {
                sql.append(", N3 = ? \n");
            }

            if (nota.getNota4() != null) {
                sql.append(", N4 = ? \n");
            }

            if (nota.getMedia() != null) {
                sql.append(", MEDIA = ?");
            }


            sql.append(" WHERE ID_NOTAS = ? ");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            if (nota.getNota1() != null) {
                statement.setDouble(index++, nota.getNota1());
            }

            if (nota.getNota2() != null) {
                statement.setDouble(index++, nota.getNota2());
            }

            if (nota.getNota3() != null) {
                statement.setDouble(index++, nota.getNota3());
            }

            if (nota.getNota4() != null) {
                statement.setDouble(index++, nota.getNota4());
            }

            if (nota.getMedia() != null) {
                statement.setDouble(index++, nota.getMedia());
            }

            statement.setInt(index++, id);

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

    public Nota listarPorDisciplina(Integer idDisciplina, Integer idAluno) throws SQLException {

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM NOTAS WHERE ID_DISCIPLINA = ? AND ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idDisciplina);
            statement.setInt(2, idAluno);

            ResultSet res = statement.executeQuery();
            res.next();

            return getFromResultSet(res);
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

    public List<Nota> listarPorAluno(Integer idAluno) throws SQLException {
        List<Nota> notas = new ArrayList<>();

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM NOTAS WHERE ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idAluno);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                notas.add(getFromResultSet(res));
            }

            return notas;
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

    private Nota getFromResultSet(ResultSet res) throws SQLException {
        Nota nota = new Nota();
        nota.setIdNota(res.getInt("ID_NOTAS"));
        nota.setIdAluno(res.getInt("ID_ALUNO"));
        nota.setIdDisciplina(res.getInt("ID_DISCIPLINA"));
        nota.setNota1(res.getDouble("N1"));
        nota.setNota2(res.getDouble("N2"));
        nota.setNota3(res.getDouble("N3"));
        nota.setNota4(res.getDouble("N4"));
        nota.setMedia(res.getDouble("MEDIA"));

        return nota;
    }

    public void removerNotaPorIdDisciplina(Integer idDisciplina) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM NOTAS WHERE ID_DISCIPLINA = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idDisciplina);

            statement.execute();
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

    public void removerNotaPorIdAluno(Integer idAluno) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM NOTAS WHERE ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idAluno);

            statement.execute();
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
