/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author alumnogreibd
 */
public class BGPanel extends JPanel{
 
    private Image backgroundImage;

    public BGPanel(String imagePath)
    {
        super();
        
        this.setBackground(imagePath);
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
        catch(IOException io)
        {
            this.backgroundImage = null;
        }
        
        repaint();
    }
}

