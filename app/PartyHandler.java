/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import database.DatabaseFacade;
import gui.GuiFacade;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class PartyHandler {
    
    GuiFacade fgui;
    DatabaseFacade fbd;
    
    public PartyHandler(GuiFacade fgui, DatabaseFacade fbd){
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public String consultarOrganizador(int idFesta){
        return this.fbd.consultarOrganizador(idFesta);
    }

    List<Party> buscarFestas(String usuarioSesion, String nomeFesta, String nomeOrganizador, String tematica, String privacidade, Date data) {
        return this.fbd.buscarFestas(usuarioSesion,nomeFesta,nomeOrganizador,tematica,privacidade,data);
    }

    List<Party> buscarFPorganizador(String id,String nomeFesta) {
        return this.fbd.buscarFPorganizador(id,nomeFesta);
    }
    
    List<Party> buscarFPparticipante(String id,String nomeFesta) {
        return this.fbd.buscarFPparticipante(id,nomeFesta);
    }
    
    public void deixarFesta(int festa,String nickname){
        this.fbd.deixarFesta(festa,nickname);
    }
    
    public java.util.List<String> consultarUsuariosFestaPrivada(int idFesta){
        return this.fbd.consultarUsuariosFestaPrivada(idFesta);
    }
    
    public java.util.List<String> consultarUsuariosFestaPublica(int idFesta){
        return this.fbd.consultarUsuariosFestaPublica(idFesta);
    }
  }
