package br.com.dbccompany.time7.gestaodeensino.repositoryOLD;

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
    public AlunoEntity adicionar(AlunoEntity alunoEntity) throws RegraDeNegocioException {
        Connection con = null;
        Integer index = 1;
        int posicao = 0;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            Integer proximoMT = this.getProximoMatricula(con);
            alunoEntity.setIdAluno(proximoID);
            alunoEntity.setMatricula(proximoMT);

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO ALUNO (ID_ALUNO, NOME, TELEFONE, EMAIL, MATRICULA");
            if (alunoEntity.getIdCurso() == null && alunoEntity.getIdEndereco() == null) {
                sql.append(") VALUES (?, ?, ?, ?, ?)");
            }
            if (alunoEntity.getIdCurso() != null && alunoEntity.getIdEndereco() == null) {
                sql.append(",ID_CURSO) VALUES (?, ?, ?, ?, ?, ?)");
            }
            if (alunoEntity.getIdCurso() == null && alunoEntity.getIdEndereco() != null) {
                sql.append(",ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?)");
            }
            if (alunoEntity.getIdCurso() != null && alunoEntity.getIdEndereco() != null) {
                sql.append(", ID_CURSO, ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?, ?)");
            }

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setInt(index++, alunoEntity.getIdAluno());
            statement.setString(index++, alunoEntity.getNome());
            statement.setString(index++, alunoEntity.getTelefone());
            statement.setString(index++, alunoEntity.getEmail());
            statement.setInt(index++, alunoEntity.getMatricula());
            if (alunoEntity.getIdCurso() != null) {
                statement.setInt(index++, alunoEntity.getIdCurso());
            }
            if (alunoEntity.getIdEndereco() != null) {
                statement.setInt(index++, alunoEntity.getIdEndereco());
            }

            statement.executeUpdate();
            return alunoEntity;
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
    public boolean editar(Integer id, AlunoEntity alunoEntity) throws RegraDeNegocioException {
        Integer index = 1;
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ALUNO SET \n");
            if (alunoEntity.getNome() != null) {
                sql.append(" NOME = ?,");
            }
            if (alunoEntity.getTelefone() != null){
                sql.append(" TELEFONE = ?,");
            }
            if(alunoEntity.getEmail() != null){
                sql.append(" EMAIL = ?, ");
            }
            if (alunoEntity.getIdCurso() != null){
                sql.append(" ID_CURSO = ?, ");
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE ID_ALUNO = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            if (alunoEntity.getNome() != null) {
                stmt.setString(index++, alunoEntity.getNome());
            }
            if (alunoEntity.getTelefone() != null){
                stmt.setString(index++, alunoEntity.getTelefone());
            }
            if(alunoEntity.getEmail() != null){
                stmt.setString(index++, alunoEntity.getEmail());
            }
            if (alunoEntity.getIdCurso() != null){
                stmt.setInt(index++, alunoEntity.getIdCurso());
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
        List<AlunoEntity> alunoEntities = new ArrayList<>();

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ALUNO";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                alunoEntities.add(getAlunoFromResultSet(res));
            }
            return alunoEntities.stream().sorted(Comparator.comparing(AlunoEntity::getNome)).toList();
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
        AlunoEntity alunoEntity = new AlunoEntity();
        alunoEntity.setNome(res.getString("NOME"));
        alunoEntity.setIdAluno(res.getInt("ID_ALUNO"));
        alunoEntity.setTelefone(res.getString("TELEFONE"));
        alunoEntity.setEmail(res.getString("EMAIL"));
        alunoEntity.setMatricula(res.getInt("MATRICULA"));
        alunoEntity.setIdEndereco(res.getInt("ID_ENDERECO"));
        alunoEntity.setIdCurso(res.getInt("ID_CURSO"));
        return alunoEntity;
    }

    public List<AlunoEntity> conferirAlunosComIdEndereco(Integer id) throws RegraDeNegocioException {
        List<AlunoEntity> quantidadeAlunoEntities = new ArrayList<>();
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
                quantidadeAlunoEntities.add(getAlunoFromResultSet(res));
            }
            return quantidadeAlunoEntities;
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
            List<AlunoEntity> alunoEntities = new ArrayList<>();
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ALUNO WHERE ID_CURSO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, idCurso);

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                alunoEntities.add(getAlunoFromResultSet(res));
            }

            return alunoEntities;
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
            AlunoEntity alunoEntity = new AlunoEntity();
            if (res.next()) {
                alunoEntity = getAlunoFromResultSet(res);
            }

            return alunoEntity;
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

