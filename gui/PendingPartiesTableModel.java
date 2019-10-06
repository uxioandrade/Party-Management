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
public class PendingPartiesTableModel extends AbstractTableModel{
    
    private java.util.List<app.Party> festas;
    
    public PendingPartiesTableModel(){
        this.festas = new java.util.ArrayList<app.Party>();
    }
     public int getColumnCount (){
        return 3;
    }

    public int getRowCount(){
        return festas.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Nome"; break;
            case 1: nombre= "Data"; break;
            case 2: nombre="Dias restantes"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.String.class; break;
            case 2: clase=java.lang.Integer.class; break;
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
            case 0: resultado= festas.get(row).getName(); break;
            case 1: resultado= festas.get(row).getDate(); break;
            case 2: resultado=festas.get(row).getDias();break;
        }
        return resultado;
    }

    public void setFilas(java.util.List<app.Party> festas){
        this.festas=festas;
        fireTableDataChanged();
    }

    public app.Party obtenerFesta(int i){
        return this.festas.get(i);
    }
}
