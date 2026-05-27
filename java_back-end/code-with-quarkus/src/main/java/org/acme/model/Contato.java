package org.acme.model;

public class Contato {
    private int id;
    private String nome;
    private String email;
    private String sexo;
    private String nascimento;
    private String cpf;
    private String telefone;
    private String mensagem;

    public Contato(int id,String nome, String email, String sexo, String nascimento, String cpf, String telefone, String mensagem) {
        this.id=id;
        this.nome = nome;
        this.email = email;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.mensagem = mensagem;
    }

    public Contato() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
