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
public class Image {
    
    private String url;
    private int festa;
    private String galeria;
    private int x;
    private int y;
    
    public Image(String url,int x, int y,int festa,String galeria){
        this.url = url;
        this.x = x;
        this.y = y;
        this.festa=festa;
        this.galeria=galeria;
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
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public int getFesta() {
        return festa;
    }

    public void setFesta(int festa) {
        this.festa = festa;
    }

    public String getGaleria() {
        return galeria;
    }

    public void setGaleria(String galeria) {
        this.galeria = galeria;
    }
    
    
    
    
    
}
