/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author alumnogreibd
 */
public class DAOAccess extends AbstractDAO{
    
    public DAOAccess(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public void insertarAcceso(String id){
        
        Connection con;
        PreparedStatement stmAcceso=null;
        String hostname = "Unknown";
        con=super.getConexion();
        
        try{
            InetAddress addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }catch(UnknownHostException ex){
            System.out.println("El nombre del host no ha podido ser obtenido");
        }
        
        try {
            stmAcceso=con.prepareStatement("insert into accesos(usuario,fechainicio, dispositivo) "+
                                          "values (?,now(),?)");
            stmAcceso.setString(1,id);
            stmAcceso.setString(2,hostname);
            stmAcceso.executeUpdate();
        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al insertar el acceso del usuario");
        }finally{
          try {stmAcceso.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
    public void acabarAcceso(String id){
        Connection con;
        PreparedStatement stmAcceso =null;
        con= this.getConexion();
            
            try{
                stmAcceso=con.prepareStatement("Update accesos "
                                              + "Set fechafin = now() "
                                              + "Where usuario = ? and fechafin IS NULL");
                stmAcceso.setString(1,id);

                stmAcceso.executeUpdate();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }finally{
              try {stmAcceso.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
    }
}
