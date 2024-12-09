/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Classes.Medico;
import DB.Conexao;
import java.sql.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author João Victor
 */
public class MedicoDAO {
    private Connection connection;

    
    public MedicoDAO(Connection connection) {
        this.connection = connection;
    }

    
    public MedicoDAO() {
        try {
            this.connection = Conexao.getConnection(); 
        } catch (SQLException e) {
            System.out.println("Erro ao obter a conexão: " + e.getMessage());
        }
    }
    
    public void salvarMedico(Medico medico) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "INSERT INTO medico (crm, nome, especialidade, telefone, email) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, medico.getCrm());
        statement.setString(2, medico.getNomeMedico());
        statement.setString(3, medico.getEspecialidade());
        statement.setString(4, medico.getTelefone());
        statement.setString(5, medico.getEmail());
        
        statement.executeUpdate();
        JOptionPane.showMessageDialog(null, "Médico(a) salvo com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao salvar o Médico(a): " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    
    public void excluirMedico(String crm) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

  
    String query = "SELECT nome FROM medico WHERE crm = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, crm);
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            String nomeMedico = rs.getString("nome");

            
            int confirmacao = JOptionPane.showConfirmDialog(null, 
                "Tem certeza que deseja excluir o médico: " + nomeMedico + " (CRM: " + crm + ")?", 
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
               
                String deleteQuery = "DELETE FROM medico WHERE crm = ?";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                    deleteStatement.setString(1, crm);
                    int rowsAffected = deleteStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Médico(a) " + nomeMedico + " (CRM: " + crm + ") excluído com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum médico encontrado com o CRM: " + crm, "Atenção", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Médico não encontrado com o CRM: " + crm, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao excluir o médico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    
    public List<Medico> carregarMedicos() {
    List<Medico> medicos = new ArrayList<>();

    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return medicos; 
    }

    String query = "SELECT crm, nome, especialidade FROM medico";

    try (PreparedStatement statement = connection.prepareStatement(query);
         ResultSet rs = statement.executeQuery()) {

        while (rs.next()) {
            
            Medico medico = new Medico();
            medico.setCrm(rs.getString("crm"));
            medico.setNomeMedico(rs.getString("nome"));
            medico.setEspecialidade(rs.getString("especialidade"));

            medicos.add(medico); // Adiciona à lista
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao carregar médicos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }

    return medicos;
}
    
    
    
    
    
    public  boolean carregaMedico(javax.swing.JComboBox combo) {
        String ConsultaSQL;
        ConsultaSQL = "SELECT crm, nome, especialidade FROM medico ORDER BY nome";
        ResultSet rs;
        try {
            PreparedStatement Query1 = connection.prepareStatement(ConsultaSQL);
            rs = Query1.executeQuery();
            combo.removeAllItems();

            while((rs.next())){
                Medico listamedico = new Medico();
                listamedico.setCrm(rs.getString("crm"));
                listamedico.setNomeMedico(rs.getString("nome"));
                listamedico.setEspecialidade(rs.getString("especialidade"));
                combo.addItem(listamedico);
            }
        
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar médicos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }



    
}
