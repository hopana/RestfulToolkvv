package com.github.aloxc.plugin.restfulvv.restful.navigator;

import com.github.aloxc.plugin.restfulvv.restful.common.RequestHelper;
import com.github.aloxc.plugin.restfulvv.restful.component.VTextPane;
import com.github.aloxc.plugin.restfulvv.restful.highlight.JTextAreaHighlight;
import com.github.aloxc.plugin.restfulvv.utils.JsonUtils;
import com.github.aloxc.plugin.restfulvv.utils.ToolkitUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.intellij.openapi.editor.colors.FontPreferences;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;


public class RestServiceDetail extends JBPanel/*WithEmptyText*/ {
    private static RestServiceDetail restServiceDetail;
    /**
     * 用 awt 重新定义，后期再改吧。
     */
//    public JPanel this;// = new JBPanelWithEmptyText();
    public JTextField urlField;
    public JPanel urlPanel;
    public JTextField methodField;
    public JButton sendButton;
    public JTabbedPane rightTabbedPane;
    private JTree userCaseTree;
    private JPanel requestPane;
    private JLabel caseTextPane;
    private JPanel midPane;
    private JButton saveCaseButton;
    private JPanel leftNavPane;
    private JButton envSelectButton;
    private JButton testButton;
    private JScrollPane userCaseScroll;
    private JButton deleteButton;
    private JButton modifyButton;
    private JPanel rootPanel;
    private JPanel rightPane;
    private JPanel searchPane;
    private JCheckBox keyCheckBox;
    private JCheckBox valueCheckBox;
    private JTextField searchKey;
    private JButton searchButton;

    public VTextPane requestParamsTextArea;
    public VTextPane requestBodyTextArea;
    public VTextPane responseTextArea;

/*    public static RestServiceDetail getInstance() {
        if (restServiceDetail == null) {
            restServiceDetail =  new RestServiceDetail();
        }
        return restServiceDetail;
    }*/

    public static RestServiceDetail getInstance(Project p) {
        return p.getComponent(RestServiceDetail.class);
    }


    private RestServiceDetail() {
        super();
//        withEmptyText("JSON FORMAT");
        initComponent();
    }

    public void initComponent() {
        initUI();
        initActions();
        initTab();
    }

    private void initActions() {
//        bindMouseEvent(servicesTree);
        bindSendButtonActionListener();

        bindUrlTextActionListener();

        bindSearchActionListener();

        bindSearchKeyListener();

        bindSaveCaseListener();
    }

    private void bindSaveCaseListener() {
        saveCaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                JPopupMenu menu = new JPopupMenu();        //创建保存菜单
                JMenuItem save = new JMenuItem("Save ");//保存
                save.setIcon(IconLoader.getIcon("/icons/save.png"));
                JMenuItem saveAs = new JMenuItem("Save as");//另存为
                saveAs.setIcon(IconLoader.getIcon("/icons/saveas.png"));
                menu.add(save);
                menu.add(saveAs);
                save.addActionListener(evt -> {
                    Messages.showMessageDialog("点击了" + ((JMenuItem) e.getSource()).getText(), "保存行为", IconLoader.getIcon("/icons/save.png"));
                });
                saveAs.addActionListener(evt -> {
                    Messages.showMessageDialog("点击了" + ((JMenuItem) evt.getSource()).getText(), "保存行为", IconLoader.getIcon("/icons/save.png"));
                });
                menu.show(saveCaseButton, x, y);
            }
        });
    }

    /**
     * 搜索输入框按键 监听器
     */
    private void bindSearchKeyListener() {
        this.searchKey.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //回车直接搜索
                if (e.getKeyCode() == 10) {
                    try {
                        if(searchKey != null && responseTextArea != null) {
                            responseTextArea.search(searchKey);
                        }
                    } catch (BadLocationException var3) {
                        var3.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 搜索按钮动作监听
     */
    private void bindSearchActionListener() {
        this.searchButton.addActionListener(event -> {
            try {
                if(searchKey != null && responseTextArea != null) {
                    responseTextArea.search(searchKey);
                }
            } catch (BadLocationException e) {
                System.err.println("搜索按钮动作监听异常");
                e.printStackTrace();
            }
        });
    }

    public void initTab() {
//        jTextArea.setAutoscrolls(true);
        String jsonFormat = "Try press 'Ctrl(Cmd) Enter'";
        VTextPane textArea = createTextArea("{'key':'value'}");

        addRequestTabbedPane(jsonFormat, textArea);
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);
    }

    private void initUI() {
        GridLayoutManager rightLayoutManager = new GridLayoutManager(1, 3);
        rightLayoutManager.setHGap(1);
        rightLayoutManager.setVGap(1);
        requestPane = new JBPanel<>();
        requestPane.setLayout(rightLayoutManager);

        initRequestPanel();
        initUserCaseLeftPanel();
        initUserCaseMidPanel();
        initUserCaseRightPanel();
        initUserCasePanel();

    }

    private void initUserCasePanel() {
        requestPane.add(leftNavPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(18, -1), new Dimension(18, -1), new Dimension(18, -1)));

        requestPane.add(midPane,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(250, -1), new Dimension(250, -1), new Dimension(250, -1)));

        requestPane.add(rightPane,
                new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(-1, -1), new Dimension(-1, -1), new Dimension(-1, -1)));

        this.add(requestPane,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(-1, -1), new Dimension(-1, -1), new Dimension(-1, -1)));
    }

    private void initUserCaseRightPanel() {
        GridLayoutManager rightPaneLayoutManager = new GridLayoutManager(2, 1);
        rightPaneLayoutManager.setHGap(1);
        rightPaneLayoutManager.setVGap(1);
        rightPaneLayoutManager.setMargin(new Insets(0, 5, 0, 0));
        rightPane = new JBPanel<>();
        rightPane.setLayout(rightPaneLayoutManager);
//        rightPane.setMaximumSize(new Dimension(-1,26));
//        rightPane.setMinimumSize(new Dimension(250,26));
//        rightPane.setPreferredSize(new Dimension(250,26));

        rightPane.add(rightTabbedPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(-1, -1), new Dimension(-1, -1), new Dimension(-1, -1)));


        searchPane = new JBPanel<>();
//        searchPane.setMaximumSize(new Dimension(-1,28));
//        searchPane.setMinimumSize(new Dimension(-1,28));
//        searchPane.setPreferredSize(new Dimension(-1,28));

        keyCheckBox.setAutoscrolls(true);
        valueCheckBox.setAutoscrolls(true);
        GridLayoutManager searchLayoutManager = new GridLayoutManager(1, 4);
        searchLayoutManager.setHGap(1);
        searchLayoutManager.setVGap(1);
        searchPane.setLayout(searchLayoutManager);
        GridConstraints test = new GridConstraints();
        searchPane.add(keyCheckBox,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
        searchPane.add(valueCheckBox,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
        searchPane.add(searchKey,
                new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
//// 是否必要保留？
        searchPane.add(searchButton,
                new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
//        this.setBorder(BorderFactory.createEmptyBorder());
//        this.setLayout(new GridLayoutManager(2, 1));
//
//        this.add(urlPanel,
//                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
//                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED,
//                        null, null, null));
//
//
        rightPane.add(searchPane,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(-1, -1), new Dimension(-1, -1), new Dimension(-1, -1)));

    }

    private void initUserCaseMidPanel() {
        GridLayoutManager midPaneLayoutManager = new GridLayoutManager(2, 1);
        midPaneLayoutManager.setHGap(1);
        midPaneLayoutManager.setVGap(1);
        midPaneLayoutManager.setMargin(new Insets(0, 5, 0, 0));
        midPane = new JBPanel<>();
        midPane.setLayout(midPaneLayoutManager);
        midPane.setMaximumSize(new Dimension(250, 26));
        midPane.setMinimumSize(new Dimension(250, 26));
        midPane.setPreferredSize(new Dimension(250, 26));

        midPane.add(caseTextPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(250, 28), new Dimension(250, 28), new Dimension(250, 28)));

        DefaultTreeModel model = (DefaultTreeModel) userCaseTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();

        JPopupMenu menu = new JPopupMenu();        //创建菜单
        JMenuItem deleteMenu = new JMenuItem("Delete");//创建菜单项(点击菜单项相当于点击一个按钮)
        JMenuItem modifyMenu = new JMenuItem("Modify");//创建菜单项(点击菜单项相当于点击一个按钮)
        menu.add(deleteMenu);
        menu.add(modifyMenu);
        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Messages.showMessageDialog("删除treeNode", "删除元素", IconLoader.getIcon("/icons/delete.png"));

            }
        });
        modifyMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Messages.showMessageDialog("编辑treeNode", "编辑元素", IconLoader.getIcon("/icons/delete.png"));

            }
        });
        userCaseTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) userCaseTree.getLastSelectedPathComponent();
                int selectIndex = node.getParent().getIndex(node);
                System.out.println("选中索引 " + selectIndex);

                if (null != node) {
                    Object userObject = node.getUserObject();
                    if (null != userObject) {
                        if (userObject instanceof String) {
                            String objectName = userObject.toString();

                        }
                    }
                }
            }
        });
        for (int i = 0; i < 50; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode("节点\t" + i);
            root.add(node);
        }
        model.reload(root);
        userCaseTree.expandPath(new TreePath(root));
        userCaseTree.updateUI();

        //不显示根节点
        userCaseTree.setRootVisible(false);
        userCaseTree.setShowsRootHandles(false);
        userCaseTree.setRowHeight(22);
        DefaultTreeSelectionModel singleSelectionModel = new DefaultTreeSelectionModel();
        singleSelectionModel.setSelectionMode(SINGLE_TREE_SELECTION);
        userCaseTree.setSelectionModel(singleSelectionModel);
        DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) (userCaseTree.getCellRenderer());
        render.setIcon(null);
        render.setLeafIcon(null);
        render.setOpenIcon(null);
        render.setClosedIcon(null);

        ScrollPaneLayout userCaseScrollLayoutManager = new ScrollPaneLayout();
        userCaseScrollLayoutManager.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        userCaseScrollLayoutManager.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        userCaseScroll.setLayout(userCaseScrollLayoutManager);
        userCaseScroll.getViewport().add(userCaseTree,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(250, -1), new Dimension(250, -1), new Dimension(250, -1)));
        midPane.add(userCaseScroll,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(250, -1), new Dimension(250, -1), new Dimension(250, -1)));

    }

    private void initUserCaseLeftPanel() {
        LayoutManager flowLayoutManager = new FlowLayout();
        leftNavPane = new JBPanel<>();
        leftNavPane.setLayout(flowLayoutManager);
        saveCaseButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveCaseButton.setBorderPainted(false);
        saveCaseButton.setOpaque(false);
        saveCaseButton.setRolloverEnabled(true);
        envSelectButton.setFocusPainted(true);

        envSelectButton.setBorder(BorderFactory.createRaisedBevelBorder());
        envSelectButton.setBorderPainted(false);
        envSelectButton.setOpaque(false);
        envSelectButton.setRolloverEnabled(true);
        envSelectButton.setFocusPainted(true);

        testButton.setBorder(BorderFactory.createRaisedBevelBorder());
        testButton.setBorderPainted(false);
        testButton.setOpaque(false);
        testButton.setRolloverEnabled(true);
        testButton.setFocusPainted(true);

        deleteButton.setBorder(BorderFactory.createRaisedBevelBorder());
        deleteButton.setBorderPainted(false);
        deleteButton.setOpaque(false);
        deleteButton.setRolloverEnabled(true);
        deleteButton.setFocusPainted(true);

        modifyButton.setBorder(BorderFactory.createRaisedBevelBorder());
        modifyButton.setBorderPainted(false);
        modifyButton.setOpaque(false);
        modifyButton.setRolloverEnabled(true);
        modifyButton.setFocusPainted(true);

        modifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Messages.showMessageDialog("点击了编辑按钮", "编辑元素", IconLoader.getIcon("/icons/modify.png"));
            }
        });

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Messages.showMessageDialog("点击了删除按钮", "删除元素", IconLoader.getIcon("/icons/delete.png"));
            }
        });

        envSelectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                Messages.showMessageDialog("点击了环境切换按钮" ,"环境切换", IconLoader.getIcon("/icons/env.png"));
                int x = e.getX();
                int y = e.getY();
                JPopupMenu menu = new JPopupMenu();        //创建菜单
                for (int i = 0; i < 5; i++) {
                    JMenuItem item = new JMenuItem("env " + i);//创建菜单项(点击菜单项相当于点击一个按钮)
                    item.setIcon(IconLoader.getIcon("/icons/" + (i+1) + ".png"));
                    menu.add(item);
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JMenuItem item = (JMenuItem) e.getSource();
                            Messages.showMessageDialog("点击了环境切换按钮111" + item.getText(), "环境切换", IconLoader.getIcon("/icons/env.png"));
                        }
                    });
                }
                menu.show(envSelectButton, x, y);
            }
        });
        leftNavPane.add(saveCaseButton,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16, 16), new Dimension(16, 16), new Dimension(16, 16)));
        leftNavPane.add(envSelectButton,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16, 16), new Dimension(16, 16), new Dimension(16, 16)));

        leftNavPane.add(testButton,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16, 16), new Dimension(16, 16), new Dimension(16, 16)));

        leftNavPane.add(modifyButton,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16, 16), new Dimension(16, 16), new Dimension(16, 16)));

        leftNavPane.add(deleteButton,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16, 16), new Dimension(16, 16), new Dimension(16, 16)));

    }

    private void initRequestPanel() {
        urlField.setAutoscrolls(true);
        urlPanel = new JBPanel();
        GridLayoutManager mgr = new GridLayoutManager(1, 3);
        mgr.setHGap(1);
        mgr.setVGap(1);
        urlPanel.setLayout(mgr);
        urlPanel.add(methodField,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
        urlPanel.add(urlField,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
// 是否必要保留？
        urlPanel.add(sendButton,
                new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setLayout(new GridLayoutManager(2, 1));

        this.add(urlPanel,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
    }

    private void bindSendButtonActionListener() {
        sendButton.addActionListener(e -> {
            // PluginManagerMain
            ProgressManager.getInstance().run(new Task.Backgroundable(null, "Sending Request") {
                @Override
                public void run(@NotNull ProgressIndicator indicator) {
                    final Runnable runnable = () -> {
                        String url = urlField.getText();

                        if (requestParamsTextArea != null) {
                            String requestParamsText = requestParamsTextArea.getText();
                            Map<String, String> paramMap = ToolkitUtil.textToParamMap(requestParamsText);
                            if (paramMap != null && paramMap.size() > 0) {
                                // set PathVariable value to request URI
                                for (String key : paramMap.keySet()) {
                                    url = url.replaceFirst("\\{(" + key + "[\\s\\S]*?)\\}", paramMap.get(key));
                                }
                            }

                            String params = ToolkitUtil.textToRequestParam(requestParamsText);
                            if (params.length() != 0) {
                                // 包含了参数
                                if (url.contains("?")) {
                                    url += "&" + params;
                                } else {
                                    url += "?" + params;
                                }
                            }
                        }

                        // 完整url
                        String method = methodField.getText();
                        String responseText = url;

                        String response;
                        if (requestBodyTextArea != null && StringUtils.isNotBlank(requestBodyTextArea.getText())) {
                            response = RequestHelper.postRequestBodyWithJson(url, requestBodyTextArea.getText());
                        } else {
                            response = RequestHelper.request(url, method);
                        }

                       /* else if (method.equalsIgnoreCase("post")) {
//                response = HttpClientHelper.post(url);
                            response = RequestHelper.post(url);
                        } else {
                            response = RequestHelper.get(url);
                        }*/

                        if (response != null) responseText = response;

                        addResponseTabPanel(responseText);

                    };
                    runnable.run();
                }
            });

        });
    }

    private void bindUrlTextActionListener() {
        rightTabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                urlField.moveCaretPosition(urlField.getDocument().getLength());
//                urlField.select(0,0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                urlField.selectAll();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mousePressed(e);
                urlField.selectAll();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mousePressed(e);
                urlField.selectAll();
            }
        });


        /*urlField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                urlField.moveCaretPosition(urlField.getDocument().getLength());
//                urlField.select(0,0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
//                urlField.selectAll();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mousePressed(e);
                urlField.selectAll();
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mousePressed(e);
                urlField.selectAll();
            }
        });*/

        methodField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                methodField.selectAll();
            }
        });

    }


    public void addRequestParamsTab(String requestParams) {

        StringBuilder paramBuilder = new StringBuilder();

        if (StringUtils.isNotBlank(requestParams)) {
            String[] paramArray = requestParams.split("&");
            for (String paramPairStr : paramArray) {
                String[] paramPair = paramPairStr.split("=");

                String param = paramPair[0];
                String value = paramPairStr.substring(param.length() + 1);
                paramBuilder.append(param).append(" : ").append(value).append("\n");
            }
        }

        if (requestParamsTextArea == null) {
            requestParamsTextArea = createTextArea(paramBuilder.toString());
        } else {
            requestParamsTextArea.setText(paramBuilder.toString());
//            addRequestTabbedPane("RequestParams", requestParamsTextArea);
        }


        addRequestTabbedPane("RequestParam(s)", requestParamsTextArea);

    }

    public void addRequestBodyTabPanel(String text) {
//        jTextArea.setAutoscrolls(true);
        String reqBodyTitle = "RequestBody";
        if (requestBodyTextArea == null) {
            requestBodyTextArea = createTextArea(text);
        } else {
            requestBodyTextArea.setText(text);
        }


        addRequestTabbedPane(reqBodyTitle, this.requestBodyTextArea);
    }


    public void addRequestTabbedPane(String title, VTextPane jTextArea) {

        JScrollPane jbScrollPane = new JBScrollPane(jTextArea, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jTextArea.addKeyListener(new TextAreaKeyAdapter(jTextArea));

        rightTabbedPane.addTab(title, jbScrollPane);
//        jTextArea.format();
        rightTabbedPane.setSelectedComponent(jbScrollPane);//.setSelectedIndex(rightTabbedPane.getTabCount() - 1);
    }

    /*添加 Response Tab*/
    public void addResponseTabPanel(String text) {

        String responseTabTitle = "Response";
        if (responseTextArea == null) {
            responseTextArea = createTextArea(text);
            addRequestTabbedPane(responseTabTitle, responseTextArea);
        } else {
            Component componentAt = null;
            responseTextArea.setText(text);
            int tabCount = rightTabbedPane.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                if (rightTabbedPane.getTitleAt(i).equals(responseTabTitle)) {
                    componentAt = rightTabbedPane.getComponentAt(i);
                    rightTabbedPane.addTab(responseTabTitle, componentAt);
                    rightTabbedPane.setSelectedComponent(componentAt);
                    break;
//                    Component component = rightTabbedPane.getComponent(i);
                }
            }
            if (componentAt == null) {
                addRequestTabbedPane(responseTabTitle, responseTextArea);
            }

        }

    }

    @NotNull
    public VTextPane createTextArea(String text) {
        Font font = getEffectiveFont();

        VTextPane textArea = new VTextPane();
        textArea.setShowLineNumber(true);
        textArea.setText(text);
//        JTextArea textArea = new JTextArea(text);
        textArea.setFont(font);

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = textArea.getText();
                getEffectiveFont(text);
            }
        });

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    // copy text to parse
                    CopyPasteManager.getInstance().setContents(new StringSelection(textArea.getText()));
                }
            }
        });
        return textArea;
    }

    @NotNull  // editor.xml
    private Font getEffectiveFont(String text) {
        FontPreferences fontPreferences = this.getFontPreferences();

        List<String> effectiveFontFamilies = fontPreferences.getEffectiveFontFamilies();

        int size = fontPreferences.getSize(fontPreferences.getFontFamily());
//        Font font=new Font(FontPreferences.FALLBACK_FONT_FAMILY,Font.PLAIN,size);
        Font font = new Font(FontPreferences.DEFAULT_FONT_NAME, Font.PLAIN, size);
//        String fallbackFontFamily = getFallbackName(fontFamily, size, null);
        //有效字体
        for (String effectiveFontFamily : effectiveFontFamilies) {
            Font effectiveFont = new Font(effectiveFontFamily, Font.PLAIN, size);
            if (effectiveFont.canDisplayUpTo(text) == -1) {
                font = effectiveFont;
                break;
            }
        }
        return font;
    }

    @NotNull
    private final FontPreferences getFontPreferences() {
        //        FontPreferences fontPreferences = AppEditorFontOptions.getInstance().getFontPreferences();
        return new FontPreferences();
    }

    @NotNull
    private Font getEffectiveFont() {
        //        UIUtil.getEditorPaneBackground()
        //editor default font
        FontPreferences fontPreferences = this.getFontPreferences();
        String fontFamily = fontPreferences.getFontFamily();
        int size = fontPreferences.getSize(fontFamily);
        return new Font("Monospaced", Font.PLAIN, size);
    }


    public void resetRequestTabbedPane() {
        this.rightTabbedPane.removeAll();
        resetTextComponent(requestParamsTextArea);
        resetTextComponent(requestBodyTextArea);
        resetTextComponent(responseTextArea);
    }

    private void resetTextComponent(VTextPane textComponent) {
        if (textComponent != null && StringUtils.isNotBlank(textComponent.getText())) {
            textComponent.setText("");
        }
    }

    public void setMethodValue(String method) {
        methodField.setText(String.valueOf(method));
    }

    public void setUrlValue(String url) {
        urlField.setText(url);
    }

    private class TextAreaKeyAdapter extends KeyAdapter {
        private final VTextPane jTextArea;

        public TextAreaKeyAdapter(VTextPane jTextArea) {
            this.jTextArea = jTextArea;
        }

        @Override
        public void keyPressed(KeyEvent event) {
            super.keyPressed(event);

            // 组合键ctrl+enter自定义，当Ctrl (Command on Mac)+Enter组合键按下时响应
            if ((event.getKeyCode() == KeyEvent.VK_ENTER)
                    && (event.isControlDown() || event.isMetaDown())) {

                //解析，格式化json
                String oldValue = jTextArea.getText();
                if (!JsonUtils.isValidJson(oldValue)) {
                    return;
                }

                JsonParser parser = new JsonParser();
                JsonElement parse = parser.parse(oldValue);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(parse);
                jTextArea.setText(json);
            }
        }
    }


}