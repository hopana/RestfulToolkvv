package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

/**
 * 捐助
 * @author liyh
 */
public class DonateAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Messages.showMessageDialog(null,"Donate it!", IconLoader.getIcon("/images/timg.jpg"));
    }
}
