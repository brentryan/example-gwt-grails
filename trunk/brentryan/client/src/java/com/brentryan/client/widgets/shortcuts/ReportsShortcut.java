/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brentryan.client.widgets.shortcuts;

import com.google.gwt.core.client.GWT;
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
import com.brentryan.client.widgets.MainPanel;

/**
 *
 * @author bryan
 */
public class ReportsShortcut extends AbstractShortcut {

    private static final ReportsShortcutConstants CONSTANTS = (ReportsShortcutConstants) GWT.create(ReportsShortcutConstants.class);
    private static final String JOB_LIST_NODE_ID = "jobList";

    public ReportsShortcut(MainPanel mainPanel) {
        super(mainPanel);

        this.treePanel = new TreePanel("reports-tree", new TreePanelConfig() {

            {
                setContainerScroll(true);
                setAnimate(true);
                setRootVisible(false);
            }
        });
        root = new TreeNode(CONSTANTS.ReportsTitle());
        treePanel.setRootNode(root);
        treePanel.render();

        root.expand(false, true);

        TreeNode jobListNode = this.addShortcut(JOB_LIST_NODE_ID, CONSTANTS.JobListTitle(), false, true);
        jobListNode.addTreeNodeListener(new JobListListener());
        setDefaultNode(jobListNode);

        VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");
        vp.add(treePanel);
        initWidget(vp);
        this.setTitle(CONSTANTS.ReportsTitle());
    }

    private class JobListListener extends TreeNodeListenerAdapter {

        public void onClick(Node node, EventObject event) {
            showJobList();
        }
    }

    private void showJobList() {
        BorderLayout innerLayout = this.getMainPanel().getInnerLayout();

        //clear out the old content
        innerLayout.remove(LayoutRegionConfig.CENTER, MainPanel.CONTENT_ID);
        ContentPanel p = new ContentPanel(MainPanel.CONTENT_ID);

        innerLayout.add(LayoutRegionConfig.CENTER, p);

    }

    public void showDefault() {
        super.showDefault();
        showJobList();
    }
}
