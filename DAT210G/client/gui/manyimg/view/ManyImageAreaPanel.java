package gui.manyimg.view;

import gui.manyimg.controller.ManyImgController;
import gui.manyimg.model.Thumbnail;
import gui.manyimg.model.HelperPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by Ronnie on 11.02.14.
 *
 * This only loads images that are visible in viewport to memory.
 *
 * Idea borrowed from blog: http://sahits.ch/blog/?p=1205
 *
 */
public class ManyImageAreaPanel extends JPanel implements AdjustmentListener, ComponentListener {


    private int thumbnailSize = 200;
    private int spacing = 5;
    private double visibleRows;
    private int columns = 3;    //TODO: Kan kanskje brukes for zoom?
    private int rows;

    private BoundedRangeModel brm;
    private ManyImgController manyImgController;



    private BufferedImage[] thumbnails;



    private HelperPanel[] thumbnailPanels;
    private Thumbnail[] thumbnail;


    private GridLayout gridLayout;

    public ManyImageAreaPanel(ManyImgController manyImgController, int[] imageIdArray) {

        this.manyImgController = manyImgController;

        thumbnails = new BufferedImage[imageIdArray.length];
       // icons = new JLabel[thumbnails.length];
        thumbnailPanels = new HelperPanel[thumbnails.length];
        rows = (int) Math.ceil(1.0 * thumbnails.length / columns);

        setBorder(new EmptyBorder(5, 5, 5, 5));

        gridLayout = new GridLayout(rows, columns, spacing, spacing);
        setLayout(gridLayout);

        BufferedImage placeholder = new BufferedImage(thumbnailSize, thumbnailSize, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = (Graphics2D) placeholder.getGraphics();
        g.drawRect(0, 0, placeholder.getWidth(), placeholder.getHeight());
        Icon icon = new ImageIcon(placeholder);
        for (int i = 0; i < thumbnails.length; i++) {

            //icons[i] = new JLabel(icon);
            thumbnailPanels[i] = new HelperPanel(0);
            add(thumbnailPanels[i]);
        }

    }


    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        updateView();
    }

    private void updateView() {
        double percent = 100.0 * brm.getValue() / (brm.getMaximum() - brm.getExtent());
        int firstRow = computeFirstRowIndex(percent / 100);
        int lastRow = computeLastRowIndex(percent/100);
        updateImages(firstRow, lastRow);
    }

    private void updateImages(int firstRow, int lastRow) {

        int firstImageIndex = firstRow * columns;
        int lastImageIndex = Math.min(lastRow * columns + columns - 1, thumbnails.length - 1);

        /*
        for (int i = firstImageIndex; i <= lastImageIndex; i++) {

                BufferedImage thumbnail = manyImgController.getThumbnail(i);
                Graphics2D g = (Graphics2D) thumbnail.getGraphics();
                BufferedImage image = new BufferedImage(thumbnailSize, thumbnailSize, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2d = (Graphics2D) image.getGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                FontRenderContext frc = g2d.getFontRenderContext();
                Font font = g2d.getFont();
                font = font.deriveFont(24);

                String imageText = "BildeID: " + String.valueOf(i);

                GlyphVector gv = font.createGlyphVector(frc, imageText);
                Rectangle2D rect = gv.getVisualBounds();
                int x = (int) ((thumbnailSize - rect.getWidth())/2);
                int y = (int) ((thumbnailSize - rect.getHeight())/2);
                g2d.drawGlyphVector(gv, x, y);
                g.drawImage(image, 0, 0, this);
                icons[i].setIcon(new ImageIcon(thumbnail));

        }
        */

        for (int i = firstImageIndex; i <= lastImageIndex; i++) {

            thumbnailPanels[i].removeAll();
            thumbnailPanels[i].setImageID(i);
            thumbnailPanels[i].add(new Thumbnail(manyImgController.getThumbnail(i),i));



        }
    }

    private int computeLastRowIndex(double percent) {
        double pos = computeTopPosition(percent);
        int result = (int) Math.floor((pos-spacing)/(spacing+thumbnailSize)+visibleRows);
        return Math.min(result, rows);
    }

    private int computeFirstRowIndex(double percentage) {
        double pos = computeTopPosition(percentage);
        int result = (int) Math.floor((pos-spacing)/(spacing+thumbnailSize));
        return Math.max(0, result);
    }

    private double computeTopPosition(double percent) {
		/*
		 * The percentage indicates the scroll position, so 0% means all to
		 * the top, 100% is all the bottom. The top position of the bottom however
		 * is the height of the panel - the height of the viewport (parent). anything
		 * in between is procentual
		 */
        double top = getBounds().height*percent;
        double correction = getParent().getBounds().height*percent;
        return top-correction;
    }

    public void setBrm(BoundedRangeModel brm) {
        this.brm = brm;
    }


    public void componentHidden(ComponentEvent e) { }

    public void componentMoved(ComponentEvent e) { }

    public void componentResized(ComponentEvent e) {
        int rowHeight = spacing+thumbnailSize;
        visibleRows = 1.0 * getParent().getBounds().height / rowHeight;
        updateView(); // make sure the view is properly updated
    }

    public void componentShown(ComponentEvent e) { }



    public HelperPanel[] getThumbnailPanels() {
        return thumbnailPanels;
    }





}
