/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database;


import app.Audio;
import app.Company;
import app.Party;
import app.Image;
import app.Particular;
import app.SocialNetwork;
import app.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author basesdatos
 */
public class DatabaseFacade {
    private app.FachadaAplicacion fa;
    private java.sql.Connection conexion;

    private DAOUsers daoUsuarios;
    private DAOImages daoImagen;
    private DAOComunication daoComunicacion;
    private DAOSocialNetworks daoRedes;
    private DAOGalleries daoGalerias;
    private DAOAccess daoAccesos;
    private DAOPosts daoPosts;
    private DAOAudio daoAudio;
    private DAOParties daoFestas;
    
    public DatabaseFacade (app.FachadaAplicacion fa){
        
        Properties configuracion = new Properties();
        this.fa=fa;
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();
     

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion=java.sql.DriverManager.getConnection("jdbc:"+gestor+"://"+
                    configuracion.getProperty("servidor")+":"+
                    configuracion.getProperty("puerto")+"/"+
                    configuracion.getProperty("baseDatos"),
                    usuario);
          
            this.daoUsuarios = new DAOUsers(conexion,fa);
            this.daoImagen = new DAOImages(conexion,fa);
            this.daoComunicacion=new DAOComunication(conexion,fa);
            this.daoRedes = new DAOSocialNetworks(conexion,fa);
            this.daoGalerias = new DAOGalleries(conexion,fa);
            this.daoAccesos = new DAOAccess(conexion,fa);         
            this.daoPosts = new DAOPosts(conexion,fa);
            this.daoAudio = new DAOAudio(conexion,fa);
            this.daoFestas = new DAOParties(conexion,fa);

        } catch (FileNotFoundException f){
            System.out.println(f.getMessage());
            fa.muestraExcepcion(f.getMessage());
        } catch (IOException i){
            System.out.println(i.getMessage());
            fa.muestraExcepcion(i.getMessage());
        } 
        catch (java.sql.SQLException e){
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());
        }
        
           
    } 

    public User validarUsuario(String idUsuario, String clave){
        return daoUsuarios.validarUsuario(idUsuario,clave);
    }
    
    public float darBajaUsuario(String id){
       return this.daoUsuarios.borrarUsuario(id);
    }
    
    public void insertarAcceso(String id){
        this.daoAccesos.insertarAcceso(id);
    }
    
    public void acabarAcceso(String id){
        this.daoAccesos.acabarAcceso(id);
    }
    
    public Boolean insertarUsuario(User u){
        return daoUsuarios.insertarUsuario(u);
    }
    
    
    public void updateUsuario(User u) {
        if(u instanceof Particular){
            daoUsuarios.updateParticular((Particular)u);
        }
        else if(u instanceof Company){
            daoUsuarios.updateEmpresa((Company)u);
        }
        else{
            this.fa.getFachadaGui().muestraExcepcion("Erro: Debe seleccionar o tipo de usuario");
        }
    }
    
    public String consultarContrasinal(String email){
        return daoUsuarios.consultarContrasinal(email);
    }
    
    public void updateContrasinal(User u, String contrasinal) {
        daoUsuarios.updateContrasinal(u,contrasinal);
    }
    
    public void updateContrasinal(String email, String contrasinal) {
        daoUsuarios.updateContrasinal(email,contrasinal);
    }
    
    public float consultarSaldo(String id){
        return daoUsuarios.consultarSaldo(id);
    }
    
    public void updateSaldo(float dinero,String id){
        daoUsuarios.updateSaldo(dinero,id);
    }

    public java.util.List<String> buscarUsuario(String id){
        return this.daoUsuarios.buscarUsuario(id);
    }
    
    public User seleccionarUsuario(String id){
        return this.daoUsuarios.seleccionarUsuario(id);
    }
    
    public java.util.ArrayList<app.Image> consultarImagenesGaleria(int festa,String galeria){
        return daoImagen.consultarImagenesGaleria(festa,galeria);
    }
    
    public void insertarMensaje(app.Message m){
        daoComunicacion.insertarMensaje(m);
    }
    
    public void borrarMensaje(app.Message m){
        daoComunicacion.borrarMensaje(m);
    }
    
    public java.util.List<app.Message> consultarMensajes(String emisor, String receptor){
        return this.daoComunicacion.consultarMensajes(emisor, receptor);
    }

    public boolean subirImagen(Image imagen,String pie) {
        return daoImagen.subirImagen(imagen,pie);
    }

    public SocialNetwork consultarRedSocial(String idUsuario, String red){
        return this.daoRedes.consultarRedSocial(idUsuario,red);
    }
    
    public java.util.ArrayList<SocialNetwork> consultarRedes(String usuario){
        return daoRedes.consultarRedes(usuario);
    }
    
    public void insertarRedSocial(SocialNetwork red){
        this.daoRedes.insertarRedSocial(red);
    } 

    public void EliminarRedSocial(String plataforma, String id){
        this.daoRedes.EliminarRedSocial(plataforma, id);
    }
    
    public void insertarGaleria(int idFesta, String galeria){
        this.daoGalerias.insertarGaleria(idFesta,galeria);
    }
    
    public void ComentarPost(app.WallPost post){
        this.daoPosts.ComentarMuro(post);
    }
    
    public void borrarPost(String u, int festa, java.sql.Timestamp ts){
        this.daoPosts.borrarPost(u, festa, ts);
    }

    public java.util.List<app.WallPost> consultarPosts(int festa){
        return this.daoPosts.consultarPosts(festa);
    }
    
    public ArrayList<Audio> consultarAudiosGaleria(int festa, String galeria) {
        return this.daoAudio.consultarAudiosGaleria(festa,galeria);
    }

    public boolean subirAudio(Audio audio,String pie) {
        return this.daoAudio.subirAudio(audio,pie);
    }

    public java.util.List<String> consultarGalerias(int festa) {
        return this.daoGalerias.consultarGalerias(festa);
    }

    public void eliminarGaleria(int festa, String galeria) {
        this.daoGalerias.eliminarGaleria(festa, galeria);
    }

    public void eliminarImagen(Image imagen) {
        this.daoImagen.eliminarImagen(imagen);
    }
    
    public void eliminarAudio(Audio audio){
        this.daoAudio.eliminarAudio(audio);
    }

    
    public String consultarOrganizador(int idFesta) {
        return this.daoFestas.consultarOrganizador(idFesta);
    }


    public void actualizarFotoPerfil(String url,String id){
        this.daoUsuarios.updateUrlPerfil(url, id);
    }

    public List<Party> buscarFestas(String usuarioSesion, String nomeFesta, String nomeOrganizador, String tematica, String privacidade, Date data) {
        return this.daoFestas.buscarFestas(usuarioSesion,nomeFesta,nomeOrganizador,tematica,privacidade,data);
    }

    public List<Party> buscarFPorganizador(String idUsuario,String nomeFesta) {
        return this.daoFestas.buscarFPorganizador(idUsuario,nomeFesta);
    }
    
    public List<Party> buscarFPparticipante(String idUsuario,String nomeFesta) {
        return this.daoFestas.buscarFPparticipante(idUsuario,nomeFesta);
    }
    
    public void deixarFesta(int festa, String nickname){
        this.daoFestas.deixarFesta(festa,nickname);
    }

    public java.util.List<String> consultarUsuariosFestaPrivada(int idFesta){
        return this.daoFestas.consultarUsuariosFestaPrivada(idFesta);
    }
    
    public java.util.List<String> consultarUsuariosFestaPublica(int idFesta){
        return this.daoFestas.consultarUsuariosFestaPublica(idFesta);
    }
    
    public void silenciarUsuario(String usuario,int festa){
        this.daoPosts.silenciarUsuario(usuario, festa);
    }
   
    public void desilenciarUsuario(String usuario, int festa){
        this.daoPosts.desilenciarUsuario(usuario, festa);
    }
    
}

  