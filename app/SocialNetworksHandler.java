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
public class SocialNetworksHandler {
 
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public SocialNetworksHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public SocialNetwork consultarRedSocial(String idUsuario, String red){
        return this.fbd.consultarRedSocial(idUsuario,red);
    }
    
    public java.util.ArrayList<SocialNetwork> consultarRedes(String usuario){
        return this.fbd.consultarRedes(usuario);
    }
    
    public void anhadirRedSocial(SocialNetwork red){
        this.fbd.insertarRedSocial(red);
    }
    
    public void eliminarRedSocial(String plataforma, String id){
        this.fbd.EliminarRedSocial(plataforma, id);
    }
}
