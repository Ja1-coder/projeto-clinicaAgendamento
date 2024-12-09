/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import Classes.Paciente;
import DB.Conexao;
import javax.swing.JOptionPane;

public class PacienteDAO {
    private Connection connection;

    
    public PacienteDAO(Connection connection) {
        this.connection = connection;
    }

    
    public PacienteDAO() {
        try {
            this.connection = Conexao.getConnection(); 
        } catch (SQLException e) {
            System.out.println("Erro ao obter a conexão: " + e.getMessage());
        }
    }

    // Método SALVAR
    public void salvarPaciente(Paciente paciente) {
        if (connection == null) {
            System.out.println("Erro: Conexão com o banco de dados não foi estabelecida.");
            return;
        }

        String query = "INSERT INTO paciente (cpf, nome, telefone, cep, sexo, dataNascimento, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, paciente.getCpf());
            statement.setString(2, paciente.getNomePaciente());
            statement.setString(3, paciente.getTelefone());
            statement.setString(4, paciente.getCep());
            statement.setString(5, paciente.getSexo());
            statement.setString(6, paciente.getDataNascimento());
            statement.setString(7, paciente.getEmail());
            statement.executeUpdate();
            System.out.println("Paciente salvo com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar o paciente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //Método EXCLUIR
    public void excluirPaciente(String cpf) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    String verificaQuery = "SELECT COUNT(*) FROM paciente WHERE cpf = ?";
    try (PreparedStatement verificaStatement = connection.prepareStatement(verificaQuery)) {
        verificaStatement.setString(1, cpf);
        ResultSet resultSet = verificaStatement.executeQuery();

        if (resultSet.next() && resultSet.getInt(1) > 0) {
            
            String query = "DELETE FROM paciente WHERE cpf = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, cpf);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Paciente com CPF " + cpf + " excluído com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar excluir o paciente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir o paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
          
            JOptionPane.showMessageDialog(null, "Nenhum paciente encontrado com o CPF " + cpf, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao verificar a existência do paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    // Método ALTERAR
public void alterarPaciente(Paciente paciente) {
    if (connection == null) {
        JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "UPDATE paciente SET nome = ?, telefone = ?, cep = ?, sexo = ?, dataNascimento = ?, email = ? WHERE cpf = ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, paciente.getNomePaciente());
        statement.setString(2, paciente.getTelefone());
        statement.setString(3, paciente.getCep());
        statement.setString(4, paciente.getSexo());
        statement.setString(5, paciente.getDataNascimento());
        statement.setString(6, paciente.getEmail());
        statement.setString(7, paciente.getCpf());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Paciente com CPF " + paciente.getCpf() + " alterado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum paciente encontrado com o CPF " + paciente.getCpf(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao alterar o paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

}

