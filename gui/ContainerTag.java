/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.FlowLayout;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alumnogreibd
 */
public class ContainerTag extends JPanel{
    
    private JLabel etiqueta;
    private Container contenedor;
    private String textoEtiqueta;
    
    public ContainerTag(Container contenedor)
    {
        this.contenedor = contenedor;
        
        initComponents();
        setupComponents();
        layoutComponents();
    }
    
    public ContainerTag(Container contenedor, String pathIcono)
    {
        this.contenedor = contenedor;
        
        initComponents(pathIcono);
        setupComponents();
        layoutComponents();
    }
    
    private void initComponents()
    {
        this.textoEtiqueta = this.contenedor.xerarEtiqueta();
        
        this.etiqueta = new JLabel(this.textoEtiqueta, JLabel.LEFT);
    }
    
    private void initComponents(String pathIcono)
    {
        this.textoEtiqueta = this.contenedor.xerarEtiqueta();
        
        ImageIcon tempIcon;
        
        try
        {
            tempIcon = new ImageIcon(ImageIO.read(getClass().getResource(pathIcono)));
            this.etiqueta = new JLabel(this.textoEtiqueta, tempIcon, JLabel.LEFT);
        }
        catch(IOException | IllegalArgumentException io)
        {
            this.etiqueta = new JLabel(this.textoEtiqueta, JLabel.LEFT);
        }
    }
    
    private void setupComponents()
    {
        this.add(this.etiqueta);
    }
    
    private void layoutComponents()
    {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public Container getContenedor() {
        return this.contenedor;
    }
    
    
}
