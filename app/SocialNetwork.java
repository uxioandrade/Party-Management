/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author alumnogreibd
 */
public class SocialNetwork {
    
    private String plataforma;
    private String id_plataforma;
    private String url;
    private String usuario;
    
    public SocialNetwork(String plataforma,String id_plataforma,String url,String usuario){
        this.plataforma = plataforma;
        this.id_plataforma = id_plataforma;
        this.url = url;
        this.usuario = usuario;
    }

    /**
     * @return the plataforma
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * @param plataforma the plataforma to set
     */
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    /**
     * @return the id_plataforma
     */
    public String getId_plataforma() {
        return id_plataforma;
    }

    /**
     * @param id_plataforma the id_plataforma to set
     */
    public void setId_plataforma(String id_plataforma) {
        this.id_plataforma = id_plataforma;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
