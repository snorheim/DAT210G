package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ronnie on 11.02.14.
 */
public class MainController {

    private ManyImgController manyImgController;
    private SingleImgController singleImgController;
    private ImageHandler imageHandler;

    private MainView mainView;

    private Dimension screenSize;

    public MainController() {

        imageHandler = new ImageHandler();

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        manyImgController = new ManyImgController(this);
        singleImgController = new SingleImgController(this);

        mainView = new MainView(manyImgController.getSidebarPanel(), manyImgController.getImageAreaPanel(), screenSize);






    }

    public void setSingleImageMode(int imageID) {

        BufferedImage temp = imageHandler.getLargeImage(imageID);

        singleImgController.setImage(temp);

        mainView.setSingleImageMode(singleImgController.getSidebarPanel(), singleImgController.getImageAreaPanel());


    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    public ImageHandler getImageHandler() {
        return imageHandler;
    }

    
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainController();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }


}
