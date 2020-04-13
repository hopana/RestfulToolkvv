package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.ComponentUtil;
import com.intellij.util.ui.UIUtil;
import net.sf.cglib.asm.$AnnotationVisitor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * 语言切换
 * @author liyh
 */
public class LanguageAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        JPopupMenu menu = new JPopupMenu();        //创建保存菜单
        JMenuItem save = new JMenuItem("中文");//保存
        save.setIcon(IconLoader.getIcon("/icons/language.png"));
        JMenuItem saveAs = new JMenuItem("英文");//另存为
        saveAs.setIcon(IconLoader.getIcon("/icons/language.png"));
        menu.add(save);
        menu.add(saveAs);
        save.addActionListener(evt -> {
            Messages.showMessageDialog("点击了" + ((JMenuItem) evt.getSource()).getText(), "保存行为", IconLoader.getIcon("/icons/save.png"));
        });
        saveAs.addActionListener(evt -> {
            Messages.showMessageDialog("点击了" + ((JMenuItem) evt.getSource()).getText(), "保存行为", IconLoader.getIcon("/icons/save.png"));
        });

    }
}
