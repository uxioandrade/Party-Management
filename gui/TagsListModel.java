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
public class TagsListModel extends javax.swing.AbstractListModel {
    java.util.List<ContainerTag> elementos;
    
    public TagsListModel()
    {
        this.elementos = new java.util.ArrayList<>();
    }
    
    @Override
    public int getSize()
    {
        return this.elementos.size();
    }
    
    @Override
    public ContainerTag getElementAt(int i)
    {
        return this.elementos.get(i);
    }
    
    public void nuevoElemento(ContainerTag e)
    {
        this.elementos.add(e);
         fireIntervalAdded(this, this.elementos.size()-1, this.elementos.size()-1);
    }
    
    public void nuevoElemento(Container contenedor)
    {
        this.elementos.add(new ContainerTag(contenedor));
         fireIntervalAdded(this, this.elementos.size()-1, this.elementos.size()-1);
    }
    
    public void reemplazarElemento(int i, Container contenedor)
    {
        this.elementos.remove(i);
        this.elementos.add(new ContainerTag(contenedor));
        fireContentsChanged(this, 0, elementos.size()-1);
    }
    
    public void reemplazarElemento(int i, Container contenedor, String imagePath)
    {
        this.elementos.remove(i);
        this.elementos.add(new ContainerTag(contenedor, imagePath));
        fireContentsChanged(this, 0, elementos.size()-1);
    }
    
    public void eliminarElemento(int i)
    {
        this.elementos.remove(i);
        fireContentsChanged(this, 0, elementos.size()-1);
    }
    
    public void clear()
    {
        this.elementos.clear();
        fireContentsChanged(this, 0, -1);
    }
    
    public void setElementos(java.util.List<ContainerTag> elementos)
    {
        this.elementos = elementos;
        fireContentsChanged(this, 0, elementos.size()-1);
    }
    
    public java.util.List<ContainerTag> getElementos()
    {
        return this.elementos;
    }
}
