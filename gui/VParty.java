/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author alumnogreibd
 */
public class VParty extends javax.swing.JDialog {

    /**
     * Creates new form VParty
     */
    private app.FachadaAplicacion fa;
    private int idFesta;
    private boolean esOrganizador; //Necesario para saber si el usuario es el organizador de la fiesta
    private String[] galeriasArr;

    public VParty(java.awt.Frame parent, boolean modal, app.FachadaAplicacion fa, int id, java.util.List<app.WallPost> posts, boolean esOrganizador, String esPrivada) {
        super(parent, modal);
        this.fa = fa;
        this.idFesta = id;
        this.esOrganizador = esOrganizador;
        this.consultarGalerias();
        BackgroundPanel panelFondo = new BackgroundPanel("/resources/background.png");
        setContentPane(panelFondo);
        initComponents();
        this.setLocationRelativeTo(null);
        PartyWallTableModel mFest = new PartyWallTableModel();
        this.tablaPosts.setModel(mFest);
        mFest.setFilas(posts);

        StringsListModel mAsist = new StringsListModel();
        this.lstAsistentes.setModel(mAsist);
        if (esPrivada.equalsIgnoreCase("privada")) {
            mAsist.setElementos(this.fa.consultarUsuariosFestaPrivada(idFesta));
        } else if (esPrivada.equalsIgnoreCase("publica")) {
            mAsist.setElementos(this.fa.consultarUsuariosFestaPublica(idFesta));
        }

        JPopupMenu popupTable = new JPopupMenu();
        JMenuItem borrar = new JMenuItem("Borrar mensaxe");
        popupTable.add(borrar);
        tablaPosts.setComponentPopupMenu(popupTable);

        borrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fa.getUsuarioActual().getId().equals(tablaPosts.getValueAt(tablaPosts.getSelectedRow(), 0)) || esOrganizador) {
                    if(tablaPosts.getSelectedRow() != -1){
                        fa.borrarPost((String) tablaPosts.getValueAt(tablaPosts.getSelectedRow(), 0), idFesta, (java.sql.Timestamp) tablaPosts.getValueAt(tablaPosts.getSelectedRow(), 1));

                        PartyWallTableModel mFest = (PartyWallTableModel) tablaPosts.getModel();
                        mFest.setFilas(fa.consultarPosts(idFesta)); 
                    }
                } else {
                    fa.muestraExcepcion("Non tes permiso para borrar esta mensaxe");
                }
            }
        });

        if (esOrganizador) {

            JMenuItem silenciar2 = new JMenuItem("Silenciar usuario");
            JMenuItem desilenciar2 = new JMenuItem("Desilenciar usuario");
            popupTable.add(silenciar2);
            popupTable.add(desilenciar2);

            JPopupMenu popupSilenciar = new JPopupMenu();
            JMenuItem silenciar = new JMenuItem("Silenciar usuario");
            JMenuItem desilenciar = new JMenuItem("Desilenciar usuario");
            popupSilenciar.add(silenciar);
            popupSilenciar.add(desilenciar);
            lstAsistentes.setComponentPopupMenu(popupSilenciar);

            silenciar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(lstAsistentes.getSelectedIndex() != -1){
                        fa.silenciarUsuario((String) lstAsistentes.getSelectedValue(), idFesta);
                    }
                }
            });

            silenciar2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (tablaPosts.getSelectedRow() != -1) {
                        fa.silenciarUsuario((String) tablaPosts.getValueAt(tablaPosts.getSelectedRow(), 0), idFesta);
                    }
                }
            });

            desilenciar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (lstAsistentes.getSelectedIndex() != -1) {
                        fa.desilenciarUsuario((String) lstAsistentes.getSelectedValue(), idFesta);
                    }
                }
            });

            desilenciar2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(tablaPosts.getSelectedRow() != -1){
                        fa.desilenciarUsuario((String) tablaPosts.getValueAt(tablaPosts.getSelectedRow(), 0), idFesta);
                    }
                }
            });
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPosts = new javax.swing.JTable();
        Comentario = new javax.swing.JTextField();
        btnPost = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnUbicacion = new javax.swing.JButton();
        cbGalerias = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        btnCrearGaleria = new javax.swing.JButton();
        btnVerGalerias = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstAsistentes = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaPosts.setModel(new gui.PartyWallTableModel());
        jScrollPane1.setViewportView(tablaPosts);

        btnPost.setText("POST");
        btnPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("NOME DA FESTA");

        btnUbicacion.setText("Ubicación");

        cbGalerias.setModel(new javax.swing.DefaultComboBoxModel<>(galeriasArr));
        cbGalerias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGaleriasActionPerformed(evt);
            }
        });

        jLabel2.setText("Galerias");

        btnCrearGaleria.setText("Crear Galería");
        btnCrearGaleria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearGaleriaActionPerformed(evt);
            }
        });

        btnVerGalerias.setText("Ver");
        btnVerGalerias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerGaleriasActionPerformed(evt);
            }
        });

        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/backArrow.png"))); // NOI18N
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        jLabel3.setText("ASISTENTES");

        lstAsistentes.setModel(new gui.StringsListModel());
        jScrollPane2.setViewportView(lstAsistentes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Comentario, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPost, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cbGalerias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(11, 11, 11)
                                    .addComponent(btnVerGalerias))
                                .addComponent(btnCrearGaleria, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(btnUbicacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(9, 9, 9)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUbicacion)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbGalerias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVerGalerias))
                        .addGap(12, 12, 12)
                        .addComponent(btnCrearGaleria))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Comentario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPost))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostActionPerformed
        //TODO add your handling code here:
        if (Comentario.getText() != null) {
            app.WallPost post = new app.WallPost(this.fa.getUsuarioActual().getId(), Comentario.getText(), null, this.idFesta);
            fa.ComentarMuro(post);

            PartyWallTableModel mFest = (PartyWallTableModel) this.tablaPosts.getModel();
            mFest.setFilas(fa.consultarPosts(idFesta));
        }

        this.Comentario.setText(null);

    }//GEN-LAST:event_btnPostActionPerformed

    private void btnCrearGaleriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearGaleriaActionPerformed
        // TODO add your handling code here:
        if (esOrganizador) {
            String galeria = JOptionPane.showInputDialog("Introduzca el nombre de la galería");
            this.fa.crearGaleria(this.idFesta, galeria);
            //Actualizamos el listado de galerias de la fiesta
            this.consultarGalerias();
            cbGalerias.setModel(new javax.swing.DefaultComboBoxModel<>(galeriasArr));
        } else {
            this.fa.muestraExcepcion("Solo el organizador de una fiesta puede crear una galeria");
            this.btnCrearGaleria.setEnabled(false);
        }
    }//GEN-LAST:event_btnCrearGaleriaActionPerformed

    private void cbGaleriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGaleriasActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbGaleriasActionPerformed

    private void btnVerGaleriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerGaleriasActionPerformed
        // TODO add your handling code here:
        if(cbGalerias.getItemCount()>0){
            VGallery vg = new VGallery(this.fa.getFachadaGui().getVPrincipal(), true, this.fa, this.idFesta, this.cbGalerias.getSelectedItem().toString(), this.esOrganizador);
            vg.setVisible(true);
        }
        else{
            this.fa.muestraExcepcion("Esta festa non ten ainda ningunha galeria asociada");
        }
    }//GEN-LAST:event_btnVerGaleriasActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Comentario;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnCrearGaleria;
    private javax.swing.JButton btnPost;
    private javax.swing.JButton btnUbicacion;
    private javax.swing.JButton btnVerGalerias;
    private javax.swing.JComboBox cbGalerias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstAsistentes;
    private javax.swing.JTable tablaPosts;
    // End of variables declaration//GEN-END:variables

        private void consultarGalerias() {
        java.util.List<String> galerias = (java.util.ArrayList) this.fa.consultarGalerias(this.idFesta);
        galeriasArr = new String[galerias.size()];
        galeriasArr = galerias.toArray(galeriasArr);       
    }
}
