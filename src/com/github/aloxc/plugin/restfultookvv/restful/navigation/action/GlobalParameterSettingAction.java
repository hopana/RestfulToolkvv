package com.github.aloxc.plugin.restfultookvv.restful.navigation.action;

import com.github.aloxc.plugin.restfultookvv.restful.entity.RestfulTookvv;
import com.github.aloxc.plugin.restfultookvv.restful.navigator.RestServiceProject;
import com.github.aloxc.plugin.restfultookvv.restful.navigator.RestServiceProjectsManager;
import com.github.aloxc.plugin.restfultookvv.restful.navigator.RestServiceStructure;
import com.github.aloxc.plugin.restfultookvv.utils.JsonUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.SystemIndependent;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 全局參數設置
 * @author liyh
 */
public class GlobalParameterSettingAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        long startTime = System.currentTimeMillis();
        Project project = event.getProject();
        if(project == null)return;
        @SystemIndependent String basePath = project.getBasePath();
        String configFile = basePath + "/RestfulTookvv.json";
        File  file = new File(configFile);
        if(!file.exists()){
            try {
                file.createNewFile();
                RestfulTookvv vv = new RestfulTookvv();
                String s = JsonUtils.toJson(vv);
                FileUtils.write(file,s,"utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<RestServiceProject> projects = RestServiceProjectsManager.getInstance(project).getServiceProjects();
        int serviceCount = 0;
//        DefaultMutableTreeNode rootTreeNode = createTreeNode("REST Services");
//        myTreeBuilder.addSubtreeToUpdate(rootTreeNode);

        for (RestServiceProject each : projects) {
            System.out.println("project  " + each.getModuleName());
            System.out.println("==================");
            List<RestServiceItem> serviceItems = each.getServiceItems();
            serviceCount += serviceItems.size();
            for (RestServiceItem serviceItem : serviceItems) {
                System.out.println(serviceItem.getFullUrl() + "\t\t" + serviceItem.getUrl());
            }
//            RestServiceStructure.ProjectNode node = findNodeFor(each);
//            if (node == null) {
//                node = new RestServiceStructure.ProjectNode(myRoot,each);
//                myProjectToNodeMapping.put(each, node);
//            }
        }
        System.out.println("basePath " + basePath + ",spend " + (System.currentTimeMillis() - startTime));
    }
}
