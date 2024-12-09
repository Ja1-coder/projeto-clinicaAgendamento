/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author João Victor
 */
public class Consulta {
    private int id_consulta;
    private String dataConsulta;
    private String horaConsulta;
    private String observacao;
    private String cpf_paciente;
    private String crm_medico;

    public Consulta(String dataConsulta, String horaConsulta, String observacao, String cpf_paciente, String crm_medico) {
        
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.observacao = observacao;
        this.cpf_paciente = cpf_paciente;
        this.crm_medico = crm_medico;
    }

    public Consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }
    
    

    public Consulta() {
    }
    
    

    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(String horaConsulta) {
        this.horaConsulta = horaConsulta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCpf_paciente() {
        return cpf_paciente;
    }

    public void setCpf_paciente(String cpf_paciente) {
        this.cpf_paciente = cpf_paciente;
    }

    public String getCrm_medico() {
        return crm_medico;
    }

    public void setCrm_medico(String crm_medico) {
        this.crm_medico = crm_medico;
    }
    
    
    
    
}
