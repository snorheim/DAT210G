package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by Ronnie on 14.02.14.
 */
public class Thumbnail extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int imageID;
    private BufferedImage image;
    private Dimension panelSize;
    private Boolean drawMe = false;

    public Thumbnail() {


    }

    public Thumbnail(BufferedImage image, int imageID) {
        drawMe = true;
        this.image = image;
        this.imageID = imageID;
        this.panelSize = new Dimension(image.getWidth(), image.getHeight());
        this.setMinimumSize(panelSize);
        this.setPreferredSize(panelSize);

    }

    public void paint (Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        if (drawMe) {
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.RED);

        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.drawString(Integer.toString(imageID), image.getWidth()/2, image.getHeight()/2);
        }
        g.drawString("null", 50, 50);

    }

    public void addImageListener(MouseListener listenForImageClick){

        this.addMouseListener(listenForImageClick);

    }

    public int getImageID() {
        return imageID;
    }

    public BufferedImage getImage() {
        return image;
    }
}
