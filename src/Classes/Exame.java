/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;


/**
 *
 * @author João Victor
 */
public class Exame {
    private int id_exame;
    private String descricao;
    private String dataExame;
    private String horaExame;
    private String resultado;
    private int id_consulta;

    public Exame( String descricao, String dataExame, String horaExame, int id_consulta) {
        this.descricao = descricao;
        this.dataExame = dataExame;
        this.horaExame = horaExame;
        //this.resultado = resultado;
        this.id_consulta = id_consulta;
    }

    public Exame(int id_exame) {
        this.id_exame = id_exame;
    }
    
    

    public Exame() {
    }
    
    

    public int getId_exame() {
        return id_exame;
    }

    public void setId_exame(int id_exame) {
        this.id_exame = id_exame;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataExame() {
        return dataExame;
    }

    public void setDataExame(String dataExame) {
        this.dataExame = dataExame;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getHoraExame() {
        return horaExame;
    }

    public void setHoraExame(String horaExame) {
        this.horaExame = horaExame;
    }

    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }
    
    
 
   
    
    
    
    
}
