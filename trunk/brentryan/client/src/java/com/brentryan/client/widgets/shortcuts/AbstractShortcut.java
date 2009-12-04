/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brentryan.client.widgets.shortcuts;

import com.brentryan.client.widgets.*;
import com.google.gwt.user.client.ui.Composite;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreeNodeConfig;
import com.gwtext.client.widgets.tree.TreePanel;
import java.util.ArrayList;

/**
 *
 * @author bryan
 */
public abstract class AbstractShortcut extends Composite implements Shortcut {

    protected TreePanel treePanel;
    protected TreeNode root;
    protected ArrayList children = new ArrayList();
    private MainPanel mainPanel;
    private boolean selected = false;
    private TreeNode defaultNode;

    public AbstractShortcut(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public TreeNode addShortcut(final String shortcutId, final String shortcut, boolean inactive, boolean preventAnim) {
        TreeNode node = treePanel.getNodeById(shortcutId);
        if (node != null) {
            if (!inactive) {
                node.select();
            }
            return node;
        }
        node = new TreeNode(new TreeNodeConfig() {

            {
                setIconCls("shortcut-icon");
                setLeaf(true);
                setCls("shortcut");
                setId(shortcutId);
                setText(shortcut);
            }
        });
        root.appendChild(node);
        if (!inactive) {
            if (!preventAnim) {
                node.select();
            } else {
                node.select();
            }
        }
        children.add(node);

        return node;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setDefaultNode(TreeNode defaultNode) {
        this.defaultNode = defaultNode;
    }

    public TreeNode getDefaultNode() {
        return defaultNode;
    }

    public void showDefault() {
        defaultNode.select();
    }
}
