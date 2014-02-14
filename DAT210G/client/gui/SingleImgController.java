package gui;

import java.awt.image.BufferedImage;

/**
 * Created by Ronnie on 11.02.14.
 */
public class SingleImgController {

    private MainController mainController;
    private SingleSidebarPanel singleSidebarPanel;
    private SingleImageAreaPanel singleImageAreaPanel;

    public SingleImgController(MainController mainController) {

        this.mainController = mainController;


        singleSidebarPanel = new SingleSidebarPanel();

    }

    public void setImage(BufferedImage image) {
        singleImageAreaPanel = new SingleImageAreaPanel(image);
    }

    public SingleSidebarPanel getSidebarPanel() {
        return singleSidebarPanel;
    }

    public SingleImageAreaPanel getImageAreaPanel() {
        return singleImageAreaPanel;
    }
}
