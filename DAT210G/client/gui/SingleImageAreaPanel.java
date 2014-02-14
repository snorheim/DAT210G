package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Created by Ronnie on 11.02.14.
 */
public class SingleImageAreaPanel extends JPanel {

    private JScrollPane scroll;
    private BufferedImage image;

    public SingleImageAreaPanel(BufferedImage image) {

        //this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

    System.out.println("hallo er bildet oppe?");
    add(new JLabel("hallo er bildet oppe?"));


    }
/*
    public void paint (Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        g.drawImage(image, 0, 0, this);

    }
*/
}
