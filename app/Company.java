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
public class Company extends User{
    
    private String cif;
    private String direccion;
    private String web;
    
    public Company(String id,String contrasinal, String nome,String email, float saldo, String biografia, String urlFotoPerfil,String cif,String direccion, String web){
        super(id,contrasinal,nome,email,saldo,biografia,urlFotoPerfil);
        this.cif = cif;
        this.direccion = direccion;
        this.web = web;
    }
    
    public Company(User u,String cif,String direccion, String web){
        super(u.getId(),u.getContrasinal(),u.getNome(),u.getEmail(),u.getSaldo(),u.getBiografia(),u.getUrlFotoPerfil());
        this.cif = cif;
        this.direccion = direccion;
        this.web = web;
    }

    /**
     * @return the cif
     */
    public String getCif() {
        return cif;
    }

    /**
     * @param cif the cif to set
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web the web to set
     */
    public void setWeb(String web) {
        this.web = web;
    }
    
}
