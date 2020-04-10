package cn.vv.plugin.restfultookvv.restful.navigation.action;

import cn.vv.plugin.restfultookvv.restful.method.HttpMethod;
import com.intellij.featureStatistics.FeatureUsageTracker;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider;
import com.intellij.ide.util.gotoByName.ChooseByNameModel;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.util.Arrays;
import java.util.List;


public class SearchRequestMappingAction extends GotoActionBase implements DumbAware {
    public SearchRequestMappingAction() {
    }

    @Override
    protected void gotoActionPerformed(AnActionEvent e) {
        //进入导航
        Project project = e.getProject();
        if (project == null) return;

        FeatureUsageTracker.getInstance().triggerFeatureUsed("navigation.popup.service");

        ChooseByNameContributor[] chooseByNameContributors = {
                new GotoRequestMappingContributor(e.getData(DataKeys.MODULE))/*,
                new RequestMappingContributor()*/
        };

        final GotoRequestMappingModel model = new GotoRequestMappingModel(project, chooseByNameContributors);

//        GotoRequestMappingCallback callback = new GotoRequestMappingCallback();

        GotoActionCallback<HttpMethod> callback = new GotoActionCallback<HttpMethod>() {
            @Override
            protected ChooseByNameFilter<HttpMethod> createFilter(@NotNull ChooseByNamePopup popup) {
                return new GotoRequestMappingFilter(popup, model, project);
            }

            @Override
            public void elementChosen(ChooseByNamePopup chooseByNamePopup, Object element) {
                if (element instanceof RestServiceItem) {
                    RestServiceItem navigationItem = (RestServiceItem) element;
                    if (navigationItem.canNavigate()) {
                        navigationItem.navigate(true);
                    }
                }
            }
        };

//        this.showNavigationPopup(e, model, callback, false);
        GotoRequestMappingProvider provider = new GotoRequestMappingProvider(getPsiContext(e));
        showNavigationPopup(e, model, callback, "Request Mapping Url matching pattern", true, true, (ChooseByNameItemProvider)provider);
//        showNavigationPopup(callback,"Request Mapping Url matching pattern",);

    }

// TODO IDEA 2018 删了？
//    @Override
    protected <T> void showNavigationPopup(AnActionEvent e,
                                           ChooseByNameModel model,
                                           final GotoActionCallback<T> callback,
                                           @Nullable final String findUsagesTitle,
                                           boolean useSelectionFromEditor,
                                           final boolean allowMultipleSelection,
                                           final ChooseByNameItemProvider itemProvider) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        boolean mayRequestOpenInCurrentWindow = model.willOpenEditor() && FileEditorManagerEx.getInstanceEx(project).hasSplitOrUndockedWindows();
        Pair<String, Integer> start = getInitialText(useSelectionFromEditor, e);
        /*showNavigationPopup(callback, findUsagesTitle,
                ChooseByNamePopup.createPopup(project, model, itemProvider, start.first,
                        mayRequestOpenInCurrentWindow,
                        start.second), allowMultipleSelection);*/

        String copiedURL = tryFindCopiedURL();

        String predefinedText = start.first == null ? copiedURL : start.first;

        showNavigationPopup(callback, findUsagesTitle,
                RestServiceChooseByNamePopup.createPopup(project, model, itemProvider, predefinedText,
                        mayRequestOpenInCurrentWindow,
                        start.second), allowMultipleSelection);
    }

    private String tryFindCopiedURL() {
        String contents = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor);
        if (contents == null) {
            return null;
        }

        contents = contents.trim();
        if (contents.startsWith("http")) {
            if (contents.length() <= 120) {
                return contents;
            }else {
                return contents.substring(0, 120);
            }
        }

        return null;
    }

/*
    private class GotoRequestMappingCallback extends GotoActionCallback{
//        定位选择文件
        @Override
        public void elementChosen(ChooseByNamePopup chooseByNamePopup, Object element) {
            if (element instanceof RestServiceItem) {
                RestServiceItem navigationItem = (RestServiceItem) element;
                if (navigationItem.canNavigate()) {
                    navigationItem.navigate(true);
                }
            }
        }
    }*/

    protected static class GotoRequestMappingFilter extends ChooseByNameFilter<HttpMethod> {
        GotoRequestMappingFilter(final ChooseByNamePopup popup, GotoRequestMappingModel model, final Project project) {
            super(popup, model, GotoRequestMappingConfiguration.getInstance(project), project);
        }

        @Override
        @NotNull
        protected List<HttpMethod> getAllFilterValues() {
//            List<HttpMethod> elements = new ArrayList<>();
            /*elements.add(HttpMethod.GET);
            elements.add(HttpMethod.POST);
            elements.add(HttpMethod.DELETE);
            elements.add(HttpMethod.PATCH);*/
            List<HttpMethod> elements = Arrays.asList(HttpMethod.values());

            return elements;
        }

        @Override
        protected String textForFilterValue(@NotNull HttpMethod value) {
            return value.name();
        }

        @Override
        protected Icon iconForFilterValue(@NotNull HttpMethod value) {
//            return value.getIcon();
            return null;
        }
    }


    //找到文件
    private PsiElement getElement(PsiElement element, ChooseByNamePopup chooseByNamePopup) {
        return null;
    }
}
