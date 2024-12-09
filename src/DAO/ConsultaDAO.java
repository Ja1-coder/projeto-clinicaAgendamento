/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Classes.*;
import DB.Conexao;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author João Victor
 */
public class ConsultaDAO {
    private Connection connection;

   
    public ConsultaDAO(Connection connection) {
        this.connection = connection;
    }

    
    public ConsultaDAO() {
        try {
            this.connection = Conexao.getConnection(); 
        } catch (SQLException e) {
            System.out.println("Erro ao obter a conexão: " + e.getMessage());
        }
    }
    
    
    public void salvarConsulta(Consulta consulta) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (!existePaciente(consulta.getCpf_paciente())) {
        JOptionPane.showMessageDialog(null, "Paciente não encontrado no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (!existeMedico(consulta.getCrm_medico())) {
        JOptionPane.showMessageDialog(null, "Médico não encontrado no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "INSERT INTO consulta (dataConsulta, horaConsulta, observacao, cpf_paciente, crm_medico) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, consulta.getDataConsulta());
        statement.setString(2, consulta.getHoraConsulta());
        statement.setString(3, consulta.getObservacao());
        statement.setString(4, consulta.getCpf_paciente());
        statement.setString(5, consulta.getCrm_medico());
        
        statement.executeUpdate();
        JOptionPane.showMessageDialog(null, "Consulta agendada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao agendar a consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    // Relatórios POR DIA
    public void relatorioConsultasPorDia(String dataConsulta) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "SELECT * FROM consulta WHERE dataConsulta = ?";
    StringBuilder relatorio = new StringBuilder("Relatório de Consultas para o dia: " + dataConsulta + "\n\n");

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, dataConsulta);
        ResultSet resultSet = statement.executeQuery();

        boolean encontrou = false;
        while (resultSet.next()) {
            encontrou = true;
            relatorio.append("Consulta ID: ").append(resultSet.getInt("id_consulta"))
                     .append("\nPaciente CPF: ").append(resultSet.getString("cpf_paciente"))
                     .append("\nMédico CRM: ").append(resultSet.getString("crm_medico"))
                     .append("\nData: ").append(resultSet.getString("dataConsulta"))
                     .append("\nHora: ").append(resultSet.getString("horaConsulta"))
                     .append("\nObservação: ").append(resultSet.getString("observacao"))
                     .append("\n\n");
        }

        if (!encontrou) {
            JOptionPane.showMessageDialog(null, "Nenhuma consulta encontrada para a data informada.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, relatorio.toString(), "Relatório de Consultas", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de consultas por dia: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    
    
  // Relatório POR MÉDICO
  public void relatorioConsultasPorMedico(String crmMedico) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "SELECT * FROM consulta WHERE crm_medico = ?";
    StringBuilder relatorio = new StringBuilder("Relatório de Consultas para o Médico com CRM: " + crmMedico + "\n\n");

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, crmMedico);
        ResultSet resultSet = statement.executeQuery();

        boolean encontrou = false;
        while (resultSet.next()) {
            encontrou = true;
            relatorio.append("Consulta ID: ").append(resultSet.getInt("id_consulta"))
                     .append("\nPaciente CPF: ").append(resultSet.getString("cpf_paciente"))
                     .append("\nData: ").append(resultSet.getString("dataConsulta"))
                     .append("\nHora: ").append(resultSet.getString("horaConsulta"))
                     .append("\n\n");
        }

        if (!encontrou) {
            JOptionPane.showMessageDialog(null, "Nenhuma consulta encontrada para o CRM informado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, relatorio.toString(), "Relatório de Consultas", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de consultas por médico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}


  
  
  
  public boolean existePaciente(String cpf) {
    String query = "SELECT COUNT(*) FROM paciente WHERE cpf = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, cpf);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;  
        }
    } catch (SQLException e) {
        System.out.println("Erro ao verificar paciente: " + e.getMessage());
    }
    
    return false;  
}
  
  
  public boolean existeMedico(String crm) {
    String query = "SELECT COUNT(*) FROM medico WHERE crm = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, crm);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;  
        }
    } catch (SQLException e) {
        System.out.println("Erro ao verificar médico: " + e.getMessage());
    }
    
    return false;  
}
  
  
  public void cancelarConsulta(int idConsulta) {
    if (connection == null) {
        System.out.println("Erro: Conexão com o banco de dados não foi estabelecida.");
        return;
    }

    
    String query = "DELETE FROM consulta WHERE id_consulta = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, idConsulta);
        
       
        int rowsAffected = statement.executeUpdate();

 
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Consulta com ID " + idConsulta + " foi cancelada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Nenhuma consulta encontrada com o ID " + idConsulta);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao cancelar consulta: " + e.getMessage());
        e.printStackTrace();
    }
}
  
  
public void buscarConsultasPorPaciente(String cpfPaciente) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }


    String query = "SELECT id_consulta, dataConsulta, horaConsulta, observacao, crm_medico FROM consulta WHERE cpf_paciente = ?";
    
    StringBuilder resultado = new StringBuilder("Consultas encontradas:\n");

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, cpfPaciente);
        ResultSet resultSet = statement.executeQuery();

    
        boolean encontrou = false;
        while (resultSet.next()) {
            encontrou = true;
            int idConsulta = resultSet.getInt("id_consulta");
            String dataConsulta = resultSet.getString("dataConsulta");
            String horaConsulta = resultSet.getString("horaConsulta");
            String observacao = resultSet.getString("observacao");
            String crmMedico = resultSet.getString("crm_medico");

          
            resultado.append("ID: ").append(idConsulta)
                     .append("\nData: ").append(dataConsulta)
                     .append("\nHora: ").append(horaConsulta)
                     .append("\nObservação: ").append(observacao)
                     .append("\n CRM do Médico: ").append(crmMedico)
                     .append("\n\n");
        }

        if (!encontrou) {
            JOptionPane.showMessageDialog(null, "Nenhuma consulta encontrada para o CPF informado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            
            JOptionPane.showMessageDialog(null, resultado.toString(), "Consultas do Paciente", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar consultas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}







    
    
    
}
