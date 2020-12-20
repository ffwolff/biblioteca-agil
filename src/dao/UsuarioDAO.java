/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Usuario;

/**
 *
 * @author Franco
 */
public class UsuarioDAO {
    
    private final Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario(nome_usuario, login, senha) VALUES (?, ?, ?);";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getLogin());
        statement.setString(3, usuario.getSenha());
        statement.execute(); 
        
    }
    
    public void update(Usuario usuario) throws SQLException {
        String sql = "UPDATE INTO usuario SET nome_usuario = ?, login = ?, senha = ? WHERE id_usuario = ?";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getLogin());
        statement.setString(3, usuario.getSenha());
        statement.setInt(4, usuario.getId());
        statement.execute();
    }
    
    public void insertOrUpdate(Usuario usuario) throws SQLException {
        if(usuario.getId() > 0){
            update(usuario);
        } else {
            insert(usuario);
        }
    }
    
    public void delete(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, usuario.getId());
        statement.execute();
    }
    
    public ArrayList<Usuario> selectAll() throws SQLException{
        String sql = "SELECT * FROM usuario";        
        PreparedStatement statement = connection.prepareStatement(sql);
        
        return search(statement);
    }

    private ArrayList<Usuario> search(PreparedStatement statement) throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        
        while(resultSet.next()) {
            int id = resultSet.getInt("id_usuario");
            String nome = resultSet.getString("nome_usuario");
            String login = resultSet.getString("login");
            String senha = resultSet.getString("senha");
            
            Usuario usuarioBanco = new Usuario(id, nome, login, senha);
            usuarios.add(usuarioBanco);
        }
        
        return usuarios;
    }
    
    public Usuario selectById(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ? ";
        Usuario consulta = null;
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        
        if(resultSet.next()){
            consulta = new Usuario();
            consulta.setId(resultSet.getInt("id_usuario"));
            consulta.setNome(resultSet.getString("nome_usuario"));
            consulta.setLogin(resultSet.getString("login"));
            consulta.setSenha(resultSet.getString("senha"));
        }
        
        
        return consulta;
    }
    
    public Usuario selectPorLoginESenha(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, usuario.getLogin());
        statement.setString(2, usuario.getSenha());
        statement.execute();   
        
        return search(statement).get(0);
    }

    public boolean autenticarPorLoginESenha(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, usuario.getLogin());
        statement.setString(2, usuario.getSenha());
        statement.execute();   
        
        ResultSet resultSet = statement.getResultSet();
        
        return resultSet.next();
    }
    
    public boolean selectPorLogin(Usuario usuario) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE login = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setString(1, usuario.getLogin());
        ResultSet resultSet = statement.executeQuery();;
        boolean check = false;
        
        if(resultSet.next()){
                check = true;
            }
        return check;
    }
    
}
