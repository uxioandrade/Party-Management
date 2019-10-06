/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.time.LocalDate;

/**
 *
 * @author alumnogreibd
 */
public class Message {
    
    String emisor;
    String receptor;
    java.sql.Timestamp fecha;
    String contenido;
    Boolean borrado;

    
    public Message(String idEmisor,String idReceptor,java.sql.Timestamp data, String contido, Boolean borrar){
        
        this.emisor=idEmisor;
        this.receptor=idReceptor;
        if(data == null){
            this.fecha = new java.sql.Timestamp(System.currentTimeMillis()); //Hora actual con precisión de milisegundos
        }
        else this.setFecha(data);
        this.contenido=contido;
        this.borrado=borrar;
    }
    
    public void setEmisor(String id){
        this.emisor=id;
    }
    public String getEmisor() {
        return emisor;
    }
    
    public void setReceptor(String id){
        this.emisor=id;
    }
    public String getReceptor() {
        return receptor;
    }
    
    public void setFecha(java.sql.Timestamp data){
        this.fecha=data;
    }
    public java.sql.Timestamp getFecha() {
        return fecha;
    }
    
    public void setContenido(String contido){
        this.contenido=contido;
    }
    public String getContenido() {
        return contenido;
    }
    
    public void setBorrado(Boolean bool){
        this.borrado=bool;
    }
    public Boolean getBorrado() {
        return borrado;
    }
    
    
}
