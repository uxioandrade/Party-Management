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
public class Location implements IEtiquetable {
    private float coordenadaLatitude;
    private float coordenadaLonxitude;
    private String nome;
    private String indicacions;
    private Boolean serPrivada;
    private String creador;
    
    public Location(float coordenadaLatitude, float coordenadaLonxitude, String nome, String indicacions, Boolean serPrivada, String creador)
    {
        this.coordenadaLatitude = coordenadaLatitude;
        this.coordenadaLonxitude = coordenadaLonxitude;
        this.nome = nome;
        this.indicacions = indicacions;
        this.serPrivada = serPrivada;
        this.creador = creador;
    }
    
    public Location(){
        this.coordenadaLatitude = 0;
        this.coordenadaLonxitude = 0;
        this.nome = " ";
        this.indicacions = " ";
        this.serPrivada = false;
        this.creador = " ";
    }   

    public float getCoordenadaLatitude() {
        return coordenadaLatitude;
    }

    public void setCoordenadaLatitude(float coordenadaLatitude) {
        this.coordenadaLatitude = coordenadaLatitude;
    }

    public float getCoordenadaLonxitude() {
        return coordenadaLonxitude;
    }

    public void setCoordenadaLonxitude(float coordenadaLonxitude) {
        this.coordenadaLonxitude = coordenadaLonxitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndicacions() {
        return indicacions;
    }

    public void setIndicacions(String indicacions) {
        this.indicacions = indicacions;
    }

    public Boolean getSerPrivada() {
        return serPrivada;
    }

    public void setSerPrivada(Boolean serPrivada) {
        this.serPrivada = serPrivada;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }
    
    @Override
    public String xerarEtiqueta()
    {
        return "<html><b>" + this.nome + "</b><br>" + this.indicacions + "</html>";
    }
}
