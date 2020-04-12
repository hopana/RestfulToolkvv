package com.github.aloxc.plugin.restfulvv.restful.navigator;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBPanel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;

public class Designer extends JBPanel {
    private JPanel rootPanel;
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

    protected void printComponent(Graphics g) {
        super.printComponent(g);
    }

    public Designer(){
        super();
        initComponent();
    }

    private void initComponent() {
        initUI();
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
                        new Dimension(18,-1), new Dimension(18,-1), new Dimension(18,-1)));

        requestPane.add(midPane,
                new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(250,-1), new Dimension(250,-1), new Dimension(250,-1)));
        requestPane.add(rightTabbedPane,
                new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(-1,-1), new Dimension(-1,-1), new Dimension(-1,-1)));
        this.add(requestPane,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(-1,-1), new Dimension(-1,-1), new Dimension(-1,-1)));
    }

    private void initUserCaseRightPanel() {

    }

    private void initUserCaseMidPanel() {
        GridLayoutManager midPaneLayoutManager = new GridLayoutManager(2, 1);
        midPaneLayoutManager.setHGap(1);
        midPaneLayoutManager.setVGap(1);
        midPaneLayoutManager.setMargin(new Insets(0,5,0,0));
        midPane = new JBPanel<>();
        midPane.setLayout(midPaneLayoutManager);
        midPane.setMaximumSize(new Dimension(250,26));
        midPane.setMinimumSize(new Dimension(250,26));
        midPane.setPreferredSize(new Dimension(250,26));

        midPane.add(caseTextPane,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(250,28), new Dimension(250,28),  new Dimension(250,28)));

        DefaultTreeModel model = (DefaultTreeModel)userCaseTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        root.removeAllChildren();

        JPopupMenu menu=new JPopupMenu();		//创建菜单
        JMenuItem deleteMenu=new JMenuItem("Delete");//创建菜单项(点击菜单项相当于点击一个按钮)
        JMenuItem modifyMenu=new JMenuItem("Modify");//创建菜单项(点击菜单项相当于点击一个按钮)
        menu.add(deleteMenu);
        menu.add(modifyMenu);
        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Messages.showMessageDialog("删除treeNode" ,"删除元素", IconLoader.getIcon("/icons/delete.png"));

            }
        });
        modifyMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Messages.showMessageDialog("编辑treeNode" ,"编辑元素", IconLoader.getIcon("/icons/delete.png"));

            }
        });
        userCaseTree.addTreeSelectionListener(new TreeSelectionListener(){
            @Override
            public void valueChanged(TreeSelectionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) userCaseTree.getLastSelectedPathComponent();
                int selectIndex = node.getParent().getIndex(node);
                System.out.println("选中索引 " + selectIndex);

                if(null!=node) {
                    Object userObject = node.getUserObject();
                    if (null != userObject) {
                        if (userObject instanceof String) {
                            String objectName = userObject.toString();

                        }
                    }
                }
            }
        });
//        userCaseTree.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                int x = e.getX();
//                int y = e.getY();
//                if(e.getButton()==MouseEvent.BUTTON3){
//                    //menuItem.doClick(); //编程方式点击菜单项
//                    TreePath pathForLocation = userCaseTree.getPathForLocation(x, y);//获取右键点击所在树节点路径
//                    userCaseTree.setSelectionPath(pathForLocation);
//                    menu.show(userCaseTree, x, y);
//                }
//            }
//        });
        for(int i = 0;i<50;i++){
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
        DefaultTreeCellRenderer render=(DefaultTreeCellRenderer)(userCaseTree.getCellRenderer());
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
                        new Dimension(250,-1), new Dimension(250,-1), new Dimension(250,-1)));
        midPane.add(userCaseScroll,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(250,-1), new Dimension(250,-1), new Dimension(250,-1)));

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
                Messages.showMessageDialog("点击了编辑按钮" ,"编辑元素", IconLoader.getIcon("/icons/modify.png"));
            }
        });

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Messages.showMessageDialog("点击了删除按钮" ,"删除元素", IconLoader.getIcon("/icons/delete.png"));
            }
        });

        envSelectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Messages.showMessageDialog("点击了环境切换按钮" ,"环境切换", IconLoader.getIcon("/icons/env.png"));
                int x = e.getX();
                int y = e.getY();
                JPopupMenu menu=new JPopupMenu();		//创建菜单
                for(int i = 0;i<5;i++){
                    JMenuItem item=new JMenuItem("env " + i);//创建菜单项(点击菜单项相当于点击一个按钮)
                    menu.add(item);
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JMenuItem item = (JMenuItem)e.getSource();
                            Messages.showMessageDialog("点击了环境切换按钮111" + item.getText() ,"环境切换", IconLoader.getIcon("/icons/env.png"));
                        }
                    });
                }
                menu.show(envSelectButton,x,y);
            }
        });
        leftNavPane.add(saveCaseButton,
                new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16,16), new Dimension(16,16),  new Dimension(16,16)));
        leftNavPane.add(envSelectButton,
                new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16,16), new Dimension(16,16),  new Dimension(16,16)));

        leftNavPane.add(testButton,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16,16), new Dimension(16,16),  new Dimension(16,16)));

        leftNavPane.add(modifyButton,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16,16), new Dimension(16,16),  new Dimension(16,16)));

        leftNavPane.add(deleteButton,
                new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED,
                        new Dimension(16,16), new Dimension(16,16),  new Dimension(16,16)));

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

}
