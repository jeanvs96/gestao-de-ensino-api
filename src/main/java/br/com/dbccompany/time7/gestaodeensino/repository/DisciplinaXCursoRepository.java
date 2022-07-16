package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.config.ConexaoBancoDeDados;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaXCurso;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class DisciplinaXCursoRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_DISCIPLINA_X_CURSO.nextval mysequence FROM DUAL";
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

    public void adicionarDisciplinaNoCurso(DisciplinaXCurso disciplinaXCurso) throws SQLException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            disciplinaXCurso.setId(proximoID);

            String sql = "INSERT INTO DISCIPLINA_X_CURSO (ID_DISCIPLINA_X_CURSO, ID_DISCIPLINA, ID_CURSO)" +
                    " VALUES (?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, disciplinaXCurso.getId());
            statement.setInt(2, disciplinaXCurso.getIdDisciplina());
            statement.setInt(3, disciplinaXCurso.getIdCurso());

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

    public void removerPorDisciplinaECurso(Integer idCurso, Integer idDisciplina) throws SQLException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM DISCIPLINA_X_CURSO WHERE ID_CURSO = ? AND ID_DISCIPLINA = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idCurso);
            statement.setInt(2, idDisciplina);

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

    public List<DisciplinaXCurso> listarPorCurso(Integer idCurso) throws SQLException {
        List<DisciplinaXCurso> disciplinaXCursos = new ArrayList<>();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM DISCIPLINA_X_CURSO WHERE ID_CURSO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idCurso);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                disciplinaXCursos.add(getFromResultSet(res));
            }

            return disciplinaXCursos;
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

    private DisciplinaXCurso getFromResultSet(ResultSet res) throws SQLException {
        DisciplinaXCurso disciplinaXCurso = new DisciplinaXCurso();
        disciplinaXCurso.setId(res.getInt("ID_DISCIPLINA_X_CURSO"));
        disciplinaXCurso.setIdCurso(res.getInt("ID_CURSO"));
        disciplinaXCurso.setIdDisciplina(res.getInt("ID_DISCIPLINA"));
        return disciplinaXCurso;
    }

    public void removerPorIdDisciplina(Integer idDisciplina) throws SQLException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM DISCIPLINA_X_CURSO WHERE ID_DISCIPLINA = ?";

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

    public void removerPorIdCurso(Integer idCurso) throws SQLException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM DISCIPLINA_X_CURSO WHERE ID_CURSO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idCurso);

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
