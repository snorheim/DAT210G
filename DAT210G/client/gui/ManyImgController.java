package gui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by Ronnie on 11.02.14.
 */
public class ManyImgController {

    private ManyImageAreaPanel imageAreaPanel;
    private ManySidebarPanel sidebarPanel;
    private int[] imageIdArray;

    private MainController mainController;

    public ManyImgController(MainController mainController) {

        imageIdArray = mainController.getImageHandler().getAllImages();

        this.mainController = mainController;
        imageAreaPanel = new ManyImageAreaPanel(this, imageIdArray);
        sidebarPanel = new ManySidebarPanel();
        sidebarPanel.addImportBtnListener(new ImportBtnListener());
        sidebarPanel.addSearchBtnListener(new SearchBtnListener());

        addMouseListenersToImages();


    }

    private void addMouseListenersToImages() {
        for (int i = 0; i < imageAreaPanel.getThumbnailPanels().length; i++) {
            imageAreaPanel.getThumbnailPanels()[i].addMouseListener(new ImageClickListener());
        }
    }

    public BufferedImage getThumbnail(int imageId) {
        return mainController.getImageHandler().getThumbnail(imageId);
    }


    public ManySidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public ManyImageAreaPanel getImageAreaPanel() {
        return imageAreaPanel;
    }

    class ImportBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked importbutton");
            System.out.println(Thread.currentThread().toString());
            JFileChooser fc = sidebarPanel.openFileChooser();

            if (fc == null) {
                System.out.println("User chose no files");
            } else {
                System.out.println(fc.getSelectedFiles().length);
            }
        }
    }

    class SearchBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked searchbutton");
            System.out.println(Thread.currentThread().toString());
        }
    }

    class ImageClickListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

            HelperPanel thumbnail = (HelperPanel) e.getSource();
            System.out.println("Clicked image with ID: " + thumbnail.getImageID());
            mainController.setSingleImageMode(thumbnail.getImageID());


        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            //System.out.println("mouse pressed");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            //System.out.println("mouse released");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            //System.out.println("mouse entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            //System.out.println("mouse exited");
        }

    }
}
