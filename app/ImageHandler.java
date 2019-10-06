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
public class ImageHandler {
    
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public ImageHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public java.util.ArrayList<Image> consultarImagenesGaleria(int festa,String galeria){
        return this.fbd.consultarImagenesGaleria(festa, galeria);
    }

    public boolean subirImagen(Image imagen,String pie) {
        return this.fbd.subirImagen(imagen,pie);
    }

    public void eliminarImagen(Image imagen) {
        this.fbd.eliminarImagen(imagen);
    }
    
}
