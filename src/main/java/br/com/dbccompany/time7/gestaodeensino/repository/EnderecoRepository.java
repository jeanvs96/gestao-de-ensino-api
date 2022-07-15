package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Endereco;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class EnderecoRepository implements Repositorio<Integer, Endereco> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT VEMSER_JEAN.SEQ_ENDERECO.nextval mysequence FROM DUAL";
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
    public Endereco adicionar(Endereco endereco) throws SQLException {
        Connection con = null;
        int posicao = 0;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            endereco.setIdEndereco(proximoID);

            String sql = "INSERT INTO ENDERECO (ID_ENDERECO, LOGRADOURO, NUMERO, COMPLEMENTO, CIDADE, ESTADO, CEP) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, endereco.getIdEndereco());
            statement.setString(2, endereco.getLogradouro());
            statement.setInt(3, endereco.getNumero());
            statement.setString(4, endereco.getComplemento());
            statement.setString(5, endereco.getCidade());
            statement.setString(6, endereco.getEstado());
            statement.setString(7, endereco.getCep());

            statement.executeUpdate();
            return endereco;
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

            String sql = "DELETE FROM ENDERECO WHERE ID_ENDERECO = ?";

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
    public boolean editar(Integer id, Endereco endereco) throws SQLException {
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();
            int index = 1;
            int res = 0;
            con = conexaoBancoDeDados.getConnection();

            sql.append("UPDATE ENDERECO SET \n");

            if (endereco.getLogradouro() != null) {
                sql.append(" LOGRADOURO = ?,");
            }

            if (endereco.getNumero() != null) {
                sql.append(" NUMERO = ?,");
            }

            if (endereco.getComplemento() != null) {
                sql.append(" COMPLEMENTO = ?,");
            }

            if (endereco.getCidade() != null) {
                sql.append(" CIDADE = ?,");
            }

            if (endereco.getEstado() != null) {
                sql.append(" ESTADO = ?,");
            }

            if (endereco.getCep() != null) {
                sql.append(" CEP = ?,");
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE ID_ENDERECO = ? ");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            if (endereco.getLogradouro() != null) {
                statement.setString(index++, endereco.getLogradouro());
            }

            if (endereco.getNumero() != null) {
                statement.setInt(index++, endereco.getNumero());
            }

            if (endereco.getComplemento() != null) {
                statement.setString(index++, endereco.getComplemento());
            }

            if (endereco.getCidade() != null) {
                statement.setString(index++, endereco.getCidade());
            }

            if (endereco.getEstado() != null) {
                statement.setString(index++, endereco.getEstado());
            }

            if (endereco.getCep() != null) {
                statement.setString(index++, endereco.getCep());
            }
            statement.setInt(index++, id);

            res = statement.executeUpdate();

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
    public List<Endereco> listar() throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ENDERECO";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                enderecos.add(getEnderecoFromResultSet(res));
            }
            return enderecos;
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

    public List<Endereco> containsEndereco(EnderecoCreateDTO enderecoCreateDTO) throws SQLException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ENDERECO WHERE LOGRADOURO = ? AND NUMERO = ? AND COMPLEMENTO = ? " +
                    "AND CIDADE = ? AND ESTADO = ? AND CEP = ?";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, enderecoCreateDTO.getLogradouro());
            statement.setInt(2, enderecoCreateDTO.getNumero());
            statement.setString(3, enderecoCreateDTO.getComplemento());
            statement.setString(4, enderecoCreateDTO.getCidade());
            statement.setString(5, enderecoCreateDTO.getEstado());
            statement.setString(6, enderecoCreateDTO.getCep());

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                enderecos.add(getEnderecoFromResultSet(res));
            }
            return enderecos;
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

    private Endereco getEnderecoFromResultSet(ResultSet res) throws SQLException {
        Endereco endereco = new Endereco(res.getString("LOGRADOURO"));
        endereco.setIdEndereco(res.getInt("ID_ENDERECO"));
        endereco.setNumero(res.getInt("NUMERO"));
        endereco.setComplemento(res.getString("COMPLEMENTO"));
        endereco.setCidade(res.getString("CIDADE"));
        endereco.setEstado(res.getString("ESTADO"));
        endereco.setCep(res.getString("CEP"));
        return endereco;
    }

    public Boolean conferirIdEndereco(Integer id) throws SQLException{
        Connection con = null;
        Boolean controle = false;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ENDERECO WHERE ID_ENDERECO = ?";

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

    public Endereco pegarEnderecoPorId(Integer id) throws SQLException{
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM ENDERECO WHERE ID_ENDERECO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet res = statement.executeQuery();
            res.next();
            Endereco endereco = getEnderecoFromResultSet(res);
            return endereco;

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
