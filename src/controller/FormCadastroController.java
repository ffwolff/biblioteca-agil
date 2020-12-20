/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectionFactory;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Usuario;
import view.FormCadastroView;

/**
 *
 * @author Franco
 */
public class FormCadastroController {
    private FormCadastroView view;

    public FormCadastroController(FormCadastroView view) {
        this.view = view;
    }
    
    public void salvarUsuario() {
        
        String nome = view.getjTextFieldNome().getText();
        String login = view.getjTextFieldUsuario().getText();
        String senha = view.getjPasswordFieldSenha().getText();
        
        Usuario usuario = new Usuario(nome, login, senha);
        
        
        try {
            Connection conexao = ConnectionFactory.getConnection();
            UsuarioDAO usuarioDao = new UsuarioDAO(conexao);
            boolean check = usuarioDao.selectPorLogin(usuario);
            
            if(check){
                JOptionPane.showMessageDialog(null, "Usuário já existe.");
            } else {
                usuarioDao.insert(usuario);
            
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                close();
            }
 
        } catch (SQLException ex) {
            Logger.getLogger(FormCadastroView.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro de conexão: " + ex);
        }

        
        
        
    }
    
    public void close() {
        view.dispose();
    }
    
    
}
