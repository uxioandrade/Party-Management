/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import app.User;
import app.Particular;
import app.Company;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author alumnogreibd
 */
public class DAOUsers extends AbstractDAO{
    
    public DAOUsers(Connection conexion, app.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public User validarUsuario(String id, String clave){
        User resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        
        String contrasinalAlmacenado = null;
        
        con = this.getConexion();
        
        try{
            stmUsuario = con.prepareStatement("select * from usuarios where nickname = ?");
            
            stmUsuario.setString(1,id);
            rsUsuario = stmUsuario.executeQuery();
            if(rsUsuario.next()){                        
                contrasinalAlmacenado = rsUsuario.getString("contrasinal");
                if(app.Password.checkPassword(clave, contrasinalAlmacenado))
                    resultado=this.seleccionarUsuario(new User(rsUsuario.getString("nickname"),rsUsuario.getString("contrasinal"),rsUsuario.getString("nome"),rsUsuario.getString("email"),rsUsuario.getFloat("saldo"),rsUsuario.getString("biografia"),rsUsuario.getString("urlFotoPerfil")));
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        }finally{
            try{stmUsuario.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public Boolean insertarUsuario(User u){
        Connection con;
        PreparedStatement stmUsuario=null;
        PreparedStatement stmIdUsuario = null; //Checkea unicidad
        ResultSet rsCheck = null;
        String idUsuario=null;

        con=super.getConexion();
        
        try{
            stmIdUsuario = con.prepareStatement("select nickname from usuarios where nickname = ?");
            stmIdUsuario.setString(1, u.getId());
            rsCheck = stmIdUsuario.executeQuery();
            if(rsCheck.next())
                return false;
        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al insertar el usuario");
        }
        
        if(u instanceof app.Particular){
            Particular p = (Particular) u;
            try {
                con.setAutoCommit(false);
                stmUsuario=con.prepareStatement("insert into usuarios(nickname,contrasinal, nome, email, saldo, biografia, urlFotoPerfil) "+
                                              "values (?,?,?,?,?,?,?)");
                stmUsuario.setString(1,p.getId());
                stmUsuario.setString(2,p.getContrasinal());
                stmUsuario.setString(3,p.getNome());
                stmUsuario.setString(4,p.getEmail());
                stmUsuario.setFloat(5, p.getSaldo());
                stmUsuario.setString(6,p.getBiografia());
                stmUsuario.setString(7,p.getUrlFotoPerfil());
                stmUsuario.executeUpdate();
                stmUsuario=con.prepareStatement("insert into particulares(nickname,fechaNacimiento,estadosentimental,sexo) "+
                                                " values (?,?,?,?)");
                Date fecha = new Date(p.getFechaNacimiento());
                stmUsuario.setString(1,p.getId());
                stmUsuario.setDate(2,new java.sql.Date(fecha.getTime()));
                stmUsuario.setString(3, p.getEstadoSentimental());
                if(p.getGenero().equals("Hombre")){
                    stmUsuario.setString(4, "M");
                }else if(p.getGenero().equals("Mujer")){
                    stmUsuario.setString(4, "F");
                }else{
                    stmUsuario.setString(4, "O");
                }
                stmUsuario.executeUpdate();
                
                con.commit();
                
            }catch (SQLException e){
                e.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
              this.getFachadaAplicacion().muestraExcepcion("Error al borrar el usuario");
                }
            }finally{
                try {if(stmUsuario!=null) stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }  
        }else{
            Company e = (Company) u;
            try{
                con.setAutoCommit(false);
                stmUsuario=con.prepareStatement("insert into usuarios(nickname,contrasinal, nome, email, saldo, biografia, urlFotoPerfil) "+
                                              "values (?,?,?,?,?,?,?); ");                                               
                stmUsuario.setString(1,e.getId());
                stmUsuario.setString(2,e.getContrasinal());
                stmUsuario.setString(3,e.getNome());
                stmUsuario.setString(4,e.getEmail());
                stmUsuario.setFloat(5, e.getSaldo());
                stmUsuario.setString(6,e.getBiografia());
                stmUsuario.setString(7,e.getUrlFotoPerfil());
                stmUsuario.executeUpdate();
                stmUsuario = con.prepareStatement("insert into empresas(nickname,cif,direccion,web) "+
                                                " values (?,?,?,?)");
                stmUsuario.setString(1,e.getId());
                stmUsuario.setString(2,e.getCif());
                stmUsuario.setString(3,e.getDireccion());
                stmUsuario.setString(4,e.getWeb());
                stmUsuario.executeUpdate();
                
                con.commit();
            }catch (SQLException ex){
                ex.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
              this.getFachadaAplicacion().muestraExcepcion("Error al borrar el usuario");
                }
            }finally{
                try {if(stmUsuario!=null) stmUsuario.close();con.setAutoCommit(true);} catch (SQLException ex){System.out.println("Imposible cerrar cursores");}
            }
        }
        
        return true;
    }
    
    public User seleccionarUsuario(String id){
        Connection con;
        PreparedStatement stmUsuario = null;
        PreparedStatement stmParticular=null;
        PreparedStatement stmEmpresa=null;
        ResultSet rsEmpresa = null;
        ResultSet rsUsuario = null;
        ResultSet rsParticular=null;
        con=super.getConexion();
        Particular particular = null;
        User u = null;
        Company empresa=null;
        try{
            stmUsuario = con.prepareStatement("select * from usuarios where nickname = ?");
            stmUsuario.setString(1,id);
            rsUsuario = stmUsuario.executeQuery();
            if(rsUsuario.next()){
                u = new User(rsUsuario.getString("nickname"),rsUsuario.getString("contrasinal"),rsUsuario.getString("nome"),rsUsuario.getString("email"),rsUsuario.getFloat("saldo"),rsUsuario.getString("biografia"),rsUsuario.getString("urlFotoPerfil"));
                stmParticular = con.prepareStatement("select * from particulares where nickname = ?");
                stmParticular.setString(1, id);
                rsParticular = stmParticular.executeQuery();
            if(rsParticular.next()){
                particular= new Particular(u,rsParticular.getString("fechaNacimiento"),rsParticular.getString("estadoSentimental"),rsParticular.getString("sexo"));                                  
            }
            else{
                    stmEmpresa = con.prepareStatement("select * from empresas where nickname = ?");
                    stmEmpresa.setString(1,id);
                    rsEmpresa = stmEmpresa.executeQuery();
                    if(rsEmpresa.next()){
                        empresa=new Company(u,rsEmpresa.getString("CIF"),rsEmpresa.getString("direccion"),rsEmpresa.getString("web"));
                    }
            }
            }
        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al seleccionar el usuario");
        }finally{
            try{if(stmParticular!=null)stmParticular.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
        if(particular!=null){
            return particular;
        }
        else{
            return empresa;
        }
    }
    
    public User seleccionarUsuario(User u){
        Connection con;       
        PreparedStatement stmParticular=null;
        PreparedStatement stmEmpresa=null;
        ResultSet rsEmpresa = null;
        ResultSet rsParticular=null;
        Particular particular=null;
        Company empresa=null;
        con=super.getConexion();
        
        try{
            stmParticular = con.prepareStatement("select * from particulares where nickname = ?");
            stmParticular.setString(1, u.getId());
            rsParticular = stmParticular.executeQuery();
            if(rsParticular.next()){
                particular= new Particular(u,rsParticular.getString("fechaNacimiento"),rsParticular.getString("estadoSentimental"),rsParticular.getString("sexo"));              
            }
            else{
                try{
                    stmEmpresa = con.prepareStatement("select * from empresas where nickname = ?");
                    stmEmpresa.setString(1,u.getId());
                    rsEmpresa = stmEmpresa.executeQuery();
                    if(rsEmpresa.next()){
                        empresa=new Company(u,rsEmpresa.getString("CIF"),rsEmpresa.getString("direccion"),rsEmpresa.getString("web"));
                    }
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                    this.getFachadaAplicacion().muestraExcepcion("Error al seleccionar el usuario");
                }finally{
                try{stmEmpresa.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
                }
            }
        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion("Error al seleccionar el usuario");
        }finally{
            try{stmParticular.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
        if(particular!=null){
            return particular;
        }
        else{
            return empresa;
        }
    }
    
    public String consultarContrasinal(String email){
        String resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        con = this.getConexion();
        
        try{
            stmUsuario = con.prepareStatement("select contrasinal from usuarios where email = ?");
            
            stmUsuario.setString(1,email);
            rsUsuario = stmUsuario.executeQuery();
            if(rsUsuario.next()){
                resultado = rsUsuario.getString("contrasinal");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        }finally{
            try{stmUsuario.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public void updateContrasinal(User u,String contrasinalNovo){
        
  
        Connection con;
        PreparedStatement stmUsuario =null;
        con= this.getConexion();
            
            try{
                stmUsuario=con.prepareStatement("Update usuarios "
                                              + "Set contrasinal= ? "
                                              + "Where nickname= ?");
                stmUsuario.setString(1,contrasinalNovo);
                stmUsuario.setString(2,u.getId());

                stmUsuario.executeUpdate();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }finally{
              try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
        } 
    
    public void updateContrasinal(String email, String contrasinalNovo){
        Connection con;
        PreparedStatement stmUsuario =null;
        con= this.getConexion();
            
            try{
                stmUsuario=con.prepareStatement("Update usuarios "
                                              + "Set contrasinal= ? "
                                              + "Where email= ?");
                stmUsuario.setString(1,contrasinalNovo);
                stmUsuario.setString(2,email);

                stmUsuario.executeUpdate();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
                this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }finally{
              try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
    }
    
    public void updateParticular(Particular p) {
        
        Connection con;
        PreparedStatement stmUsuario=null;
        PreparedStatement stmParticular=null;
        con=this.getConexion();
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        
        try{
            con.setAutoCommit(false);
            stmUsuario=con.prepareStatement("Update usuarios"
                                          + " Set nome=?, "
                                          + "     email=?, "
                                          + "     biografia=?"
                                          + "Where nickname=?");
            stmUsuario.setString(1,p.getNome());
            stmUsuario.setString(2,p.getEmail());
            stmUsuario.setString(3,p.getBiografia());
            stmUsuario.setString(4,p.getId());          
            stmUsuario.executeUpdate();
            try{
                stmParticular=con.prepareStatement("Update particulares "
                                                    +"Set fechaNacimiento=?, "
                                                    +"     estadoSentimental=?, "
                                                    +"     sexo=?"
                                                    +"Where nickname=?");
                
                 
                stmParticular.setDate(1, new java.sql.Date(format.parse(p.getFechaNacimiento()).getTime()));
                stmParticular.setString(2,p.getEstadoSentimental());
                stmParticular.setString(3, p.getGenero());
                stmParticular.setString(4,p.getId());
                stmParticular.executeUpdate();
                con.commit();;
            }catch(ParseException ex){                
                System.out.println(ex.getMessage());
                this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            } catch (SQLException ex) {
                ex.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
                    this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
                }
                }finally{
                    try {stmParticular.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                }
        }catch(SQLException e){
                e.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                }
        }finally{
          try {stmUsuario.close();con.setAutoCommit(true);} catch (SQLException eq){System.out.println("Imposible cerrar cursores");}
        }
    }
    
     public void updateEmpresa(Company emp) {
        
        Connection con;
        PreparedStatement stmUsuario=null;
        PreparedStatement stmEmpresa=null;
        con=this.getConexion();
       
        
        try{
            con.setAutoCommit(false);
            stmUsuario=con.prepareStatement("Update usuarios"
                                          + " Set nome=?, "
                                          + "     email=?,"
                                          + "     biografia=? "
                                          + "Where nickname=?");
            stmUsuario.setString(1,emp.getNome());
            stmUsuario.setString(2,emp.getEmail());
            stmUsuario.setString(3,emp.getBiografia());
            stmUsuario.setString(4,emp.getId());          
            stmUsuario.executeUpdate();
            try{
                stmEmpresa=con.prepareStatement("Update empresas "
                                                    +"Set cif=?, "
                                                    +"     direccion=?, "
                                                    +"     web=? "
                                                    +"Where nickname=?");
                
                
                stmEmpresa.setString(1,emp.getCif());
                stmEmpresa.setString(2, emp.getDireccion());
                stmEmpresa.setString(3,emp.getWeb());
                stmEmpresa.setString(4,emp.getId());
                stmEmpresa.executeUpdate();
                con.commit();
                
            }catch(SQLException e){
                e.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                }
            }finally{
                try {stmEmpresa.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
        }catch(SQLException e){
            e.printStackTrace();
                try{
                    if(con!=null)
                        con.rollback();
                }catch(SQLException e2){
                    e2.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                }
        }finally{
          try {stmUsuario.close();con.setAutoCommit(true);} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
     
    public float consultarSaldo(String id){
        float resultado = 0;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        con = this.getConexion();
        
        try{
            stmUsuario = con.prepareStatement("select saldo from usuarios where nickname = ?");
            
            stmUsuario.setString(1,id);
            rsUsuario = stmUsuario.executeQuery();
            if(rsUsuario.next()){
                resultado = rsUsuario.getFloat("saldo");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        }finally{
            try{stmUsuario.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public void updateSaldo(float saldo, String id){
        float saldoTotal = 0;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        con = this.getConexion();
        
        float saldoActual = this.consultarSaldo(id);
        saldoTotal = saldo+saldoActual;
        try{
            stmUsuario = con.prepareStatement("update usuarios "
                    + "set saldo = ? "
                    + "where nickname = ?");
            stmUsuario.setFloat(1, saldoTotal);
            stmUsuario.setString(2,id);
            stmUsuario.executeUpdate();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        }finally{
            try{stmUsuario.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
    }

    public float borrarUsuario(String id){
        Connection con;
        PreparedStatement stmBorrarUsuario=null;
        ResultSet rsDevolucion;
        ResultSet rsUsuario;
        float saldoUsuario = 0;
        con=super.getConexion();
        
        try{
            con.setAutoCommit(false);
            stmBorrarUsuario=con.prepareStatement("select * "+
                                        "from festas "+
                                            "where organizador = ? and CAST(fecha as date)-CAST(now() as date) >= 0");
            stmBorrarUsuario.setString(1, id);
            rsUsuario=stmBorrarUsuario.executeQuery();
            if(rsUsuario.next()){
                return -1;
            }
            
            stmBorrarUsuario = con.prepareStatement("select e.cantidade,f.organizador from entradas as e, festas as f "+
                                                " where e.festa = f.id and " +
                                                " e.usuario = ? and "+
                                                " CAST(f.fecha as date)-CAST(now() as date) >= 0 and " +
                                                " e.fechaadquisicion IS NOT NULL ");
            stmBorrarUsuario.setString(1,id);
            rsDevolucion = stmBorrarUsuario.executeQuery();
            while(rsDevolucion.next()){
                float cantidad = rsDevolucion.getFloat("cantidade")/2;
                saldoUsuario += cantidad;
                String organizador = rsDevolucion.getString("organizador");
                stmBorrarUsuario = con.prepareStatement("update usuarios set saldo = saldo + ? " +
                                                    " where nickname = ? or nickname = ?");
                //Se le devuelve la mitad de la entrada al usuario
                //Y se le pasa inmediatamente la otra mitad al organizador
                stmBorrarUsuario.setFloat(1,cantidad);
                stmBorrarUsuario.setString(2, id);
                stmBorrarUsuario.setString(3,organizador);
                stmBorrarUsuario.executeUpdate();
            }

            stmBorrarUsuario = con.prepareStatement("delete from usuarios as u " +
                    " where u.nickname = ?");
            stmBorrarUsuario.setString(1, id);
            stmBorrarUsuario.executeUpdate();
            con.commit();

        }catch (SQLException e){
          e.printStackTrace();
          try{
              if(con!=null)
                  con.rollback();
          }catch(SQLException e2){
              e2.printStackTrace();
              this.getFachadaAplicacion().muestraExcepcion("Error al borrar el usuario");
          }
        }finally{
          try {if(stmBorrarUsuario!=null) stmBorrarUsuario.close();con.setAutoCommit(true);} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }  
        return saldoUsuario;
    }
    
   
    
    public void updateUrlPerfil(String urlPerfil,String id){
        Connection con;
        PreparedStatement stmUsuario = null;
        con = this.getConexion();
        
        try{
            stmUsuario = con.prepareStatement("update usuarios "
                    + "set urlFotoPerfil = ? "
                    + "where nickname = ?");
            stmUsuario.setString(1, urlPerfil);
            stmUsuario.setString(2,id);
            stmUsuario.executeUpdate();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        }finally{
            try{stmUsuario.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
    }
    
    public java.util.List<String> buscarUsuario(String id){
        
        Connection con;
        
        String consulta = "select * from usuarios where nickname like '%" + id + "%' order by nickname ASC";
        
        PreparedStatement stmUsuario = null;
        
        java.util.List<String> users = new java.util.ArrayList<>();
        
        ResultSet rsUsuarios = null;
        
        con = super.getConexion();
        
        try{
            stmUsuario = con.prepareStatement(consulta);
            
            rsUsuarios = stmUsuario.executeQuery();
            
            while(rsUsuarios.next()){
                String a = rsUsuarios.getString("nickname");
                users.add(a);
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
        }finally{
            try{stmUsuario.close();}catch(SQLException ex){System.out.println("Imposible cerrar cursores");}
        }
        
        return users;
    }
}
