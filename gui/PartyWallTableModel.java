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
public class PartyWallTableModel extends AbstractTableModel{
    
    private java.util.List<app.WallPost> posts;
    
    public PartyWallTableModel(){
        this.posts = new java.util.ArrayList<app.WallPost>();
    }
    
    public int getColumnCount (){
        return 3;
    }

    public int getRowCount(){
        return posts.size();
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
            case 0: resultado= posts.get(row).getUser(); break;
            case 1: resultado= posts.get(row).getData(); break;
            case 2: resultado=posts.get(row).getMensaxe();break;
        }
        return resultado;
    }

    public void setFilas(java.util.List<app.WallPost> posts){
        this.posts=posts;
        fireTableDataChanged();
    }

    public app.WallPost obtenerPost(int i){
        return this.posts.get(i);
    }
    
}
