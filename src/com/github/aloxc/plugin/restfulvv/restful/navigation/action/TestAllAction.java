package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

/**
 * 测试所有
 */
public class TestAllAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Messages.showMessageDialog(" 测试所有","Test All RestfulVV", IconLoader.getIcon("/icons/test.png"));
    }
}
