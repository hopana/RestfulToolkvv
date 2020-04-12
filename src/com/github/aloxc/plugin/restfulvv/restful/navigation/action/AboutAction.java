package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

/**
 * 关于
 * @author liyh
 */
public class AboutAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Messages.showMessageDialog(" <h3>一套 RESTful 服务开发辅助工具集。</h3>\n" +
                "  <ul>\n" +
                "   <li>1.根据 URL 直接跳转到对应的方法定义 ( Ctrl \\ or Ctrl Alt N ); </li>\n" +
                "   <li>2.提供了一个 Services tree 的显示窗口; </li>\n" +
                "   <li>3.一个简单的 http 请求工具;</li>\n" +
                "   <li>4.在请求方法上添加了有用功能: <em>复制生成 URL</em>;,<em>复制方法参数</em>... </li>\n" +
                "   <li>5.其他功能: java 类上添加 <em>Convert to JSON</em> 功能，<em>格式化 json 数据</em> ( <em>Windows: Ctrl + Enter; Mac: Command + Enter</em> )。</li>\n" +
                "</ul>\n" +
                "   <p>支持 Spring 体系 (Spring MVC / Spring Boot)\n" +
                "   <p>支持 JAX-RS\n" +
                "   <p>支持 Java 和 Kotlin 语言。\n" +
                "\n" +
                "    ]]","About RestfulVV", IconLoader.getIcon("/icons/about.png"));
//        FeatureUsageTracker.getInstance().triggerFeatureUsed("navigation.popup.service");
//
//        ChooseByNameContributor[] chooseByNameContributors = {
//                new GotoRequestMappingContributor(e.getData(DataKeys.MODULE))/*,
//                new RequestMappingContributor()*/
//        };
//
//        final SearchRequestMappingModel model = new SearchRequestMappingModel(project, chooseByNameContributors);
//
////        GotoRequestMappingCallback callback = new GotoRequestMappingCallback();
//
//        GotoActionBase.GotoActionCallback<HttpMethod> callback = new GotoActionBase.GotoActionCallback<HttpMethod>() {
//            @Override
//            protected ChooseByNameFilter<HttpMethod> createFilter(@NotNull ChooseByNamePopup popup) {
//                return new SearchRequestMappingAction.GotoRequestMappingFilter(popup, model, project);
//            }
//
//            @Override
//            public void elementChosen(ChooseByNamePopup chooseByNamePopup, Object element) {
//                if (element instanceof RestServiceItem) {
//                    RestServiceItem navigationItem = (RestServiceItem) element;
//                    if (navigationItem.canNavigate()) {
//                        navigationItem.navigate(true);
//                    }
//                }
//            }
//        };
//
////        this.showNavigationPopup(e, model, callback, false);
//        GotoRequestMappingProvider provider = new GotoRequestMappingProvider(getPsiContext(e));
//        showNavigationPopup(e, model, callback, "Request Mapping Url matching pattern", true, true, (ChooseByNameItemProvider)provider);
////        showNavigationPopup(callback,"Request Mapping Url matching pattern",);
    }
}
