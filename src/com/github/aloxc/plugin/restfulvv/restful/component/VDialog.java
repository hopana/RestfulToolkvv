package com.github.aloxc.plugin.restfulvv.restful.component;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 渲染一个dialog出来
 * @author liyh
 */
public class VDialog extends JDialog {
    /**
     *
     * @param parent 父窗体
     * @param root dialog中显示的窗体
     * @param minimumSize dialog最小尺寸
     * @param maximumSize dialog最大尺寸
     * @param preferredSize dialog初始化尺寸
     * @param icon dialog图标
     */
    public VDialog(Frame parent,@NotNull JBPanel root,Dimension minimumSize,Dimension maximumSize,Dimension preferredSize,@NotNull Icon icon,@NotNull String title){
        super(parent, true);
        setLayout(new FlowLayout());
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        getContentPane().add(root, BorderLayout.NORTH);
        setMaximumSize(maximumSize);
        setMinimumSize(minimumSize);
        setPreferredSize(preferredSize);
        //设置窗体属性
        setTitle(title);
        setIconImage(IconLoader.toImage(icon));
        setLocationRelativeTo(root);
        setResizable(false);
        pack();
    }
}