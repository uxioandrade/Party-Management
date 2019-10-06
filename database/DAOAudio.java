/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import app.Audio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alumnogreibd
 */
public class DAOAudio extends AbstractDAO {
    
    public DAOAudio(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public ArrayList<Audio> consultarAudiosGaleria(int idFesta, String galeria){
        System.out.println("festa" + idFesta + "galeria " + galeria);
        
        java.util.ArrayList<Audio> resultado = new java.util.ArrayList<Audio>();
        Connection con;
        PreparedStatement stmIAudios = null;
        Audio aud;
        ResultSet rsAudios;
        
        con = this.getConexion();
        
        String consulta = "select DISTINCT au.url,au.duracion " + 
                                "from audios as au " +
                                "where url IN( " +
                                "SELECT m.url "+
                                "from multimedia as m " +
                                "where festa = ? and galeria = ? )";

        try{
            stmIAudios = con.prepareStatement(consulta);
            stmIAudios.setInt(1,idFesta);
            stmIAudios.setString(2, galeria);
            rsAudios = stmIAudios.executeQuery();
            
            while (rsAudios.next()){
                aud = new Audio(rsAudios.getString("url"),rsAudios.getTime("duracion"),idFesta,galeria);
                resultado.add(aud);
            }
        }
        catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al constultar las imagenes");
        }finally{
          try {stmIAudios.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
        
    }

    public boolean subirAudio(Audio audio,String pie) {
          
        Connection con;
        PreparedStatement stmMultimedia =null;
        PreparedStatement stmAudio = null;
        boolean ok=false;
        
        con = this.getConexion();

        try{
            con.setAutoCommit(false);
            stmMultimedia = con.prepareStatement("insert into multimedia (url,festa,galeria,pe) values (?,?,?,?)");
            stmMultimedia.setString(1,audio.getUrl());
            stmMultimedia.setInt(2, audio.getParty());
            stmMultimedia.setString(3, audio.getGallery());
            stmMultimedia.setString(4,pie);
            stmMultimedia.executeUpdate();
            try{
                stmAudio = con.prepareStatement("insert into audios values (?,?,?,?)");
                stmAudio.setString(1,audio.getUrl());
                stmAudio.setInt(2,audio.getParty());
                stmAudio.setString(3, audio.getGallery());
                stmAudio.setTime(4,audio.getDuration());
                stmAudio.executeUpdate();
                con.commit();
                ok=true;
            }catch(SQLException ex){
                ex.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e2.getMessage());
                }
                this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }finally{
            try {stmAudio.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
            
        }
        catch (SQLException e){
          e.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e2.getMessage());
                }
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }
          finally{
          try {stmMultimedia.close();con.setAutoCommit(true);} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return ok;
    }
    
   public void eliminarAudio(Audio audio){
       
        Connection con;
        PreparedStatement stmAudio=null;
        
        con=super.getConexion();
        
        try {
            stmAudio=con.prepareStatement("delete from multimedia "+
                                            "where url=? AND "
                                             + "festa=? AND "
                                             + "galeria=? ");
            stmAudio.setString(1,audio.getUrl());
            stmAudio.setInt(2, audio.getParty());
            stmAudio.setString(3,audio.getGallery());
            stmAudio.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al eliminar el audio");
        }finally{
          try {stmAudio.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
    
}

