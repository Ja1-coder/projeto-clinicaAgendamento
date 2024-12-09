/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author João Victor
 */
public class Paciente {
    private String cpf;
    private String nomePaciente;
    private String telefone;
    private String dataNascimento;
    private String email;
    private String sexo;
    private String cep;

    public Paciente(String cpf, String nomePaciente, String telefone, String dataNascimento, String email, String sexo, String cep) {
        this.cpf = cpf;
        this.nomePaciente = nomePaciente;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.sexo = sexo;
        this.cep = cep;
    }

    public Paciente() {
    }
    
    

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
    
    
    
}
