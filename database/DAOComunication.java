/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*DAO para as transacccion relacionadas con mensaxes, muro dunha festa, silenciar a un usuario..*/
package database;

import app.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author alumnogreibd
 */
public class DAOComunication extends AbstractDAO {
    
    public DAOComunication(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public java.util.List<app.Message> consultarMensajes(String emisor, String receptor) {

        Connection con;

        PreparedStatement stmMensaje = null;

        ResultSet rsMensaje = null;

        java.util.List<app.Message> mensajes = new java.util.ArrayList<>();

        con = super.getConexion();

        try {

            stmMensaje = con.prepareStatement("select * from mensaxear "
                    + "where ((emisor = ? "
                    + "and receptor = ?) "
                    + "or (emisor = ? "
                    + "and receptor = ?)) "
                    + "and eBorrado = false "
                    + "order by fecha ASC");
            stmMensaje.setString(1, emisor);
            stmMensaje.setString(2, receptor);
            stmMensaje.setString(3, receptor);
            stmMensaje.setString(4, emisor);

            rsMensaje = stmMensaje.executeQuery();

            while (rsMensaje.next()) {
                Message m = new Message(rsMensaje.getString("emisor"), rsMensaje.getString("receptor"), rsMensaje.getTimestamp("fecha"), rsMensaje.getString("contido"), rsMensaje.getBoolean("eBorrado"));
                mensajes.add(m);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        } finally {
            try {
                stmMensaje.close();
            } catch (SQLException ex) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return mensajes;

    }
    
    public void insertarMensaje(Message m){
        
        Connection con;
        
        PreparedStatement stmMensaje = null;
        
        con = super.getConexion();
        
        try {

            stmMensaje = con.prepareStatement("insert into mensaxear values (?,?,?,?,?)");
            stmMensaje.setString(1, m.getEmisor());
            stmMensaje.setString(2, m.getReceptor());
            stmMensaje.setTimestamp(3, m.getFecha());
            stmMensaje.setString(4, m.getContenido());
            stmMensaje.setBoolean(5, false);
            
            stmMensaje.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        } finally {
            try {
                stmMensaje.close();
            } catch (SQLException ex) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
    
    public void borrarMensaje(Message m){
        
        Connection con;
        
        PreparedStatement stmMensaje = null;
        
        con = super.getConexion();
        
        try {

            stmMensaje = con.prepareStatement("update mensaxear "
                                            + "set eBorrado = true "
                                            + "where emisor = ? "
                                            + "and receptor = ? "
                                            + "and fecha = ?");
            
            stmMensaje.setString(1, m.getEmisor());
            stmMensaje.setString(2, m.getReceptor());
            stmMensaje.setTimestamp(3, m.getFecha());
            
            stmMensaje.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        } finally {
            try {
                stmMensaje.close();
            } catch (SQLException ex) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        
    }
    
    
}
