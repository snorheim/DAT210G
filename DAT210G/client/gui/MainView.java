package gui;


import gui.manyimg.view.ManyImageAreaPanel;
import gui.manyimg.view.ManySidebarPanel;
import gui.singleimg.view.SingleImageAreaPanel;
import gui.singleimg.view.SingleSidebarPanel;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Ronnie on 11.02.14.
 */
public class MainView extends JFrame {

    private Dimension windowSize;
    private int windowWidth;
    private int windowHeight;

    private JPanel contentPane;
    private JPanel sidebarPanel;
    private JPanel imageAreaPanel;


    public MainView(JPanel sidebarPanel, JPanel imageAreaPanel, Dimension screenSize) {

        contentPane = new JPanel(new BorderLayout());

        this.setContentPane(contentPane);


        windowWidth = screenSize.width / 2;
        windowHeight = screenSize.height / 2;
        windowSize = new Dimension(windowWidth, windowHeight);
        this.setPreferredSize(windowSize);

        this.setTitle("Prosjekt");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setManyImageMode((ManySidebarPanel) sidebarPanel, (ManyImageAreaPanel) imageAreaPanel);



        this.pack();

        this.setVisible(true);


    }

    public void setSingleImageMode(SingleSidebarPanel sidebarPanel, SingleImageAreaPanel imageAreaPanel) {

        contentPane.removeAll();

        this.sidebarPanel = sidebarPanel;
        this.imageAreaPanel = imageAreaPanel;

        this.imageAreaPanel.setBackground(Color.RED);

        contentPane.add(sidebarPanel, BorderLayout.WEST);
        contentPane.add(imageAreaPanel, BorderLayout.CENTER);



    }

    public void setManyImageMode(ManySidebarPanel sidebarPanel, ManyImageAreaPanel imageAreaPanel) {

        contentPane.removeAll();

        this.sidebarPanel = sidebarPanel;
        this.imageAreaPanel = imageAreaPanel;
        this.addComponentListener(imageAreaPanel);
        JScrollPane scrollPane = new JScrollPane(imageAreaPanel);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        BoundedRangeModel brm = verticalScrollBar.getModel();
        imageAreaPanel.setBrm(brm);
        verticalScrollBar.addAdjustmentListener(imageAreaPanel);
        contentPane.add(sidebarPanel, BorderLayout.WEST);
        contentPane.add(scrollPane, BorderLayout.CENTER);

    }



}
