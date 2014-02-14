package gui;

import javax.swing.*;

/**
 * Created by Ronnie on 11.02.14.
 */
public class SingleSidebarPanel extends JPanel {

    private JButton homeBtn;
    private JButton nextBtn;
    private JButton prevBtn;
    private JButton rotCWBtn;
    private JButton rotCCWBtn;
    private JButton editMetaBtn;

    public SingleSidebarPanel() {

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        homeBtn = new JButton("Home");
        nextBtn = new JButton("Next");
        prevBtn = new JButton("Previous");
        rotCWBtn = new JButton("Rotate Right");
        rotCCWBtn = new JButton("Rotate Left");
        editMetaBtn = new JButton("Edit Metadata");

        add(homeBtn);
        add(nextBtn);
        add(prevBtn);
        add(rotCCWBtn);
        add(rotCWBtn);
        add(editMetaBtn);

    }


}
