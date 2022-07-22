package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.config.ConexaoBancoDeDados;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@AllArgsConstructor
public class AlunoRepository implements Repositorio<Integer, AlunoEntity> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws RegraDeNegocioException {
        try {
            String sql = "SELECT SEQ_ALUNO.nextval mysequence FROM DUAL";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery((sql));

            if (res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Erro ao acessar o banco de dados");
        }
    }

    public Integer getProximoMatricula(Connection connection) throws RegraDeNegocioException {
        try {
            String sql = "SELECT SEQ_ALUNO_MATRICULA.nextval mysequence FROM DUAL";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery((sql));

            if (res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Erro ao acessar o banco de dados");
        }
    }

    @Override
    public AlunoEntity adicionar(AlunoEntity aluno) throws RegraDeNegocioException {
        Connection con = null;
        Integer index = 1;
        int posicao = 0;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            Integer proximoMT = this.getProximoMatricula(con);
            aluno.setIdAluno(proximoID);
            aluno.setMatricula(proximoMT);

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO ALUNO (ID_ALUNO, NOME, TELEFONE, EMAIL, MATRICULA");
            if (aluno.getIdCurso() == null && aluno.getIdEndereco() == null) {
                sql.append(") VALUES (?, ?, ?, ?, ?)");
            }
            if (aluno.getIdCurso() != null && aluno.getIdEndereco() == null) {
                sql.append(",ID_CURSO) VALUES (?, ?, ?, ?, ?, ?)");
            }
            if (aluno.getIdCurso() == null && aluno.getIdEndereco() != null) {
                sql.append(",ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?)");
            }
            if (aluno.getIdCurso() != null && aluno.getIdEndereco() != null) {
                sql.append(", ID_CURSO, ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?, ?)");
            }

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setInt(index++, aluno.getIdAluno());
            statement.setString(index++, aluno.getNome());
            statement.setString(index++, aluno.getTelefone());
            statement.setString(index++, aluno.getEmail());
            statement.setInt(index++, aluno.getMatricula());
            if (aluno.getIdCurso() != null) {
                statement.setInt(index++, aluno.getIdCurso());
            }
            if (aluno.getIdEndereco() != null) {
                statement.setInt(index++, aluno.getIdEndereco());
            }

            statement.executeUpdate();
            return aluno;
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

    @Override
    public boolean remover(Integer id) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM ALUNO WHERE ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Erro ao acessar o banco de dados");
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
    public boolean editar(Integer id, AlunoEntity aluno) throws RegraDeNegocioException {
        Integer index = 1;
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ALUNO SET \n");
            if (aluno.getNome() != null) {
                sql.append(" NOME = ?,");
            }
            if (aluno.getTelefone() != null){
                sql.append(" TELEFONE = ?,");
            }
            if(aluno.getEmail() != null){
                sql.append(" EMAIL = ?, ");
            }
            if (aluno.getIdCurso() != null){
                sql.append(" ID_CURSO = ?, ");
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE ID_ALUNO = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            if (aluno.getNome() != null) {
                stmt.setString(index++, aluno.getNome());
            }
            if (aluno.getTelefone() != null){
                stmt.setString(index++, aluno.getTelefone());
            }
            if(aluno.getEmail() != null){
                stmt.setString(index++, aluno.getEmail());
            }
            if (aluno.getIdCurso() != null){
                stmt.setInt(index++, aluno.getIdCurso());
            }

            stmt.setInt(index++, id);

            boolean res = stmt.execute();


            return res;
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

    @Override
    public List<AlunoEntity> listar() throws RegraDeNegocioException {
        List<AlunoEntity> alunos = new ArrayList<>();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ALUNO";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                alunos.add(getAlunoFromResultSet(res));
            }
            return alunos.stream().sorted(Comparator.comparing(AlunoEntity::getNome)).toList();
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

    private AlunoEntity getAlunoFromResultSet(ResultSet res) throws SQLException {
        AlunoEntity aluno = new AlunoEntity();
        aluno.setNome(res.getString("NOME"));
        aluno.setIdAluno(res.getInt("ID_ALUNO"));
        aluno.setTelefone(res.getString("TELEFONE"));
        aluno.setEmail(res.getString("EMAIL"));
        aluno.setMatricula(res.getInt("MATRICULA"));
        aluno.setIdEndereco(res.getInt("ID_ENDERECO"));
        aluno.setIdCurso(res.getInt("ID_CURSO"));
        return aluno;
    }

    public List<AlunoEntity> conferirAlunosComIdEndereco(Integer id) throws RegraDeNegocioException {
        List<AlunoEntity> quantidadeAlunos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT *" +
                    "FROM ALUNO \n" +
                    "WHERE ALUNO.ID_ENDERECO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                quantidadeAlunos.add(getAlunoFromResultSet(res));
            }
            return quantidadeAlunos;
        } catch (SQLException e) {
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

    public void removerPorIdCurso(Integer id) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE ALUNO SET ID_CURSO = NULL WHERE ID_CURSO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            statement.execute();
        } catch (SQLException e) {
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

    public List<AlunoEntity> listByIdCurso(Integer idCurso) throws RegraDeNegocioException {
        Connection con = null;
        try {
            List<AlunoEntity> alunos = new ArrayList<>();
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ALUNO WHERE ID_CURSO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idCurso);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                alunos.add(getAlunoFromResultSet(res));
            }

            return alunos;
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

    public AlunoEntity listByIdAluno(Integer idAluno) throws RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ALUNO WHERE ID_ALUNO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idAluno);

            ResultSet res = statement.executeQuery();
            AlunoEntity aluno = new AlunoEntity();
            if (res.next()) {
                aluno = getAlunoFromResultSet(res);
            }

            return aluno;
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

