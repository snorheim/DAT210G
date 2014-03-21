package gui.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ronnie on 12.02.14.
 *
 *
 * Bare en simulering. Legger inn kode her for å snakke med server.
 */
public class ServerCommHandlerTesting {

    

    public int[] getAllImageIds() {

        int[] imageIdArray = new int[40];  // la oss si serveren har 20 bilder

        for (int i = 0; i < imageIdArray.length; i++) {
            imageIdArray[i] = i + 1;
        }

       return imageIdArray;
    }

    public BufferedImage getThumbnail(int imageID) {

        BufferedImage temp;

        System.out.println("Returnerer bilde med id: " + imageID);


        try {
            temp = ImageIO.read(new File("C:\\Users\\Ronnie\\workspace\\DAT210G_Client\\res\\funny-dog-thumb.jpg"));
            Graphics2D g = (Graphics2D) temp.createGraphics();
            g.setColor(Color.RED);
            g.drawString(String.valueOf(imageID), 10, 10); // For å teste ManyImageAreaPanel virker skikkelig


            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BufferedImage getLargeImage(int imageID) {

        BufferedImage temp;

        System.out.println("Returnerer bilde med id: " + imageID);

        try {
            temp = ImageIO.read(new File("C:\\Users\\Ronnie\\workspace\\DAT210G_Client\\res\\funny-dog.jpg"));
            Graphics2D g = (Graphics2D) temp.createGraphics();
            g.setColor(Color.RED);
            g.drawString(String.valueOf(imageID), 10, 10); // For å teste ManyImageAreaPanel virker skikkelig
            
            
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
