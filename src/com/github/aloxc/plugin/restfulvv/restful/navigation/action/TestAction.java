package com.github.aloxc.plugin.restfulvv.restful.navigation.action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * 关于
 * @author liyh
 */
public class TestAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
//      actionPerformed被点击回调后，会传入AnActionEvent对象，通过该对象可以获得如下一些对象：
//      获取当前编辑的文件, 通过PsiFile可获得PsiClass, PsiField等对象
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);

//      获取当前的project对象
        Project project = e.getProject();
//      获取数据上下文
        DataContext dataContext = e.getDataContext();

// 获取到数据上下文后，通过CommonDataKeys对象可以获得该File的所有信息
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        PsiFile curFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        VirtualFile virtualFile = CommonDataKeys.VIRTUAL_FILE.getData(dataContext);

//        PSI对象的一些常用方法
// 通过给定名称（不包含具体路径）搜索对应文件, 传入3个参数 Project, FileName, GlobalSearchScope;
// GlobalSearchScope中有Project域，Moudule域，File域等等
//        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, name, GlobalSearchScope);
// 类似于IDE中的Find Usages操作，重载方法较多，具体不再一一列出
//        Query<PsiReference> search = ReferencesSearch.search(PsiElement);
// 重命名
//        RenameRefactoring newName = RefactoringFactory.getInstance(Project).createRename(PsiElement, "newName");
// 搜索一个类的所有子类，重载方法较多，具体不再一一列出
//        Query<PsiClass> search = ClassInheritorsSearch.search(PsiClass);

// 根据类的全限定名查询PsiClass，下面这个方法是查询Project域
//        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(classQualifiedName, GlobalSearchScope.projectScope(project));

// 根据类的SiampleName查询PsiClass，下面这个方法是查询Project域
//        PsiClass[] psiClasses = PsiShortNamesCache.getInstance(Project).getClassesByName(classSimpleName, GlobalSearchScope.projectScope(Project));
// 获取Java类所在的Package
//        PsiPackage psiPackage = JavaPsiFacade.getInstance(Project).findPackage(classQualifiedName);

// 查找被特定方法重写的方法
//        Query<PsiMethod> search = OverridingMethodsSearch.search(PsiMethod);
//        至于PsiClass， PsiMethod，PsiFiled中的相关方法这里不再一一列出；具体的查看相应的接口即可。
//
//        在插件中可能需要向PsiFile中写入一些字段或者方法（这里是写入后需要展示，而不是类似PsiAugmentProvider生成的快照），
//
//        这里需要注意的是，我们拿到新生成的psiClass以后，不能使用psiClass.add(field)添加代码，要调用WriteCommandAction.runWriteCommandAction写代码，否则会抛出异常：
//
//        Must not change PSI outside command or undo-transparent action.
//
//                这时因为Intellij Platform不允许在主线程中进行实时的文件写入，而需要通过一个异步任务来进行。这时需要调用WriteCommandAction来写相关的内容，如下：
//
//        WriteCommandAction.runWriteCommandAction(Project, new Runnable() {
//            @Override
//            public void run() {
//                do something
//            }
//        });
//
//        在run方法中也是调用psiClass.add(field)等这类方法，只是需要使用WriteCommandAction.runWriteCommandAction包一层。
//
//        下面来看看相关的添加注解，添加方法，添加字段等相应的方法：
//
// 通过获取到PsiElementFactory来创建相应的Element，包括字段，方法，注解，类，内部类等等
//        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(Project);
// 创建类
//        PsiClass aClass = elementFactory.createClass(className);
// 创建注解
//        elementFactory.createAnnotationFromText(annotationName, PsiElement);
// 创建字段 所有的PsiElement创建后都可以获得其ModifierList，用于设置其修饰符
//        PsiField field = elementFactory.createField(fieldName, PsiType);
//        field.getModifierList().setModifierProperty(PsiModifier.PUBLIC, true);
// 创建方法 这个PsiType是void，也就是说方法返回类型为void
//        PsiMethod method = elementFactory.createMethod(methodName, PsiType.VOID);
//
// 添加PsiElement, 有add, addBefore, addAfter, addBetween
//        PsiElement.add(PsiElement);
//
// 由于PsiModifierList是PsiElement的子类，所以一般也这样调用
//        PsiModifierList modifierList = targetElement.getModifierList();
//        if (null != modifierList) {
//            modifierList.addAfter(newPsiElement, null); // 在targetElement上添加newPsiElement
//        }
//
//        一些高级特性
//        插件配置参数持久化：当插件有一些用户定制的配置参数信息时，需要插件具备记忆功能，在IDEA重启后，任然能够生效，这就需要用到插件配置信息持久化接口。需要实现接口：实现接口PersistentStateComponent
//        状态栏进度条：对于存在长耗时运算的插件，会了提升用户体验，常常需要增加进度条。扩展Task.Backgroundable抽象类，并覆盖run方法。
//        使用模板：相信大家都用过IDEA提供的模板，在插件开发中也可以使用文件模板，将公共部分代码进行抽离，减少创建PSI对象的复杂程度。具体可参考文献10
//                总结
//        在上文中笔者多次提到lombok的实现，甚至所有的截图都是使用的lombok的项目，这是因为刚开始接触IDEA插件开发的时候，自己也是什么都不懂。最快的学习方式就是阅读相关开源框架的源码，把lombok的源码刷了一遍之后就大概知道如何开发了，其他细节不能的地方就求助Google就能够解决了。
//
//        著名开源框架的代码都十分具有学习的价值，可能刚开始看起来会很吃力，但是看的多了就知道大体上的设计思路以及相应的实现方式，只要坚持下去就一定有所收获~
    }
}
