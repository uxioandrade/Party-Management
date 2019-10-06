/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author alumnogreibd
 */
public class BackgroundPanel extends JPanel{
 
    private Image backgroundImage;

    public BackgroundPanel(String imagePath)
    {
        super();
        
        this.setBackground(imagePath);
    }
    
    public BackgroundPanel(URL url)
    {
        super();
        
        this.setBackground(url);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        int width = this.getSize().width;
        int height = this.getSize().height;

        if (this.backgroundImage != null) {
                g.drawImage(this.backgroundImage, 0, 0, width, height, null);
        }

        super.paintComponent(g);
    }

    public void setBackground(String imagePath) {

        // Construimos la imagen y se la asignamos al atributo background.
        this.setOpaque(false);
        try
        {
            this.backgroundImage = (new ImageIcon(ImageIO.read(getClass().getResource(imagePath)))).getImage();
        }
        catch(IOException | IllegalArgumentException io)
        {
            this.backgroundImage = null;
        }
        
        repaint();
    }
    
    public void setBackground(URL url){

        // Construimos la imagen y se la asignamos al atributo background.
        this.setOpaque(false);
        try
        {
            this.backgroundImage = (new ImageIcon(ImageIO.read(url))).getImage();
        }
        catch(IOException io) {System.out.println(io.getMessage());}
        repaint();
    }
}
