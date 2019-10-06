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
public class GuiFacade {
    
    app.FachadaAplicacion fa;
    VPrincipal vp;
    
   public GuiFacade(app.FachadaAplicacion fa){
     this.fa=fa;
     this.vp = new VPrincipal(fa);
   } 
   
   public VPrincipal getVPrincipal(){
       return this.vp;
   }
    
   public void iniciaVista(){
      VAutentication va;
    
      va = new VAutentication(vp, true, fa);
      //VGaleria vg= new VGaleria(vp,true,fa,3,"Galeria de proba");
      vp.setVisible(false);
      va.setVisible(true);
      //vg.setVisible(true);
    }
    
    public void muestraExcepcion(String txtExcepcion){
       VWarning va;
       
       va = new VWarning(vp, true, txtExcepcion);
       va.setVisible(true);
    }
    
}