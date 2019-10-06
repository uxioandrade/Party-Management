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
public class User {
    
    protected String id;
    protected String contrasinal;
    protected String nome;
    protected String email;
    protected float saldo;
    protected String biografia;
    protected String urlFotoPerfil;
    
    public User(String id,String contrasinal, String nome,String email, float saldo, String biografia, String urlFotoPerfil){
        this.id = id;
        this.contrasinal = contrasinal;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
        this.biografia = biografia;
        this.urlFotoPerfil = urlFotoPerfil;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the contrasinal
     */
    public String getContrasinal() {
        return contrasinal;
    }

    /**
     * @param contrasinal the contrasinal to set
     */
    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the saldo
     */
    public float getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the biografia
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * @param biografia the biografia to set
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * @return the urlFotoPerfil
     */
    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    /**
     * @param urlFotoPerfil the urlFotoPerfil to set
     */
    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }
    
    /**
     *@return o nome
     */
    public String getNome(){
        return nome;
    }
    
    /**
    *@param nome o nome
    */
    public void setNome(String nome){
        this.nome = nome;
    }
    
    
    
    
}
