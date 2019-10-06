/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.sql.Time;

/**
 *
 * @author alumnogreibd
 */
public class Audio {
    
    private String url;
    private int party;
    private String gallery;
    private Time duration;

    public Audio(String url,Time duration,int party,String gallery){
        
        this.url=url;
        this.duration=duration;
        this.party=party;
        this.gallery=gallery;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }
    
    public int getParty(){
        return this.party;
    }
    
    
    public void setParty(int party){
        this.party=party;
    }
    
    public String getGallery(){
        return this.gallery;
    }
    
    public void setGallery(String gallery){
        this.gallery=gallery;
    }
    
}
