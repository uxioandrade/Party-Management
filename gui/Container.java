/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import app.IEtiquetable;

/**
 *
 * @author alumnogreibd
 * @param <T>
 */
public class Container<T extends IEtiquetable> implements IEtiquetable
{
    private T t;
    
    public Container(T t)
    {
        this.t = t;
    }
    
    public T getContido()
    {
        return this.t;
    }
    
    @Override
    public String xerarEtiqueta()
    {
        return this.t.xerarEtiqueta();
    }
}
