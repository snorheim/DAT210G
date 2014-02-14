package gui;

import javax.swing.*;

/**
 * Created by Ronnie on 14.02.14.
 */
public class HelperPanel extends JPanel {

    private int imageID;

    public HelperPanel(int imageId) {
        this.imageID = imageId;
    }

    public int getImageID() {
        return  imageID;
    }

    public void setImageID(int id) {
        this.imageID = id;
    }
}
