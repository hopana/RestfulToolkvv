package com.github.aloxc.plugin.restfultookvv.restful.navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.SystemIndependent;

import java.io.File;

/**
 * 全局參數設置
 * @author liyh
 */
public class GlobalParameterSettingAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if(project == null)return;
        @SystemIndependent String basePath = project.getBasePath();
        String configFile = basePath + "/RestfulTookvv.json";
        File  file = new File(configFile);
        if(!file.exists()){

        }
        System.out.println("basePath " + basePath);
    }
}
