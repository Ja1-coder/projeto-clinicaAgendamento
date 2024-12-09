/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Classes.*;
import DB.Conexao;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author João Victor
 */
public class ExameDAO {
    private Connection connection;

    
    public ExameDAO(Connection connection) {
        this.connection = connection;
    }

    
    public ExameDAO() {
        try {
            this.connection = Conexao.getConnection(); 
        } catch (SQLException e) {
            System.out.println("Erro ao obter a conexão: " + e.getMessage());
        }
    }
    
    
    public void salvarExame(Exame exame) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    

    String query = "INSERT INTO exame (descricao, dataExame, horaExame, resultado, idConsulta) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, exame.getDescricao());
        statement.setString(2, exame.getDataExame());
        statement.setString(3, exame.getHoraExame());
        statement.setString(4, exame.getResultado());
        statement.setInt(5, exame.getId_consulta());
        
        statement.executeUpdate();
        JOptionPane.showMessageDialog(null, "Exame salvo com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao salvar o exame: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    
    
    
    
     public List<Consulta> listarConsultasPorPaciente(String cpfPaciente) {
        List<Consulta> consultas = new ArrayList<>();
        
        if (connection == null) {
            System.out.println("Erro: Conexão com o banco de dados não foi estabelecida.");
            return consultas;
        }

        String query = "SELECT c.id_consulta, c.dataConsulta, c.cpf_paciente, c.crm_medico "
                     + "FROM consulta c "
                     + "WHERE c.cpf_paciente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cpfPaciente);
            ResultSet resultSet = statement.executeQuery();

           
            while (resultSet.next()) {
                Consulta consulta = new Consulta();
                consulta.setId_consulta(resultSet.getInt("idConsulta"));
                consulta.setDataConsulta(resultSet.getString("dataConsulta"));
                consulta.setCpf_paciente(resultSet.getString("cpf_paciente"));
                consulta.setCrm_medico(resultSet.getString("crm_medico"));
                
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return consultas;
    }
    
     public boolean existeConsulta(int idConsulta) {
    String query = "SELECT COUNT(*) FROM consulta WHERE idConsulta = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, idConsulta);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;  
        }
    } catch (SQLException e) {
        System.out.println("Erro ao verificar consulta: " + e.getMessage());
    }
    
    return false;  
}
     
     
     
     // Função para listar exames POR PACIENTE
public void buscarExamesPorPaciente(String cpfPaciente) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

   
    String query = "SELECT e.id_exame, e.descricao, e.dataExame, e.horaExame, e.idConsulta "
                 + "FROM exame e "
                 + "JOIN consulta c ON e.idConsulta = c.id_consulta "
                 + "WHERE c.cpf_paciente = ?";
    
    StringBuilder resultado = new StringBuilder("Exames encontrados:\n");

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, cpfPaciente);
        ResultSet resultSet = statement.executeQuery();

       
        boolean encontrou = false;
        while (resultSet.next()) {
            encontrou = true;
            int idExame = resultSet.getInt("id_exame");
            String descricao = resultSet.getString("descricao");
            String dataExame = resultSet.getString("dataExame");
            String horaExame = resultSet.getString("horaExame");
         
            int idConsulta = resultSet.getInt("idConsulta");

            
            resultado.append("ID Exame: ").append(idExame)
                     .append("\nDescrição: ").append(descricao)
                     .append("\nData: ").append(dataExame)
                     .append("\nHora: ").append(horaExame)
                     .append("\nID Consulta: ").append(idConsulta)
                     .append("\n\n");
        }

        if (!encontrou) {
            JOptionPane.showMessageDialog(null, "Nenhum exame encontrado para o CPF informado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, resultado.toString(), "Exames do Paciente", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar exames: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


// Função para listar exames POR DIA
public List<Exame> listarExamesPorDia(String dataExame) {
    List<Exame> exames = new ArrayList<>();
    
    if (connection == null) {
        System.out.println("Erro: Conexão com o banco de dados não foi estabelecida.");
        return exames;
    }

   
    String query = "SELECT e.id_exame, e.descricao, e.dataExame, e.horaExame, e.resultado, e.idConsulta "
                 + "FROM exame e "
                 + "WHERE e.dataExame = ?";  

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, dataExame);  
        ResultSet resultSet = statement.executeQuery();

        
        while (resultSet.next()) {
            Exame exame = new Exame();
            exame.setId_exame(resultSet.getInt("id_exame"));
            exame.setDescricao(resultSet.getString("descricao"));
            exame.setDataExame(resultSet.getString("dataExame"));
            exame.setHoraExame(resultSet.getString("horaExame"));
            exame.setId_consulta(resultSet.getInt("idConsulta"));
            
            exames.add(exame);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao listar exames por dia: " + e.getMessage());
        e.printStackTrace();
    }
    
    return exames;
}


public void cancelarExame(int idExame) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "DELETE FROM exame WHERE id_exame = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        int confirmacao = JOptionPane.showConfirmDialog(null, 
            "Deseja realmente cancelar o exame com ID " + idExame + "?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            statement.setInt(1, idExame);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Exame cancelado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum exame encontrado com o ID informado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao cancelar o exame: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

public void relatorioExamesPorMedico(String crmMedico) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    String query = "SELECT e.id_exame, e.descricao, e.dataExame, e.horaExame, c.id_consulta, c.cpf_paciente "
                 + "FROM exame e "
                 + "JOIN consulta c ON e.idConsulta = c.id_consulta "
                 + "WHERE c.crm_medico = ?";
    
    StringBuilder relatorio = new StringBuilder("Relatório de Exames para o Médico com CRM: " + crmMedico + "\n\n");

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, crmMedico);
        ResultSet resultSet = statement.executeQuery();

        boolean encontrou = false;
        while (resultSet.next()) {
            encontrou = true;
            relatorio.append("Exame ID: ").append(resultSet.getInt("id_exame"))
                     .append("\nDescrição: ").append(resultSet.getString("descricao"))
                     .append("\nData: ").append(resultSet.getString("dataExame"))
                     .append("\nHora: ").append(resultSet.getString("horaExame"))
                     .append("\nConsulta ID: ").append(resultSet.getInt("id_consulta"))
                     .append("\nPaciente CPF: ").append(resultSet.getString("cpf_paciente"))
                     .append("\n\n");
        }

        if (!encontrou) {
            JOptionPane.showMessageDialog(null, "Nenhum exame encontrado para o CRM informado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, relatorio.toString(), "Relatório de Exames", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de exames por médico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


public void relatorioExamesPorDia(String dataExame) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String query = "SELECT e.id_exame, e.descricao, e.dataExame, e.horaExame, c.id_consulta, c.cpf_paciente, c.crm_medico "
                 + "FROM exame e "
                 + "JOIN consulta c ON e.idConsulta = c.id_consulta "
                 + "WHERE e.dataExame = ?";
    
    StringBuilder relatorio = new StringBuilder("Relatório de Exames para o dia: " + dataExame + "\n\n");

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, dataExame);
        ResultSet resultSet = statement.executeQuery();

        boolean encontrou = false;
        while (resultSet.next()) {
            encontrou = true;
            relatorio.append("Exame ID: ").append(resultSet.getInt("id_exame"))
                     .append("\nDescrição: ").append(resultSet.getString("descricao"))
                     .append("\nData: ").append(resultSet.getString("dataExame"))
                     .append("\nHora: ").append(resultSet.getString("horaExame"))
                     .append("\nConsulta ID: ").append(resultSet.getInt("id_consulta"))
                     .append("\nPaciente CPF: ").append(resultSet.getString("cpf_paciente"))
                     .append("\nMédico CRM: ").append(resultSet.getString("crm_medico"))
                     .append("\n\n");
        }

        if (!encontrou) {
            JOptionPane.showMessageDialog(null, "Nenhum exame encontrado para a data informada.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, relatorio.toString(), "Relatório de Exames", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de exames por dia: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}






}
