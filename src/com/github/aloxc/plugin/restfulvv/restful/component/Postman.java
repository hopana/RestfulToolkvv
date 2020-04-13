package com.github.aloxc.plugin.restfulvv.restful.component;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBPanel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;

public class Postman extends JBPanel {
    private JFileChooser fileChooser;
    private JLabel label;
    private JPanel rootPanel;
    private JPanel container;

    protected void printComponent(Graphics g) {
        super.printComponent(g);
    }

    public Postman(){
        super();
        initComponent();
    }

    private void initComponent() {
        initUI();
    }


    private void initUI() {
        FlowLayout layoutManager = new FlowLayout();
//        layoutManager.setHGap(1);
//        layoutManager.setVGap(1);

        container = new JBPanel<>();
        container.setLayout(layoutManager);
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("json"));

        container.add(label,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(-1, 28), new Dimension(-1, 28), new Dimension(-1, 28)));
        container.add(fileChooser,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(-1, 28), new Dimension(-1, 28), new Dimension(-1, 28)));
        this.add(container);
    }

}
