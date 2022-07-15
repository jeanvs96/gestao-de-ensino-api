package br.com.dbccompany.time7.gestaodeensino.entity;

public class Endereco {
    private String logradouro, cidade, estado, cep, complemento;
    private Integer numero, idEndereco;

    public Endereco(String logradouro, String cidade, String estado, String cep, Integer numero) {
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.numero = numero;
    }

    public Endereco(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        if (this.complemento != null) {
            return "Logradouro: " + logradouro + ", Número: " +  numero + ", Complemento: " + complemento + ", Cidade: " + cidade + ", Estado: " + estado  + ", CEP: " + cep;
        }else {
            return "Logradouro: " + logradouro + ", Número: " +  numero + ", Cidade: " + cidade + ", Estado: " + estado  + ", CEP: " + cep;
        }

    }
}
