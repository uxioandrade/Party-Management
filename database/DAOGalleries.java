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


/**
 *
 * @author alumnogreibd
 */
public class DAOGalleries extends AbstractDAO{
    
    private app.FachadaAplicacion fa;
    
    public DAOGalleries(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
        this.fa=fa;
    }
    
    public java.util.List<String> consultarGalerias(int idFesta){
        Connection con;
        PreparedStatement stmGaleria=null;
        ResultSet rsGaleria;
        java.util.ArrayList<String> resultado=new java.util.ArrayList();
        
        con=super.getConexion();
        
        try{
            stmGaleria=con.prepareStatement("Select nome "
                                            +"from galerias "
                                            +"where festa=? "
                                            +"order by nome asc");
            stmGaleria.setInt(1, idFesta);
            rsGaleria=stmGaleria.executeQuery();
            
            while (rsGaleria.next()){
                    resultado.add(rsGaleria.getString("nome"));
                }
        }
        catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al constultar las imagenes");
        }finally{
          try {stmGaleria.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public void insertarGaleria(int idFesta,String nome){
        Connection con;
        PreparedStatement stmGaleria=null;

        con=super.getConexion();
        
        try {
            stmGaleria=con.prepareStatement("insert into galerias(festa, nome) "+
                                          "values (?,?)");
            stmGaleria.setInt(1, idFesta);
            stmGaleria.setString(2, nome);
            stmGaleria.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al insertar la galeria");
        }finally{
          try {stmGaleria.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
    public void eliminarGaleria(int idFesta, String nome){
        Connection con;
        PreparedStatement stmGaleria=null;
        PreparedStatement stmAudios=null;
        PreparedStatement stmImagenes=null;
        
        con=super.getConexion();
        DAOAudio daoAudio=new DAOAudio(con,this.fa);
        DAOImages daoImagen=new DAOImages(con,this.fa);
        
        try {
            stmGaleria=con.prepareStatement("delete from galerias "+
                                            "where festa=? AND nome=?");
            stmGaleria.setInt(1, idFesta);
            stmGaleria.setString(2, nome);
            stmGaleria.executeUpdate();

        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al insertar la galeria");
        }finally{
          try {stmGaleria.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
}
