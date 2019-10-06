/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import gui.GuiFacade;
import database.DatabaseFacade;

/**
 *
 * @author alumnogreibd
 */
public class UsersHandler {
    
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public UsersHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public User comprobarAutentificacion(String idUsuario, String clave){
        User u;
        
        u = this.fbd.validarUsuario(idUsuario,clave);
        
        return u;
    }
    
    public Boolean darAltaUsuario(User u){
        return this.fbd.insertarUsuario(u);
    }
    
    public User seleccionarUsuario(String id){
        return this.fbd.seleccionarUsuario(id);
    }
    
    public String recuperarContrasinal(String u){
        return this.fbd.consultarContrasinal(u);
    }
    
    public void cambiarContrasinal(User u,String contrasinal){
        this.fbd.updateContrasinal(u,contrasinal);
    }
    
    public void cambiarContrasinal(String email,String contrasinal){
        this.fbd.updateContrasinal(email,contrasinal);
    }
    
    public void editarPerfil(User u){
        this.fbd.updateUsuario(u);
    }
    
    public float consultarSaldo(String id){
        return this.fbd.consultarSaldo(id);
    }
    
    public void recargarSaldo(float cantidad,String id){
        this.fbd.updateSaldo(cantidad,id);
    }
    
    public float darBajaUsuario(String id){
        return this.fbd.darBajaUsuario(id);
    }
    
    public java.util.List<String> buscarUsuario(String id){
        return this.fbd.buscarUsuario(id);
    }
    
    public void insertarMensaje(app.Message m){
        this.fbd.insertarMensaje(m);
    }
    
    public void borrarMensaje(app.Message m){
        this.fbd.borrarMensaje(m);
    }
    
    public java.util.List consultarMensajes(String emisor, String receptor){
        return this.fbd.consultarMensajes(emisor, receptor);
    }
    
    public void actualizarUrlPerfil(String url,String id){
        this.fbd.actualizarFotoPerfil(url, id);

    }
}
