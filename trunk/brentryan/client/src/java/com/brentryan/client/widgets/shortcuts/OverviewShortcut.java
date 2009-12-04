/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brentryan.client.widgets.shortcuts;

import com.google.gwt.core.client.GWT;
import com.brentryan.client.widgets.*;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.Node;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.TreePanelConfig;
import com.gwtext.client.widgets.tree.event.TreeNodeListenerAdapter;

/**
 *
 * @author bryan
 */
public class OverviewShortcut extends AbstractShortcut {

    private static final OverviewShortcutConstants CONSTANTS = (OverviewShortcutConstants) GWT.create(OverviewShortcutConstants.class);
    private static final String STATUS_NODE_ID = "statusNode";

    public OverviewShortcut(MainPanel mainPanel) {
        super(mainPanel);

        this.treePanel = new TreePanel("overview-tree", new TreePanelConfig() {

            {
                setContainerScroll(true);
                setAnimate(true);
                setRootVisible(false);
            }
        });
        root = new TreeNode(CONSTANTS.OverviewTitle());
        treePanel.setRootNode(root);
        treePanel.render();

        root.expand(false, true);

        TreeNode statusNode = this.addShortcut(STATUS_NODE_ID, CONSTANTS.StatusTitle(), false, true);
        statusNode.addTreeNodeListener(new StatusNodeListener());
        setDefaultNode(statusNode);

        VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");
        vp.add(treePanel);
        initWidget(vp);
        this.setTitle(CONSTANTS.OverviewTitle());
    }

    private class StatusNodeListener extends TreeNodeListenerAdapter {

        public void onClick(Node node, EventObject event) {
            showStatus();
        }
    }

    private void showStatus() {
        BorderLayout innerLayout = this.getMainPanel().getInnerLayout();

        //clear out the old content
        innerLayout.remove(LayoutRegionConfig.CENTER, MainPanel.CONTENT_ID);
        ContentPanel p = new ContentPanel(MainPanel.CONTENT_ID);

        Grid grid = new Grid(2, 2);
        p.add(grid);
        grid.setWidth("100%");
        grid.setHeight("100%");

        ChartImage img = new ChartImage("pie");
        grid.setWidget(0, 0, img);
        grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
        ChartImage img2 = new ChartImage("pie");
        grid.setWidget(0, 1, img2);
        grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        ChartImage img3 = new ChartImage("pie");
        grid.setWidget(1, 0, img3);
        grid.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
        ChartImage img4 = new ChartImage("pie");
        grid.setWidget(1, 1, img4);
        grid.getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_CENTER);

        innerLayout.add(LayoutRegionConfig.CENTER, p);
    }

    public void showDefault() {
        super.showDefault();
        showStatus();
    }
}
