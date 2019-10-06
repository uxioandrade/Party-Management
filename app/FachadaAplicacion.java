/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author alumnogreibd
 */
public class FachadaAplicacion {
    gui.GuiFacade fgui;
    database.DatabaseFacade fbd;
    UsersHandler cu;
    ImageHandler ci;
    AudioHandler cad;
    SocialNetworksHandler cr;
    GalleryHandler cg;
    AccessHandler ca;
    WallHandler cm;
    PartyHandler cf;
    private User usuarioActual;
    
    public FachadaAplicacion(){
        fgui=new gui.GuiFacade(this);
        fbd = new database.DatabaseFacade(this);
        cu = new UsersHandler(fgui, fbd);
        ci = new ImageHandler(fgui,fbd);
        cad = new AudioHandler(fgui,fbd);
        cr = new SocialNetworksHandler(fgui,fbd);
        cg = new GalleryHandler(fgui,fbd);
        ca = new AccessHandler(fgui,fbd);
        cm = new WallHandler(fgui,fbd);
        cf = new PartyHandler(fgui,fbd);
    }
    
    public void setUsuarioActual(User u){
        this.usuarioActual = u;
    }
    
    public User getUsuarioActual(){
        return this.usuarioActual;

    }
    
    public static void main(String args[]) {
        FachadaAplicacion fa;
     
        fa= new FachadaAplicacion();
        fa.iniciaInterfazUsuario();           
    }
    

    public void enviarEmailAplicacion(String to,String subject, String text){
        
        String from = "partypisobd@gmail.com";
        String password = "born4sequel";
        String host = "localhost";
        
        Properties props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");
        
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(from,password);
                    }
                });
        //Enviar mensaje
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        }catch(MessagingException mex){mex.printStackTrace();}
    }
    
    public gui.GuiFacade getFachadaGui(){
        return this.fgui;
    }
    
    public void iniciaInterfazUsuario(){
        fgui.iniciaVista();
    }
    
    public void muestraExcepcion(String e){
     fgui.muestraExcepcion(e);

    }
    
    public User comprobarAutentificacion(String idUsuario, String clave){
        return cu.comprobarAutentificacion(idUsuario,clave);
    }
    
    public Boolean darAltaUsuario(User u){
        return cu.darAltaUsuario(u);
    }
    
    public String recuperarContrasinal(String email){
        return cu.recuperarContrasinal(email);
    }
    
    public void actualizarContrasinal(User u,String contrasinal){
        cu.cambiarContrasinal(u,contrasinal);
    }
    
    public void actualizarContrasinal(String email,String contrasinal){
        cu.cambiarContrasinal(email,contrasinal);
    }
    
    public float consultarSaldo(String id){
        return cu.consultarSaldo(id);
    }
    
    public void recargarSaldo(float cantidad){
        cu.recargarSaldo(cantidad,this.usuarioActual.getId());
    }
    
    public SocialNetwork consultarRedSocial(String idUsuario, String red){
        return cr.consultarRedSocial(idUsuario,red);
    }
    
    public java.util.ArrayList<SocialNetwork> consultarRedes(String usuario){
        return cr.consultarRedes(usuario);
    }
    
    public void anhadirRedSocial(SocialNetwork red){
        this.cr.anhadirRedSocial(red);
    }

    public void eliminarRedSocial(String plataforma, String id){
        this.cr.eliminarRedSocial(plataforma, id);
    }
    
    public java.util.ArrayList<Image> consultarImagenesGaleria(int festa, String galeria){
        return ci.consultarImagenesGaleria(festa, galeria);
    }
    
    public boolean subirImagen(Image imagen,String pie) {
        return ci.subirImagen(imagen,pie);
    }
    
     public ArrayList<Audio> consultarAudiosGaleria(int festa, String galeria) {
        return cad.consultarAudiosGaleria(festa, galeria);
    }
     
    public boolean subirAudio(Audio audio,String pie) {
        return cad.subirAudio(audio,pie);
    }
    
    public void crearGaleria(int idFesta, String galeria){
        cg.crearGaleria(idFesta,galeria);
    }
    
    public void nuevoAcceso(String id){
        this.ca.nuevoAcceso(id);
    }
    
    public void acabarAcceso(String id){
        this.ca.acabarAcceso(id);
    }
    public void ComentarMuro(WallPost post){
        cm.ComentarMuro(post);
    }
    
    public void borrarPost(String u, int festa, java.sql.Timestamp ts){
        cm.borrarPost(u, festa, ts);
    }
    
    public java.util.List<WallPost> consultarPosts(int festa){
        return this.cm.consultarPosts(festa);
    }

    public List<String> consultarGalerias(int idFesta) {
        return cg.consultarGalerias(idFesta);
    }

    public void eliminarGaleria(int festa, String galeria) {
        this.cg.eliminarGaleria(festa,galeria);
    }

    public float darBajaUsuario(String id){
        float saldoActual = this.consultarSaldo(id);
        float saldoEntradas = this.cu.darBajaUsuario(id);
        if(saldoEntradas < 0){
            return -1;
        }
        return saldoEntradas + saldoActual;
    }

    public void eliminarImagen(Image imagen) {
        this.ci.eliminarImagen(imagen);
    }

    public void eliminarAudio(Audio audio) {
        this.cad.eliminarAudio(audio);
    }

    public void editarPerfil(User u) {
        this.cu.editarPerfil(u);
    }

    public String consultarOrganizador(int id) {
        return this.cf.consultarOrganizador(id);
    }
   
    public void actualizarFotoPerfil(String url,String id){
        this.cu.actualizarUrlPerfil(url, id);
    }
    
    public void insertarMensaje(app.Message m){
        this.cu.insertarMensaje(m);
    }
    
    public void borrarMensaje(app.Message m){
        this.cu.borrarMensaje(m);
    }
    
    public java.util.List<app.Message> consultarMensajes(String emisor, String receptor){
        return this.cu.consultarMensajes(emisor, receptor);
    }
    
    public java.util.List<String> buscarUsuario(String id){
        return this.cu.buscarUsuario(id);
    }
    
    public User seleccionarUsuario(String id){
        return this.cu.seleccionarUsuario(id);
    }
    
    public java.util.List<Party> buscarFestas (String usuarioSesion, String nomeFesta, String nomeOrganizador, String tematica, String privacidade, Date data){
        return this.cf.buscarFestas(usuarioSesion,nomeFesta,nomeOrganizador,tematica,privacidade,data);
    }

    public List<Party> buscarFPorganizador(String id,String nomeFesta) {
        return this.cf.buscarFPorganizador(id,nomeFesta);
    }
    
    public List<Party> buscarFPparticipante(String id,String nomeFesta) {
        return this.cf.buscarFPparticipante(id,nomeFesta);
    }
    
    public void deixarFesta(int festa, String nickname){
        this.cf.deixarFesta(festa, nickname);
    }
    
    public java.util.List<String> consultarUsuariosFestaPrivada(int idFesta){
        return this.cf.consultarUsuariosFestaPrivada(idFesta);
    }
    
    public java.util.List<String> consultarUsuariosFestaPublica(int idFesta){
        return this.cf.consultarUsuariosFestaPublica(idFesta);
    }
    
    public void silenciarUsuario(String usuario, int festa){
        this.cm.silenciarUsuario(usuario, festa);
    }
    
    public void desilenciarUsuario(String usuario, int festa){
        this.cm.desilenciarUsuario(usuario, festa);
    }
}
