package com.github.aloxc.plugin.restfulvv.restful.action;

import com.github.aloxc.plugin.restfulvv.restful.common.PsiClassHelper;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.StringSelection;

public class ConvertClassToJSONAction extends AbstractBaseAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        PsiClass psiClass = getPsiClass(psiElement);

        if(psiClass == null) return;

        String json = PsiClassHelper.create(psiClass).convertClassToJSON(myProject(e), true);
        CopyPasteManager.getInstance().setContents(new StringSelection(json));
    }

    @Nullable
    protected PsiClass getPsiClass(PsiElement psiElement) {
        PsiClass psiClass = null;
        if (psiElement instanceof PsiClass) {
            psiClass = (PsiClass) psiElement;

        }
// TODO Kotlin
//        else if (psiElement instanceof KtClassOrObject) {
//            if (LightClassUtil.INSTANCE.canGenerateLightClass((KtClassOrObject) psiElement)) {
//                psiClass = LightClassUtilsKt.toLightClass((KtClassOrObject) psiElement);
//            }
//        }
        return psiClass;
    }

    @Override
    public void update(AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        setActionPresentationVisible(e,psiElement instanceof PsiClass /*|| psiElement instanceof KtClassOrObject*/);
    }
}
