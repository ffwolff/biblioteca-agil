/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ConnectionFactory;
import dao.LivroDAO;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Livro;
import model.Usuario;
import view.IndexView;
import view.LoginView;

/**
 *
 * @author Franco
 */
public class IndexController {
    private final IndexView view;
    Connection conexao = ConnectionFactory.getConnection();
    
    public IndexController(IndexView view) {
        this.view = view;
    }
    
    @SuppressWarnings("empty-statement")
    public void listar() throws SQLException{
        DefaultTableModel modelo = (DefaultTableModel) view.getjTLivros().getModel();
        modelo.setNumRows(0);
        LivroDAO livroDao = new LivroDAO(conexao);
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);
        Usuario usuarioId = new Usuario();
        
        for(Livro livro: livroDao.selectAll()) {
            String disponivel;
            String nomeRetirada;
            
            if(livro.getRetirada()>0){
                usuarioId = usuarioDao.selectById(livro.getRetirada());
                nomeRetirada = usuarioId.getNome();;
            } else {
                nomeRetirada = "";
            }
            
            
            if(livro.isDisponivel()){
                disponivel = "Disponível";
                
            } else {
                disponivel = "Retirado";
            } 
            modelo.addRow(new Object[]{
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                disponivel,
                nomeRetirada
            });
            
        }
    }
    
    public void retirar() throws SQLException {
        int row = view.getjTLivros().getSelectedRow();
        int id = (int) view.getjTLivros().getModel().getValueAt(row, 0);
        String titulo = view.getjTLivros().getModel().getValueAt(row, 1).toString();
        String autor = view.getjTLivros().getModel().getValueAt(row, 2).toString();
        int ano = (int) view.getjTLivros().getModel().getValueAt(row, 3);
        LivroDAO livroDao = new LivroDAO(conexao);
        Livro livro = new Livro(id, titulo, autor, ano);
        Livro controle = livroDao.selectById(livro);
        if(controle.isDisponivel()){
            controle.setDisponivel(false);
            controle.setRetirada(view.usuario.getId());
            livroDao.update(controle);
        } else {
            JOptionPane.showMessageDialog(view, "Falha ao retirar!\nLivro indisponível.");
        }
        

    }
    
    public void devolver() throws SQLException {
        int row = view.getjTLivros().getSelectedRow();
        int id = (int) view.getjTLivros().getModel().getValueAt(row, 0);
        String titulo = view.getjTLivros().getModel().getValueAt(row, 1).toString();
        String autor = view.getjTLivros().getModel().getValueAt(row, 2).toString();
        int ano = (int) view.getjTLivros().getModel().getValueAt(row, 3);
        
        LivroDAO livroDao = new LivroDAO(conexao);
        Livro livro = new Livro(id, titulo, autor, ano);
        Livro controle = livroDao.selectById(livro);
        
        if(controle.isDisponivel()){
            JOptionPane.showMessageDialog(view, "Livro disponível.");
        } else if(!controle.isDisponivel()){
            if(controle.getRetirada() == view.IDRetirada(view.usuario) ){
                //controle.setRetirada(null);
                controle.setDisponivel(true);
                controle.setRetirada(0);
                livroDao.update(controle);
            } else {
                JOptionPane.showMessageDialog(view, "Você não pode devolver livros retirados por outra pessoa.");
            }
        }
    }
    
    public void exit() {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        view.dispose();
    }
    
    public void close() {
        view.dispose();
    }

    
}
