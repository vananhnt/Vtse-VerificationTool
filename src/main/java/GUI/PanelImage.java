package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class PanelImage extends JPanel {
    Image img;
    public PanelImage() throws MalformedURLException {

    }
    protected void paintComponent(Graphics g)
    {
        int width = this.getWidth();
        //paint background image
        super.paintComponent(g);
        g.drawImage(img.getScaledInstance(width, -1, Image. SCALE_SMOOTH), 0, 0, this);

    }

}