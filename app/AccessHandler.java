/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import database.DatabaseFacade;
import gui.GuiFacade;

/**
 *
 * @author alumnogreibd
 */
public class AccessHandler {
    
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public AccessHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    
    public void nuevoAcceso(String id){
        this.fbd.insertarAcceso(id);
    }
    
    public void acabarAcceso(String id){
        this.fbd.acabarAcceso(id);
    }
    
}
