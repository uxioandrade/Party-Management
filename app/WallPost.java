/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author alumnogreibd
 */
public class WallPost {
    
    private String user; //Usuario ou String?
    private java.sql.Timestamp data; //Pódese pensar noutro tipo de dato para a fecha do mensaje
    private String mensaxe; 
    private boolean esVisible;
    private int festa; //(haberá que falar co outro grupo para ver como está implementada)
    
    public WallPost(String u, String message, java.sql.Timestamp data, int festa){
        this.user = u;
        if(data == null){
            this.data = new java.sql.Timestamp(System.currentTimeMillis()); //Hora actual con precisión de milisegundos
        }
        else this.setData(data);
        this.mensaxe = message;
        this.festa = festa;
        this.setEsVisible(true);
    }

    public int getFesta() {
        return festa;
    }

    public void setFesta(int festa) {
        this.festa = festa;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public java.sql.Timestamp getData() {
        return data;
    }

    public void setData(java.sql.Timestamp data) {
        this.data = data;
    }

    public String getMensaxe() {
        return mensaxe;
    }

    public void setMensaxe(String mensaxe) {
        this.mensaxe = mensaxe;
    }

    public boolean isEsVisible() {
        return esVisible;
    }

    public void setEsVisible(boolean esVisible) {
        this.esVisible = esVisible;
    }
    
}
