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
public class Party implements IEtiquetable {
    private int id;
    private String host;
    private String name;
    private String date;
    private String description;
    private float latitude;
    private float lonxitude;
    private String locationGuidelines;
    private int days;
    private Boolean isPrivate;
    private Location location;
    
    public Party(int id, String host, String name, String party, String description, Boolean isPrivate)
    {
        this.id = id;
        this.host = host;
        this.name = name;
        this.date = party;
        this.description = description;
        this.isPrivate = isPrivate;
        
        this.days = -1;
    }
    
    public Party(int id,String name, String date,int days, Boolean isPrivate, String host){
        this.id=id;
        this.name=name;
        this.date=date;
        this.days=Math.abs(days);
        this.isPrivate=isPrivate;
        this.host=host;
    }
    public Party(String name, String date,String host,Location location,Boolean isPrivate){
        this.name=name;
        this.date=date;
        this.host=host;
        this.location=location;
        this.isPrivate=isPrivate;
        
        this.days = -1;
    }
    public Party(int id, String organizador, String nome, String fecha, String descripcion, float latitude, float lonxitude, String indicacionsLocalizacion) {
        this.id = id;
        this.host = organizador;
        this.name = nome;
        this.date = fecha;
        this.description = descripcion;
        this.latitude = latitude;
        this.lonxitude = lonxitude;
        this.locationGuidelines = indicacionsLocalizacion;
        
        this.isPrivate = null;
        
        this.days = -1;
    }

    public Party(int id, String organizador, String nome, String fecha, String descripcion, float latitude, float lonxitude, String indicacionsLocalizacion, Boolean serPrivada) {
        this.id = id;
        this.host = organizador;
        this.name = nome;
        this.date = fecha;
        this.description = descripcion;
        this.latitude = latitude;
        this.lonxitude = lonxitude;
        this.locationGuidelines = indicacionsLocalizacion;
        this.isPrivate = serPrivada;
        
        this.days = -1;
    }
    
    public Party(int id, String organizador, String nome, String fecha, String descripcion, Location ubicacion, Boolean serPrivada)
    {
        this.id = id;
        this.host = organizador;
        this.name = nome;
        this.date = fecha;
        this.description = descripcion;
        this.location = ubicacion;
        this.isPrivate = serPrivada;
        
        this.days = -1;
    }
    
    @Override
    public String xerarEtiqueta()
    {
        String str;
        
        if(this.description != null)
        {
            str= "<html><b>" +this.name + "</b>&ensp; • &ensp;Organizador: " +  this.host + 
                "<br>" + "&ensp; • &ensp;Fecha: " +  this.date + "</html>";
            
            if(days != -1)
            {
                str= "<html><b>" +this.name + "</b>&ensp; • &ensp;Organizador: " +  this.host + 
                "<br>" + "&ensp; • &ensp;Fecha: " +  this.date + "<br>Fai: " + this.days + "</html>";
            }
        }
        else
        {
            str= "<html><b>" +this.name + "</b>&ensp; • &ensp;Organizador: " +  this.host + 
                "<br>" + "&ensp; • &ensp;Fecha: " +  this.date + "</html>";
            
            if(days != -1)
            {
                str= "<html><b>" +this.name + "</b>&ensp; • &ensp;Organizador: " +  this.host + 
                "<br>" + "&ensp; • &ensp;Fecha: " +  this.date + "<br>Fai: " + this.days + " dias </html>";
            }
        }
        
        return str;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void steHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String fecha) {
        this.date = fecha;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLonxitude() {
        return lonxitude;
    }

    public void setLonxitude(float lonxitude) {
        this.lonxitude = lonxitude;
    }

    public String getLocationGuidelines() {
        return locationGuidelines;
    }

    public void setLocationGuidelines(String locationGuidelines) {
        this.locationGuidelines = locationGuidelines;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean serPrivada) {
        this.isPrivate = serPrivada;
    }

    public void setDias(int dias) {
        this.days = dias;
    }

    public int getDias() {
        return days;
    }

    public void setLocation(Location ubicacion) {
        this.location = ubicacion;
    }

    public Location getLocation() {
        return location;
    }
}
