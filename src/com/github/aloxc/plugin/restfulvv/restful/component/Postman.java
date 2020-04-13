package com.github.aloxc.plugin.restfulvv.restful.component;

import com.intellij.ui.components.JBPanel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

public class Postman extends JBPanel {
    private JPanel rootPanel;
    public JTextField fileAddress;
    public JPanel postmanPanel;
    public JButton fileButton;
    private JLabel chooseLabel;
    private JPanel requestPane;
    private JFileChooser fileChooser = new JFileChooser();

    protected void printComponent(Graphics g) {
        super.printComponent(g);
    }

    public Postman(){
        super();
        initComponent();
    }

    private void initComponent() {
        initUI();
        initAction();
    }

    private void initAction() {
        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("json file（json）", "JSON");
                fileChooser.setFileFilter(filter);
                int i = fileChooser.showOpenDialog(Postman.this);// 显示文件选择对话框
                // 判断用户单击的是否为“打开”按钮
                if (i == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();// 获得选中的文件对象
                    fileAddress.setText(selectedFile.getAbsolutePath());// 显示选中文件的名称
                }
            }
        });
    }


    private void initUI() {

        fileAddress.setAutoscrolls(true);
        postmanPanel = new JBPanel();
        GridLayoutManager mgr = new GridLayoutManager(2, 2);
        mgr.setHGap(1);
        mgr.setVGap(0);
        mgr.setMargin(new Insets(20,20,20,20));
        postmanPanel.setLayout(mgr);
        Dimension dimension = new Dimension(-1,28);
        postmanPanel.add(chooseLabel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        dimension, dimension, dimension));
        dimension = new Dimension(-1,28);

        postmanPanel.add(fileAddress,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        dimension, dimension, dimension));
        dimension = new Dimension(50,28);
        postmanPanel.add(fileButton,
                new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        dimension, dimension, dimension));
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new GridLayoutManager(2, 1));
        this.add(postmanPanel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
    }

}
