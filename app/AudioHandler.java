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
public class AudioHandler {
    
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public AudioHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    
    public java.util.ArrayList<Audio> consultarAudiosGaleria(int festa,String galeria){
        return this.fbd.consultarAudiosGaleria(festa, galeria);
    }

    public boolean subirAudio(Audio audio,String pie) {
        return this.fbd.subirAudio(audio,pie);
    }

    public void eliminarAudio(Audio audio) {
        this.fbd.eliminarAudio(audio);
    }
}
