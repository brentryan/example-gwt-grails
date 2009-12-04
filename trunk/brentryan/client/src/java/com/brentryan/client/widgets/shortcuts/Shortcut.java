/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brentryan.client.widgets.shortcuts;

import com.gwtext.client.widgets.tree.TreeNode;

/**
 *
 * @author bryan
 */
public interface Shortcut {

    TreeNode addShortcut(final String shortcutId, final String shortcut, boolean inactive, boolean preventAnim);

    void showDefault();

    void setSelected(boolean selected);

    boolean isSelected();
}
