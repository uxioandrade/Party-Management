/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import app.Party;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author alumnogreibd
 */
public class DAOParties extends AbstractDAO{
    
    private app.FachadaAplicacion fa;
    
    public DAOParties(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
        this.fa=fa;
    }
    
    public String consultarOrganizador(int idFesta){
        
        Connection con;
        PreparedStatement stmFesta=null;
        ResultSet rsFesta=null;
        String organizador=" ";
        
        con=super.getConexion();
        
        try {
            stmFesta=con.prepareStatement("Select * from festas where id=? ");
            stmFesta.setInt(1, idFesta);
            rsFesta=stmFesta.executeQuery();
            if(rsFesta.next()){
                organizador=rsFesta.getString("organizador");
            }
        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al insertar la galeria");
        }finally{
          try {stmFesta.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        
        return organizador;
    }
    
    List<Party> buscarFestas(String usuarioSesion, String nomeFesta, String nomeOrganizador, String tematica, String privacidade, Date data){
        java.util.List<Party> resultado = new java.util.ArrayList<>();
        
        if(privacidade.equalsIgnoreCase("privada"))
        {
            resultado = this.buscarFestasPrivadas(usuarioSesion, nomeFesta, nomeOrganizador, tematica, data);
        }
        else if(privacidade.equalsIgnoreCase("publica"))
        {
            resultado = this.buscarFestasPublicas(usuarioSesion, nomeFesta, nomeOrganizador,tematica, data);
        }
        
        return resultado;
}
    
    public java.util.List<Party> buscarFestasPublicas (String usuarioSesion, String nomeFesta, String nomeOrganizador, String tematica, Date data)
    {
        java.util.List<Party> resultado = new java.util.ArrayList<>();
        Party festaActual;
        Connection con;

        PreparedStatement stmFestas=null;
        ResultSet rsFestas;
        
        con = this.getConexion();
        
        boolean serPrivada = false;
        
        
        String consulta = "select fe.id, fe.organizador, fe.nome, fe.descripcion, date_trunc('minute', fe.fecha) as fecha, pu.precio, count(en.codigoEntrada) as entradasAdquiridas, pu.aforo, (select count(*) from entradas where festa = fe.id) as vendidas " +
                            "from (festas as fe join publicas as pu on fe.id = pu.festa) left join entradas as en on fe.id = en.festa and en.usuario = ? " +
                            "where fe.nome like ? and fe.organizador like ? " +
                            "and fe.organizador not in (select bl.bloqueado from bloquear as bl where bl.bloqueador = ?) ";
                        
        if(data != null)
        {
            consulta += "and fe.fecha >= now() and fe.fecha >= ? ";
        }
        
        if(tematica != null)
        {
            consulta += "and exists (select * from teretiquetas as te where fe.id = te.festa and lower(te.etiqueta) like ?) ";
        }
        
        consulta += "group by fe.id, fe.organizador, fe.nome, fe.descripcion, fe.fecha, pu.precio, pu.aforo ";
        
        try
        {
            stmFestas = con.prepareStatement(consulta);
            stmFestas.setString(1, usuarioSesion);
            stmFestas.setString(2, "%"+nomeFesta+"%");
            stmFestas.setString(3,"%"+nomeOrganizador+"%");
            stmFestas.setString(4, usuarioSesion);
            
            if(data != null)
            {
                stmFestas.setDate(5, new java.sql.Date(data.getTime()));
                
                if(tematica != null)
                {
                    stmFestas.setString(6, "%"+tematica+"%");
                }
            }
            else
            {
                if(tematica != null)
                {
                    stmFestas.setString(5, "%"+tematica+"%");
                }
            }
            
            rsFestas = stmFestas.executeQuery();
            
            while(rsFestas.next())
            {
                
                festaActual = new Party(rsFestas.getInt("id"), rsFestas.getString("organizador"), rsFestas.getString("nome"), rsFestas.getString("fecha"),
                                        rsFestas.getString("descripcion"), false);

                resultado.add(festaActual);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }
        finally
        {
            try {stmFestas.close();} catch(SQLException e) {System.out.println("Imposible cerrar cursores"); }
        }
        
        return resultado;
    }
    
    public java.util.List<Party> buscarFestasPrivadas (String usuarioSesion, String nomeFesta, String nomeOrganizador, String tematica, Date data)
    {
        java.util.List<Party> resultado = new java.util.ArrayList<>();
        Party festaActual;
        Connection con;

        PreparedStatement stmFestas=null;
        ResultSet rsFestas;
        
        con = this.getConexion();
        
        boolean serPrivada = false;
        
        String consulta = "select fe.id, fe.organizador, fe.nome, date_trunc('minute', fe.fecha) as fecha, fe.descripcion, " +
                          "(fe.coordenadas).latitude as latitude, (fe.coordenadas).lonxitude as lonxtiude, " +
                          "fe.indicacionsLocalizacion " +
                          "from festas as fe join privadas as pr on fe.id = pr.festa " +
                          "where fe.nome like ? and fe.organizador like ? " +
                          "and fe.organizador not in (select bl.bloqueado from bloquear as bl where bl.bloqueador = ?) ";
        
        if(data != null)
        {
            consulta += "and fe.fecha >= ? and fe.fecha >= now() ";
        }
        
        if(tematica != null)
        {
            consulta += "and exists (select * from teretiquetas as te where fe.id = te.festa and lower(te.etiqueta) like ?) ";
        }
        
        try
        {
            stmFestas = con.prepareStatement(consulta);
            //stmFestas.setString(1, usuarioSesion);
            stmFestas.setString(1, "%"+nomeFesta+"%");
            stmFestas.setString(2, "%"+nomeOrganizador+"%");
            stmFestas.setString(3, usuarioSesion);
            
            if(data != null)
            {
                stmFestas.setDate(4, new java.sql.Date(data.getTime()));
                
                if(tematica != null)
                {
                    stmFestas.setString(5, "%"+tematica+"%");
                }
            }
            else
            {
                if(tematica != null)
                {
                    stmFestas.setString(4, "%"+tematica+"%");
                }
            }
            
            rsFestas = stmFestas.executeQuery();
            
            while(rsFestas.next())
            {
                
                    festaActual = new Party(rsFestas.getInt("id"), rsFestas.getString("organizador"), rsFestas.getString("nome"), rsFestas.getString("fecha"),
                                        rsFestas.getString("descripcion"), true);
                           
                    resultado.add(festaActual);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }
        finally
        {
            try {stmFestas.close();} catch(SQLException e) {System.out.println("Imposible cerrar cursores"); }
        }
        
        return resultado;
}

    
    public List<Party> buscarFPparticipante(String idUsuario,String nomeFesta){
        
        java.util.List<Party> resultado = new java.util.ArrayList<>();
        Party festaActual;
        Connection con;

        PreparedStatement stmFestas=null;
        ResultSet rsFestas;
        
        con = this.getConexion();

         try{
            stmFestas = con.prepareStatement("select fe.id, fe.nome, date_trunc('minute', fe.fecha) as fecha,  current_date - CAST(fe.fecha as date) as dias, fe.organizador, fe.id in (Select festa from privadas) as serPrivada"+
                                             " from festas as fe" +
                                             " where ( fe.nome like ? and fe.fecha>now() and fe.organizador != ?)"+
                                             " and ( fe.id in (select festa from entradas where usuario=? )"+
                                             " or fe.id in (select festa from invitar where usuario=? and aceptada = 't')"+
                                             " or fe.id in (select festa from solicitar where usuario=? and aceptada = 't'))");
            stmFestas.setString(1, "%"+nomeFesta+"%");
            stmFestas.setString(2, idUsuario);
            stmFestas.setString(3, idUsuario);
            stmFestas.setString(4, idUsuario);
            stmFestas.setString(5, idUsuario);
            
            
            rsFestas = stmFestas.executeQuery();
            
            while(rsFestas.next()){
                festaActual = new Party(rsFestas.getInt("id"), rsFestas.getString("nome"), rsFestas.getString("fecha"), rsFestas.getInt("dias"),rsFestas.getBoolean("serPrivada"), rsFestas.getString("organizador"));

                resultado.add(festaActual);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        }finally{
            try {stmFestas.close();} catch(SQLException e) {System.out.println("Imposible cerrar cursores"); }
        }
        return resultado;
    }
    
    public List<Party> buscarFPorganizador(String idUsuario,String nomeFesta){
        
        java.util.List<Party> resultado = new java.util.ArrayList<>();
        Party festaActual;
        Connection con;

        PreparedStatement stmFestas=null;
        ResultSet rsFestas;
        
        con = this.getConexion();
                                
         try{
            stmFestas = con.prepareStatement("select fe.id, fe.nome, date_trunc('minute', fe.fecha) as fecha, current_date - CAST(fe.fecha as date) as dias, fe.organizador, fe.id in (Select festa from privadas) as serPrivada"+
                                             " from festas as fe " +
                                             "where fe.nome like ? and fe.fecha>now() and fe.organizador =? ");
            stmFestas.setString(1, "%"+nomeFesta+"%");
            stmFestas.setString(2, idUsuario);
            
            
            rsFestas = stmFestas.executeQuery();
            
            while(rsFestas.next()){
                festaActual = new Party(rsFestas.getInt("id"), rsFestas.getString("nome"), rsFestas.getString("fecha"), rsFestas.getInt("dias"),rsFestas.getBoolean("serPrivada"), rsFestas.getString("organizador"));

                resultado.add(festaActual);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        }finally{
            try {stmFestas.close();} catch(SQLException e) {System.out.println("Imposible cerrar cursores"); }
        }
        return resultado;
    }
    
     public void deixarFesta(int festa,String nickname){
        
        Connection con;
        PreparedStatement stmInvitar=null;
        PreparedStatement stmSolicitar=null;
        PreparedStatement stmEntradas=null;
        PreparedStatement stmPublicas=null;
        PreparedStatement stmDevolverDinero=null;
        PreparedStatement stmActSaldo=null;
        ResultSet rsPublicas=null;
        ResultSet rsDevolucion=null;
        
        con=super.getConexion();
        
        try{
            con.setAutoCommit(false);
            stmPublicas=con.prepareStatement("Select festa from publicas where festa=?");
            stmPublicas.setInt(1,festa);
            rsPublicas=stmPublicas.executeQuery();
            if(rsPublicas.next()){//Fiesta publica: se accede a traves de una entrada
                try{
                stmDevolverDinero = con.prepareStatement("select e.cantidade,f.organizador from entradas as e, festas as f "+
                                                " where e.festa = f.id and f.id=? AND " +
                                                " e.usuario = ? and "+
                                                " CAST(f.fecha as date)-CAST(now() as date) >= 0 and " +
                                                " e.fechaadquisicion IS NOT NULL ");            
                stmDevolverDinero.setInt(1,festa);
                stmDevolverDinero.setString(2,nickname);
                rsDevolucion = stmDevolverDinero.executeQuery();
                while(rsDevolucion.next()){
                    float cantidad = rsDevolucion.getFloat("cantidade");
                    try{
                        stmActSaldo = con.prepareStatement("update usuarios set saldo = saldo + ? " +
                                                            " where nickname = ? ");
                        //Actualizamos o saldo do organizador da festa
                        stmActSaldo.setFloat(1,cantidad/2);
                        stmActSaldo.setString(2,rsDevolucion.getString("organizador"));
                        stmActSaldo.executeUpdate();
                        //Actualizamos o saldo do usuario
                        stmActSaldo.setFloat(1,cantidad/2);
                        stmActSaldo.setString(2, nickname);
                        stmActSaldo.executeUpdate();
                    }catch (SQLException e){
                                e.printStackTrace();
                        try{
                            if(con!=null)
                                con.rollback();
                        }catch(SQLException e2){
                            e2.printStackTrace();
                    this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                }
                    }finally{stmActSaldo.close();}
                }
                    try{
                        stmEntradas=con.prepareStatement("delete from entradas "
                                                         +"where usuario= ? AND "
                                                         +"festa=(select entradas.festa from entradas,festas where festas.id=entradas.festa AND entradas.festa=? AND CURRENT_DATE<festas.fecha Limit 1)");
                        stmEntradas.setString(1,nickname);
                        stmEntradas.setInt(2,festa);
                        stmEntradas.executeUpdate();
                        con.commit();
                    }catch (SQLException e1){
                            //e1.printStackTrace();
                            System.out.println(e1.getMessage());
                        try{
                            if(con!=null)
                                con.rollback();
                        }catch(SQLException e2){
                            e2.printStackTrace();
                        this.getFachadaAplicacion().muestraExcepcion(e1.getMessage());
                        }
                        this.getFachadaAplicacion().muestraExcepcion("Error al anular la asistencia a la fiesta");
                    }finally{
                        try {stmEntradas.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                    }
                }catch (SQLException e4){
                        e4.printStackTrace();
                    try{
                        if(con!=null)
                            con.rollback();
                    }catch(SQLException e2){
                        e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e4.getMessage());
                }
                    this.getFachadaAplicacion().muestraExcepcion("Error al anular la asistencia a la fiesta");
                }finally{
                    try {stmDevolverDinero.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                }
            }
            
            else{//Fiesta privada: se accede a traves de una invitacion o de una solicitud
                try{    
                    stmSolicitar=con.prepareStatement("delete from solicitar "
                                                      +"where usuario= ? AND "
                                                      +"festa=(select solicitar.festa from solicitar,festas where festas.id=solicitar.festa AND solicitar.festa=? AND CURRENT_DATE<festas.fecha Limit 1)");
                    stmSolicitar.setString(1,nickname);
                    stmSolicitar.setInt(2,festa);
                    stmSolicitar.executeUpdate();
                }catch (SQLException e2){
                        e2.printStackTrace();
                   try{
                       if(con!=null)
                           con.rollback();
                   }catch(SQLException e3){
                       e3.printStackTrace();
               this.getFachadaAplicacion().muestraExcepcion(e2.getMessage());
                }
                     this.getFachadaAplicacion().muestraExcepcion("Error al anular la asistencia a la fiesta");
                }finally{
                    try {stmSolicitar.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                }
                
                try{            
                    stmInvitar=con.prepareStatement("delete from invitar "
                                            +"where usuario= ? AND "
                                            +"festa=(select invitar.festa from invitar,festas where festas.id=invitar.festa AND invitar.festa=? AND CURRENT_DATE<festas.fecha Limit 1)");
                    stmInvitar.setString(1,nickname);                  
                    stmInvitar.setInt(2,festa);   
                    stmInvitar.executeUpdate();
                    con.commit();
                }catch (SQLException e3){
                        e3.printStackTrace();
                    try{
                        if(con!=null)
                            con.rollback();
                    }catch(SQLException e2){
                        e2.printStackTrace();
                this.getFachadaAplicacion().muestraExcepcion(e3.getMessage());
                }
                    this.getFachadaAplicacion().muestraExcepcion("Error al anular la asistencia a la fiesta");
                }finally{
                    try {stmInvitar.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                }    
            }
        }catch (SQLException e){
          e.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                }
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmPublicas.close();con.setAutoCommit(true);} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }  
        
    }
    
    public java.util.List<String> consultarUsuariosFestaPrivada(int idFesta){
        
        Connection con;
        
        PreparedStatement stmUsers = null;
        ResultSet rsUsers = null;
        
        java.util.List<String> users = new java.util.ArrayList<>();
        
        con = this.getConexion();
                                
         try{
            stmUsers = con.prepareStatement("SELECT DISTINCT usuario "
                                            + "FROM ((SELECT * FROM solicitar WHERE festa = ?) "
                                                    + "UNION (SELECT * FROM invitar WHERE festa = ?)) as assist");
            stmUsers.setInt(1, idFesta);
            stmUsers.setInt(2, idFesta);
            
            rsUsers = stmUsers.executeQuery();
            
            while(rsUsers.next()){
                String a = rsUsers.getString("usuario");                      
                users.add(a);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        }finally{
            try {stmUsers.close();} catch(SQLException e) {System.out.println("Imposible cerrar cursores"); }
        }
        
        return users;
        
    }
    
    public java.util.List<String> consultarUsuariosFestaPublica(int idFesta){
        
        Connection con;
        
        PreparedStatement stmUsers = null;
        ResultSet rsUsers = null;
        
        java.util.List<String> users = new java.util.ArrayList<>();
        
        con = this.getConexion();
                                
         try{
            stmUsers = con.prepareStatement("SELECT DISTINCT usuario "
                                            + "FROM entradas WHERE festa = ?");
            stmUsers.setInt(1, idFesta);
            
            rsUsers = stmUsers.executeQuery();
            
            while(rsUsers.next()){
                String a = rsUsers.getString("usuario");                      
                users.add(a);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        }finally{
            try {stmUsers.close();} catch(SQLException e) {System.out.println("Imposible cerrar cursores"); }
        }
        
        return users;
        
    }
    
}
  
