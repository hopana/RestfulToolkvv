package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

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
public class GlobalParameterSettingAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Designer designer = new Designer();
        designer.setMaximumSize(new Dimension(700,500));
        designer.setMinimumSize(new Dimension(700,500));
        designer.setPreferredSize(new Dimension(700,500));

        VDialog dialog = new VDialog(null,designer,
                new Dimension(700,500),
                new Dimension(700,500),
                new Dimension(700,500),
                IconLoader.getIcon("/icons/setting.png"),
                "全局参数设置"
                );
        dialog.setVisible(true);
//
//        long startTime = System.currentTimeMillis();
//        if(project == null)return;
//        @SystemIndependent String basePath = project.getBasePath();
//        String configFile = basePath + "/RestfulVV.json";
//        File  file = new File(configFile);
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//                RestfulVV vv = new RestfulVV();
//                String s = JsonUtils.toJson(vv);
//                FileUtils.write(file,s,"utf-8");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        List<RestServiceProject> projects = RestServiceProjectsManager.getInstance(project).getServiceProjects();
//        int serviceCount = 0;
////        DefaultMutableTreeNode rootTreeNode = createTreeNode("REST Services");
////        myTreeBuilder.addSubtreeToUpdate(rootTreeNode);
//
//        for (RestServiceProject each : projects) {
//            System.out.println("project  " + each.getModuleName());
//            System.out.println("==================");
//            List<RestServiceItem> serviceItems = each.getServiceItems();
//            serviceCount += serviceItems.size();
//            for (RestServiceItem serviceItem : serviceItems) {
//                System.out.println(serviceItem.getFullUrl() + "\t\t" + serviceItem.getUrl());
//            }
////            RestServiceStructure.ProjectNode node = findNodeFor(each);
////            if (node == null) {
////                node = new RestServiceStructure.ProjectNode(myRoot,each);
////                myProjectToNodeMapping.put(each, node);
////            }
//        }
//        System.out.println("basePath " + basePath + ",spend " + (System.currentTimeMillis() - startTime));
    }
}
