/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author alumnogreibd
 */
public class MessagesTableModel extends AbstractTableModel{ 

    private java.util.List<app.Message> messages;
    
    public MessagesTableModel(){
        this.messages = new java.util.ArrayList<app.Message>();
    }
    
    public int getColumnCount (){
        return 3;
    }

    public int getRowCount(){
        return messages.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Usuario"; break;
            case 1: nombre= "Data"; break;
            case 2: nombre="Mensaxe"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.sql.Timestamp.class; break;
            case 2: clase=java.lang.String.class; break;
        }
        return clase;
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    public Object getValueAt(int row, int col){
        Object resultado=null;

        switch (col){
            case 0: resultado= messages.get(row).getEmisor(); break;
            case 1: resultado= messages.get(row).getFecha(); break;
            case 2: resultado=messages.get(row).getContenido();break;
        }
        return resultado;
    }

    public void setFilas(java.util.List<app.Message> mensajes){
        this.messages=mensajes;
        fireTableDataChanged();
    }

    public app.Message obtenerMensaje(int i){
        return this.messages.get(i);
    }
    
}
