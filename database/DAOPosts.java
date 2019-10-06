/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.*;

/**
 *
 * @author alumnogreibd
 */
public class DAOPosts extends AbstractDAO{
    
    public DAOPosts(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public void ComentarMuro(app.WallPost post){
        
        Connection con;
        PreparedStatement stmPost = null;
        
        con = super.getConexion();
        
        try {
            
            stmPost = con.prepareStatement("insert into postear (usuario,festa,data,mensaxe,eVisible) values(?,?,?,?,?)");
            stmPost.setString(1, post.getUser());
            stmPost.setInt(2, post.getFesta());  //<=== INCLUÍR CLASE FESTA NO PROYECTO
            stmPost.setTimestamp(3, post.getData()); //Date en vez de Timestamp?
            stmPost.setString(4, post.getMensaxe());     
            stmPost.setBoolean(5, post.isEsVisible());
            
            stmPost.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("No se ha podido publicar el mensaje");
        }finally{
          try {stmPost.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        
    }
    
    public void borrarPost(String u, int festa, java.sql.Timestamp ts){
        
        Connection con;
        PreparedStatement stmPost = null;
        
        con = super.getConexion();
        
        try {
            
            stmPost = con.prepareStatement("update postear set evisible = false where usuario = ? and festa = ? and data = ?");
            
            stmPost.setString(1, u);
            stmPost.setInt(2, festa);
            stmPost.setTimestamp(3, ts);
                        
            stmPost.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("No se ha podido borrar el mensaje");
        }finally{
          try {stmPost.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        
    }
    
    public java.util.List<app.WallPost> consultarPosts(int festa){
        
        Connection con;
        PreparedStatement stmPost = null;
        
        con = super.getConexion();
        
        ResultSet rsPost;
        java.util.ArrayList<app.WallPost> resultado = new java.util.ArrayList<app.WallPost>();
        app.WallPost postActual;
        
        try {
            
            stmPost = con.prepareStatement("select * "
                                            + "from postear "
                                            + "where festa = ? "
                                            + "and eVisible = true "
                                            + "order by data ASC");
            
            stmPost.setInt(1, festa);
                        
            rsPost = stmPost.executeQuery();
            
            while (rsPost.next()){
                postActual = new app.WallPost(rsPost.getString("usuario"), rsPost.getString("mensaxe"), rsPost.getTimestamp("data"), rsPost.getInt("festa"));
                resultado.add(postActual);
            }

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmPost.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        
        return resultado;
    }
    
    public void silenciarUsuario(String usuario, int festa){
        
        Connection con;        
        PreparedStatement stmUsuario = null;
        
        con = super.getConexion();
        
        try {
            
            stmUsuario = con.prepareStatement("insert into silenciar values (?,?)");
            
            stmUsuario.setString(1, usuario);
            stmUsuario.setInt(2, festa);
                        
            stmUsuario.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
              
    }
    
    public void desilenciarUsuario(String usuario, int festa){
        
        Connection con;        
        PreparedStatement stmUsuario = null;
        
        con = super.getConexion();
        
        try {
            
            stmUsuario = con.prepareStatement("delete from silenciar "
                                            + "where usuario = ? "
                                            + "and festa = ?");
            
            stmUsuario.setString(1, usuario);
            stmUsuario.setInt(2, festa);
                        
            stmUsuario.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
              
    }
    
}
