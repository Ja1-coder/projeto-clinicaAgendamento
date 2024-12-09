/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author João Victor
 */
public class Medico {
    private String crm;
    private String nomeMedico;
    private String telefone;
    private String email;
    private String especialidade;

    public Medico(String crm, String nomeMedico, String especialidade, String telefone, String email) {
        this.crm = crm;
        this.nomeMedico = nomeMedico;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
        
    }

    public Medico(String crm, String nomeMedico, String especialidade) {
        this.crm = crm;
        this.nomeMedico = nomeMedico;
        this.especialidade = especialidade;
    }
    

    public Medico() {
    }
    
    
    

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    
    public String toString(){
    return "CRM: "+crm + "  Nome do médico: " + nomeMedico+ "  Especialidade:"+ especialidade;
            }
    
    
    
}
