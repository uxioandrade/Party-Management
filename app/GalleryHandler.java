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
public class GalleryHandler {
    
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public GalleryHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public void crearGaleria(int idFesta, String galeria){
        this.fbd.insertarGaleria(idFesta, galeria);
    }
    
    public java.util.List<String> consultarGalerias(int festa){
        return this.fbd.consultarGalerias(festa);
    }

    void eliminarGaleria(int festa, String galeria) {
        this.fbd.eliminarGaleria(festa,galeria);
    }
    
}
