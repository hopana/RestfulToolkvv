package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.github.aloxc.plugin.restfulvv.restful.component.Postman;
import com.github.aloxc.plugin.restfulvv.restful.component.VDialog;
import com.github.aloxc.plugin.restfulvv.restful.navigator.Designer;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * 导入post数据
 */
public class ImportPostmanAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Postman postman = new Postman();
        postman.setMaximumSize(new Dimension(700,150));
        postman.setMinimumSize(new Dimension(700,150));
        postman.setPreferredSize(new Dimension(700,150));
        VDialog dialog = new VDialog(null, postman,
                new Dimension(500,150),
                new Dimension(500,150),
                new Dimension(500,150),
                IconLoader.getIcon("/icons/P.png"),
                "导入Postman数据"
        );
        dialog.setVisible(true);
    }
}
