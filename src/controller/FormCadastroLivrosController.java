/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectionFactory;
import dao.LivroDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Livro;
import view.FormCadastroLivrosView;

/**
 *
 * @author Franco
 */
public class FormCadastroLivrosController {
    private final FormCadastroLivrosView view;

    public FormCadastroLivrosController(FormCadastroLivrosView view) {
        this.view = view;
    }
    
    public void salvarLivro() throws SQLException {
        
        String titulo = view.getjTextFieldTitulo().getText();
        String autor = view.getjTextFieldAutor().getText();
        int ano = (int) view.getjSpinnerAno().getValue();
        
        Livro livro = new Livro(titulo, autor, ano);
        
        
        
            Connection conexao = ConnectionFactory.getConnection();
            LivroDAO livroDao = new LivroDAO(conexao);
            livroDao.insert(livro);
            
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
            close();
    }
    
    public void close() {
        view.dispose();
    }
}
