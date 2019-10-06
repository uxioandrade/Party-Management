/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import app.Image;
import app.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alumnogreibd
 */
public class DAOImages extends AbstractDAO{
    
    public DAOImages(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public ArrayList<Image> consultarImagenesGaleria(int idFesta, String galeria){
        System.out.println("festa" + idFesta + "galeria " + galeria);
        
        java.util.ArrayList<Image> resultado = new java.util.ArrayList<Image>();
        Connection con;
        PreparedStatement stmImagenes = null;
        Image img;
        ResultSet rsImagenes;
        
        con = this.getConexion();
        
        String consulta = "select DISTINCT f.url,f.x,f.y " + 
                                "from fotos as f " +
                                "where url IN( " +
                                "SELECT m.url "+
                                "from multimedia as m " +
                                "where festa = ? and galeria = ? )";

        try{
            stmImagenes = con.prepareStatement(consulta);
            stmImagenes.setInt(1,idFesta);
            stmImagenes.setString(2, galeria);
            rsImagenes = stmImagenes.executeQuery();
            
            while (rsImagenes.next()){
                img = new Image(rsImagenes.getString("url"),rsImagenes.getInt("x"),rsImagenes.getInt("y"),idFesta,galeria);
                resultado.add(img);
            }
        }
        catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al constultar las imagenes");
        }finally{
          try {stmImagenes.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
        
    }

    public boolean subirImagen(Image imagen,String pie) {
          
        Connection con;
        PreparedStatement stmMultimedia =null;
        PreparedStatement stmImagen = null;
        boolean ok=false;
        
        con = this.getConexion();

        try{
            con.setAutoCommit(false);
            stmMultimedia = con.prepareStatement("insert into multimedia (url,festa,galeria,pe) values (?,?,?,?)");
            stmMultimedia.setString(1,imagen.getUrl());
            stmMultimedia.setInt(2, imagen.getFesta());
            stmMultimedia.setString(3, imagen.getGaleria());
            stmMultimedia.setString(4,pie);
            stmMultimedia.executeUpdate();
            try{
                stmImagen = con.prepareStatement("insert into fotos values (?,?,?,?,?)");
                stmImagen.setString(1,imagen.getUrl());
                stmImagen.setInt(2, imagen.getFesta());
                stmImagen.setString(3,imagen.getGaleria());
                stmImagen.setInt (4,imagen.getX());
                stmImagen.setInt(5,imagen.getY());
                stmImagen.executeUpdate();
                con.commit();                               
                ok=true;
            }catch(SQLException ex){
                ex.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
                }
                this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }finally{
            try {stmImagen.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
            
        }
        catch (SQLException e){
          e.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                }
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }
          finally{
          try {stmMultimedia.close();con.setAutoCommit(true);;} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return ok;
    }
    
    public void eliminarImagen(Image imagen){
        
        Connection con;
        PreparedStatement stmImagen=null;
        
        con=super.getConexion();
        
        try {
            stmImagen=con.prepareStatement("delete from multimedia "+
                                            "where url=? AND "
                                             + "festa=? AND "
                                             + "galeria=? ");
            stmImagen.setString(1, imagen.getUrl());
            stmImagen.setInt(2, imagen.getFesta());
            stmImagen.setString(3,imagen.getGaleria());
            stmImagen.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al eliminar la imagen");
        }finally{
          try {stmImagen.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
}
