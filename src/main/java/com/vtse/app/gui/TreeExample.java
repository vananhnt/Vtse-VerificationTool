package com.vtse.app.gui;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

public class TreeExample extends JFrame {

    public TreeExample() throws HeadlessException {
        initializeUI();
    }

    private void initializeUI() {
        setSize(200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode chapterOne = new DefaultMutableTreeNode("Chapter One");
        DefaultMutableTreeNode one = new DefaultMutableTreeNode("1.1");
        DefaultMutableTreeNode two = new DefaultMutableTreeNode("1.2");
        DefaultMutableTreeNode three = new DefaultMutableTreeNode("1.3");

        root.add(chapterOne);
        chapterOne.add(one);
        chapterOne.add(two);
        chapterOne.add(three);

        JTree tree = new JTree(root);
        tree.addTreeSelectionListener(createSelectionListener());

        JScrollPane pane = new JScrollPane(tree);
        pane.setPreferredSize(new Dimension(200, 400));

        getContentPane().add(pane);
    }

    private TreeSelectionListener createSelectionListener() {
        return new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getPath();                
                int pathCount = path.getPathCount();

                for (int i = 0; i < pathCount; i++) {
                    System.out.print(path.getPathComponent(i).toString());
                    if (i + 1 != pathCount) {
                        System.out.print("|");
                    }
                }
                System.out.println("");
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //new JTreeSelectionListenerDemo().setVisible(true);
            }
        });
    }
}