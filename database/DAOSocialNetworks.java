/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import app.SocialNetwork;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alumnogreibd
 */
public class DAOSocialNetworks extends AbstractDAO{
    
    public DAOSocialNetworks(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public SocialNetwork consultarRedSocial(String id,String red){
        Connection con;
        PreparedStatement stmRed=null;
        ResultSet rsRed = null;
        con=super.getConexion();
        SocialNetwork resultado = null;
        try{
            stmRed = con.prepareStatement("select * from redes where usuario = ? and plataforma = ?");
            stmRed.setString(1, id);
            stmRed.setString(2, red);
            rsRed = stmRed.executeQuery();
            if(rsRed.next())
                resultado = new SocialNetwork(rsRed.getString("plataforma"),rsRed.getString("id_plataforma"),rsRed.getString("url"),rsRed.getString("usuario"));
        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al intentar obtener la red social");
        }finally{
            try{stmRed.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public java.util.ArrayList<SocialNetwork> consultarRedes(String usuario){
        java.util.ArrayList<SocialNetwork> resultado = new java.util.ArrayList<SocialNetwork>();
        SocialNetwork redActual;
        Connection con;
        PreparedStatement stmRed = null;
        ResultSet rsRed;
        
        con = this.getConexion();
        
        String consulta = "select * from redes where usuario = ?";

        try{
            stmRed = con.prepareStatement(consulta);
            stmRed.setString(1, usuario);
            rsRed = stmRed.executeQuery();
            
            while (rsRed.next()){
                redActual = new SocialNetwork(rsRed.getString("plataforma"),rsRed.getString("id_plataforma"),rsRed.getString("url"),rsRed.getString("usuario"));
                resultado.add(redActual);
            }
        }
        catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al constultar las redes sociales");
        }finally{
          try {stmRed.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public void insertarRedSocial(SocialNetwork red){
        Connection con;
        PreparedStatement stmRed=null;
        PreparedStatement stmIdUsuario=null;
        ResultSet rsRed;

        String idUsuario=null;

        con=super.getConexion();
        
        try {
            stmRed=con.prepareStatement("insert into redes(plataforma, id_plataforma, url, usuario) "+
                                          "values (?,?,?,?)");
            stmRed.setString(1, red.getPlataforma());
            stmRed.setString(2, red.getId_plataforma());
            stmRed.setString(3, red.getUrl());
            stmRed.setString(4, red.getUsuario() );
            stmRed.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al insertar la red social");
        }finally{
          try {stmRed.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
    public void EliminarRedSocial(String plataforma, String id){
        
        Connection con;
        PreparedStatement stmRed = null;
        
        con = super.getConexion();
        
        try {
            stmRed=con.prepareStatement("delete from redes where plataforma = ? and id_plataforma = ?");
            stmRed.setString(1, plataforma);
            stmRed.setString(2, id);
            stmRed.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al borrar la red social");
        }finally{
          try {stmRed.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
}
