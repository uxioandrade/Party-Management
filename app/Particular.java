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
public class Particular extends User{
    
    private String fechaNacimiento;
    private String estadoSentimental;
    private String genero;
    
    public Particular(String id,String contrasinal, String nome, String email, float saldo, String biografia, String urlFotoPerfil, String fechaNacimiento, String estadoSentimental, String genero){
        super(id,contrasinal,nome,email,saldo,biografia,urlFotoPerfil);
        this.fechaNacimiento = fechaNacimiento;
        this.estadoSentimental = estadoSentimental;
        this.genero = genero;
    }
    
    public Particular(User u,String fechaNacimiento, String estadoSentimental, String genero){
        super(u.getId(),u.getContrasinal(),u.getNome(),u.getEmail(),u.getSaldo(),u.getBiografia(),u.getUrlFotoPerfil());
        this.fechaNacimiento = fechaNacimiento;
        this.estadoSentimental = estadoSentimental;
        this.genero = genero;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the estadoSentimental
     */
    public String getEstadoSentimental() {
        return estadoSentimental;
    }

    /**
     * @param estadoSentimental the estadoSentimental to set
     */
    public void setEstadoSentimental(String estadoSentimental) {
        this.estadoSentimental = estadoSentimental;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

  
    
}
