/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author alumnogreibd
 */
public class StringsListModel extends javax.swing.AbstractListModel{
    
    java.util.List<String> elementos;
    
    public StringsListModel(){
        this.elementos = new java.util.ArrayList<>();
    }
    
    public int getSize(){
        return this.elementos.size();
    }
    
    public String getElementAt(int i){
        return this.elementos.get(i);
    }
    
    public void nuevoElemento(String e){
        this.elementos.add(e);
        fireIntervalAdded(this, this.elementos.size()-1,this.elementos.size()-1);
    }
    
    public void borrarElemento(int i){
        String e;
        e = this.elementos.get(i);
        this.elementos.remove(i);
        this.elementos.remove(i);
        fireIntervalRemoved(this,i,i);
    }
    
    public void setElementos(java.util.List<String> elementos){
        this.elementos = elementos;
        fireContentsChanged(this,0,elementos.size()-1);
    }
    
    public java.util.List<String> getElementos(){
        return this.elementos;
    }
    
   
    
}
