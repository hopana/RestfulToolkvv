package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.github.aloxc.plugin.restfulvv.restful.component.Setting;
import com.github.aloxc.plugin.restfulvv.restful.component.VDialog;
import com.github.aloxc.plugin.restfulvv.restful.navigator.Designer;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * 全局參數設置
 * @author liyh
 */
public class SettingAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Setting setting = new Setting();
        setting.setMaximumSize(new Dimension(700,500));
        setting.setMinimumSize(new Dimension(700,500));
        setting.setPreferredSize(new Dimension(700,500));

        VDialog dialog = new VDialog(null, setting,
                new Dimension(700,500),
                new Dimension(700,500),
                new Dimension(700,500),
                IconLoader.getIcon("/icons/setting.png"),
                "全局参数设置"
                );
        dialog.setVisible(true);
    }
}
