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
import javax.swing.JOptionPane;
import model.Usuario;
import view.IndexView;
import view.LoginView;

/**
 *
 * @author Franco
 */
public class LoginController {
    private LoginView view;
    private IndexView indexView;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void autenticar() throws SQLException {
        
        //Buscar usuário na view
        String usuario = view.getjTextFieldUsuario().getText();
        String senha = view.getjPasswordFieldSenha().getText();
        
        Usuario usuarioAutenticar = new Usuario(usuario, senha);
        
        //Verificar se usuário existe no banco de dados
        Connection conexao = ConnectionFactory.getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);
        
        boolean verifica = usuarioDao.autenticarPorLoginESenha(usuarioAutenticar);
        
        //Se existir, direciona para o Index com o usuario em uma variável
        if(verifica) {
            if(indexView == null){
                indexView = new IndexView();
                indexView.setVisible(true);
                Usuario usuarioAutenticado = usuarioDao.selectPorLoginESenha(usuarioAutenticar);
                indexView.usuarioLogado(usuarioAutenticado);
                indexView.usuario = usuarioAutenticado;
                view.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Falha ao autenticar!\nUsuário ou senha não existe.");
        }
    }
    
    public void close() {
        view.dispose();
    }
    
    
}
