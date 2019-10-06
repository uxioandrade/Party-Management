/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import gui.GuiFacade;
import database.DatabaseFacade;
import java.time.LocalDate;

/**
 *
 * @author alumnogreibd
 */
public class WallHandler {
    
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public WallHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public void ComentarMuro(WallPost post){
        this.fbd.ComentarPost(post);
    }
    
    public java.util.List<WallPost> consultarPosts(int festa){
        return this.fbd.consultarPosts(festa);
    }
    
    public void borrarPost(String u, int festa, java.sql.Timestamp ts){
        this.fbd.borrarPost(u,festa,ts);
    }
    
    public void silenciarUsuario(String usuario, int festa){
        this.fbd.silenciarUsuario(usuario, festa);
    }
    
    public void desilenciarUsuario(String usuario, int festa){
        this.fbd.desilenciarUsuario(usuario, festa);
    }
    
}
