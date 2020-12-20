/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Livro;

/**
 *
 * @author Franco
 */
public class LivroDAO {
    
    private final Connection connection;

    public LivroDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Livro livro) throws SQLException {
        
        String sql = "INSERT INTO livro(titulo_livro, autor_livro, ano, disponivel) VALUES (?, ?, ?, ?);";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, livro.getTitulo());
        statement.setString(2, livro.getAutor());
        statement.setInt(3, livro.getAno());
        statement.setBoolean(4, true);
        statement.execute(); 
        
    }
    
    public void update(Livro livro) throws SQLException {
        String sql = "UPDATE livro SET titulo_livro = ?, autor_livro = ?, ano = ?, disponivel = ?, retirada = ? WHERE id_livro = ?";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, livro.getTitulo());
        statement.setString(2, livro.getAutor());
        statement.setInt(3, livro.getAno());
        statement.setBoolean(4, livro.isDisponivel());
        statement.setInt(5, livro.getRetirada());
        statement.setInt(6, livro.getId());
        statement.execute();
    }
    
    public void insertOrUpdate(Livro livro) throws SQLException {
        if(livro.getId() > 0){
            update(livro);
        } else {
            insert(livro);
        }
    }
    
    public void delete(Livro livro) throws SQLException {
        String sql = "DELETE FROM livro WHERE id_livro = ?";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, livro.getId());
        statement.execute();
    }
    
    public ArrayList<Livro> selectAll() throws SQLException{
        String sql = "SELECT * FROM livro";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        return search(statement);
    }
    

    private ArrayList<Livro> search(PreparedStatement statement) throws SQLException {
        ArrayList<Livro> livros = new ArrayList<>();
        
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        
        while(resultSet.next()) {
            int id = resultSet.getInt("id_livro");
            String titulo = resultSet.getString("titulo_livro");
            String autor = resultSet.getString("autor_livro");
            int ano = resultSet.getInt("ano");
            boolean disponivel = resultSet.getBoolean("disponivel");
            int retirada = resultSet.getInt("retirada");
            
            Livro livroBanco = new Livro(id, titulo, autor, ano, disponivel, retirada);
           
            livros.add(livroBanco);
        }
        
        return livros;
    }
    
    public Livro selectById(Livro livro) throws SQLException {
        String sql = "SELECT * FROM livro WHERE id_livro = ?";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, livro.getId());
        
        return search(statement).get(0);
    }
    
}
