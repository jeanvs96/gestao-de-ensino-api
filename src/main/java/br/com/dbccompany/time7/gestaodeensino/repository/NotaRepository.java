package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.config.ConexaoBancoDeDados;
import br.com.dbccompany.time7.gestaodeensino.dto.NotaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Nota;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class NotaRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_NOTAS.nextval mysequence FROM DUAL";
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

    public void adicionarNotasAluno(Nota nota) throws RegraDeNegocioException {
        Connection con = null;
        try {
            int index = 1;

            con = conexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            nota.setIdNota(proximoID);

            String sql = "INSERT INTO NOTAS (ID_NOTAS, ID_DISCIPLINA, ID_ALUNO) VALUES (?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(index++, nota.getIdNota());
            statement.setInt(index++, nota.getIdDisciplina());
            statement.setInt(index++, nota.getIdAluno());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
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

    public boolean atualizarNotasDisciplina(Integer id, NotaCreateDTO notaCreateDTO) throws RegraDeNegocioException {
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();
            int index = 1;
            int res = 0;
            int controle = 0;
            con = conexaoBancoDeDados.getConnection();

            sql.append("UPDATE NOTAS \nSET");

            if (notaCreateDTO.getNota1() != null) {
                sql.append(" N1 = ?");
                controle++;
            }

            if (notaCreateDTO.getNota2() != null && controle > 0) {
                sql.append(", N2 = ? \n");
                controle++;
            } else if (notaCreateDTO.getNota2() != null) {
                sql.append(" N2 = ? \n");
                controle++;
            }

            if (notaCreateDTO.getNota3() != null && controle > 0) {
                sql.append(", N3 = ? \n");
                controle++;
            } else if (notaCreateDTO.getNota3() != null) {
                sql.append(" N3 = ? \n");
                controle++;
            }

            if (notaCreateDTO.getNota4() != null && controle > 0) {
                sql.append(", N4 = ? \n");
                controle++;
            } else if (notaCreateDTO.getNota4() != null) {
                sql.append(" N4 = ? \n");
                controle++;
            }

            sql.append(" WHERE ID_NOTAS = ? ");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            if (notaCreateDTO.getNota1() != null) {
                statement.setDouble(index++, notaCreateDTO.getNota1());
            }

            if (notaCreateDTO.getNota2() != null) {
                statement.setDouble(index++, notaCreateDTO.getNota2());
            }

            if (notaCreateDTO.getNota3() != null) {
                statement.setDouble(index++, notaCreateDTO.getNota3());
            }

            if (notaCreateDTO.getNota4() != null) {
                statement.setDouble(index++, notaCreateDTO.getNota4());
            }

            statement.setInt(index++, id);

            res = statement.executeUpdate();

            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
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

    public boolean atualizarMediaDisciplina(Integer idNota, Double media) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE NOTAS SET MEDIA = ? WHERE ID_NOTAS = ?";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setDouble(1, media);
            statement.setInt(2, idNota);

            Boolean res = statement.execute();

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
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

    public Nota listarPorDisciplina(Integer idDisciplina, Integer idAluno) throws RegraDeNegocioException {

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM NOTAS WHERE ID_DISCIPLINA = ? AND ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idDisciplina);
            statement.setInt(2, idAluno);

            ResultSet res = statement.executeQuery();
            res.next();

            return getFromResultSet(res);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar banco de dados");
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

    public List<Nota> listarPorAluno(Integer idAluno) throws RegraDeNegocioException {
        List<Nota> notas = new ArrayList<>();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM NOTAS WHERE ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idAluno);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                notas.add(getFromResultSet(res));
            }

            return notas;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
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

    public void removerNotaPorIdDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM NOTAS WHERE ID_DISCIPLINA = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idDisciplina);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
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

    public void removerNotaPorIdAluno(Integer idAluno) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM NOTAS WHERE ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idAluno);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
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

    public void removerNotaPorIdAlunoAndIdDisciplina(Integer idAluno, Integer idDisciplina) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM NOTAS WHERE ID_ALUNO = ? AND ID_DISCIPLINA = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idAluno);
            statement.setInt(2, idDisciplina);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
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

    public Nota listNotaById(Integer idNota) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM NOTAS WHERE ID_NOTAS = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idNota);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                Nota nota = getFromResultSet(res);
                return nota;
            } else {
                throw new RegraDeNegocioException("Notas não encontradas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao acessar o banco de dados");
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
