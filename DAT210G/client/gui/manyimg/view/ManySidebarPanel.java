package gui.manyimg.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Ronnie on 11.02.14.
 */
public class ManySidebarPanel extends JPanel {

    private JPanel panel;

    private JTextField ratingJTextField;
    private JTextField nameJTextField;
    private JTextField tagsJTextField;
    private JTextField dateJTextField;
    private JTextField etcJTextField;
    private JButton searchBtn;
    private JButton importBtn;

    public ManySidebarPanel() {

        panel = new JPanel();

        panel.setLayout(new GridBagLayout());

        ratingJTextField = new JTextField("Rating", 10);
        nameJTextField = new JTextField("Name", 10);
        tagsJTextField = new JTextField("Tags", 10);
        dateJTextField = new JTextField("Date", 10);
        etcJTextField = new JTextField("Etc", 10);
        searchBtn = new JButton("Search");
        importBtn = new JButton("Import");

        GridBagConstraints a = new GridBagConstraints();
        a.fill = GridBagConstraints.HORIZONTAL;
        a.ipadx = 2;
        a.ipady = 2;
        a.weightx = 0.5;
        a.gridx = 0;
        a.gridy = 0;
        panel.add(new JLabel("Rating"), a);

        GridBagConstraints b = new GridBagConstraints();
        b.fill = GridBagConstraints.HORIZONTAL;
        b.ipadx = 2;
        b.ipady = 2;
        b.weightx = 0.5;
        b.gridx = 1;
        b.gridy = 0;
        panel.add(ratingJTextField, b);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 2;
        c.ipady = 2;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Name"), c);

        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.HORIZONTAL;
        d.ipadx = 2;
        d.ipady = 2;
        d.weightx = 0.5;
        d.gridx = 1;
        d.gridy = 1;
        panel.add(nameJTextField, d);

        GridBagConstraints e = new GridBagConstraints();
        e.fill = GridBagConstraints.HORIZONTAL;
        e.ipadx = 2;
        e.ipady = 2;
        e.weightx = 0.5;
        e.gridx = 0;
        e.gridy = 2;
        panel.add(new JLabel("Tags"), e);

        GridBagConstraints f = new GridBagConstraints();
        f.fill = GridBagConstraints.HORIZONTAL;
        f.ipadx = 2;
        f.ipady = 2;
        f.weightx = 0.5;
        f.gridx = 1;
        f.gridy = 2;
        panel.add(tagsJTextField, f);

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.ipadx = 2;
        g.ipady = 2;
        g.weightx = 0.5;
        g.gridx = 0;
        g.gridy = 3;
        panel.add(new JLabel("Date"), g);

        GridBagConstraints h = new GridBagConstraints();
        h.fill = GridBagConstraints.HORIZONTAL;
        h.ipadx = 2;
        h.ipady = 2;
        h.weightx = 0.5;
        h.gridx = 1;
        h.gridy = 3;
        panel.add(dateJTextField, h);

        GridBagConstraints i = new GridBagConstraints();
        i.fill = GridBagConstraints.HORIZONTAL;
        i.ipadx = 2;
        i.ipady = 2;
        i.weightx = 0.5;
        i.gridx = 0;
        i.gridy = 4;
        panel.add(new JLabel("Etc"), i);

        GridBagConstraints j = new GridBagConstraints();
        j.fill = GridBagConstraints.HORIZONTAL;
        j.ipadx = 2;
        j.ipady = 2;
        j.weightx = 0.5;
        j.gridx = 1;
        j.gridy = 4;
        panel.add(etcJTextField, j);

        GridBagConstraints k = new GridBagConstraints();
        k.fill = GridBagConstraints.HORIZONTAL;
        k.ipadx = 2;
        k.ipady = 5;
        k.weightx = 1.0;
        k.anchor = GridBagConstraints.PAGE_END;
        k.gridx = 0;
        k.gridy = 5;
        k.gridwidth = 2;
        panel.add(searchBtn, k);

        GridBagConstraints l = new GridBagConstraints();
        l.fill = GridBagConstraints.HORIZONTAL;
        l.ipadx = 2;
        l.ipady = 5;
        l.weightx = 0.0;
        l.gridx = 0;
        l.gridy = 6;
        l.gridwidth = 2;
        panel.add(importBtn, l);

        add(panel);

    }

    public JFileChooser openFileChooser() {
        final JFileChooser fc = new JFileChooser();

        fc.setMultiSelectionEnabled(true);
        fc.setFileHidingEnabled(true);

        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc;
        } else {
            return null;
        }
    }

    public void addImportBtnListener (ActionListener listenForImportBtn) {
        importBtn.addActionListener(listenForImportBtn);
    }

    public void addSearchBtnListener (ActionListener listenForSearchBtn) {
        searchBtn.addActionListener(listenForSearchBtn);
    }

}
