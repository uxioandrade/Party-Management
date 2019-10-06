/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

/**
 *
 * @author basesdatos
 */
public abstract class AbstractDAO {
   private app.FachadaAplicacion fa;
   private java.sql.Connection conexion;

   
    protected java.sql.Connection getConexion(){
        return this.conexion;
    }
    
    protected void setConexion(java.sql.Connection conexion){
        this.conexion=conexion;
    }
   
   protected void setFachadaAplicacion(app.FachadaAplicacion fa){
       this.fa=fa;
   }
   
   protected app.FachadaAplicacion getFachadaAplicacion(){
       return fa;
   }
   
   
}
