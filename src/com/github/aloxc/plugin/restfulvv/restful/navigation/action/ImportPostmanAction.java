package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

/**
 * 导入post数据
 */
public class ImportPostmanAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Messages.showMessageDialog("import data from postman ！","Import Postman Data", IconLoader.getIcon("/images/timg.jpg"));
    }
}
