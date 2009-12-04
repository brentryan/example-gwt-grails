/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brentryan.client.widgets;

import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.layout.NestedLayoutPanel;

/**
 *
 * @author bryan
 */
public class MainPanel {

    private NestedLayoutPanel nestedLayoutPanel;
    private BorderLayout innerLayout;
    public static final String CONTENT_ID = "mainContent";

    public MainPanel() {

        innerLayout = new BorderLayout("100%", "100%", new LayoutRegionConfig(), new LayoutRegionConfig(), new LayoutRegionConfig(), new LayoutRegionConfig(), new LayoutRegionConfig() {

            {
                setAutoScroll(true);
                setHideTabs(true);
            }
        });

        nestedLayoutPanel = new NestedLayoutPanel(innerLayout, new ContentPanelConfig());
    }

    public BorderLayout getInnerLayout() {
        return innerLayout;
    }

    public NestedLayoutPanel getNestedLayoutPanel() {
        return nestedLayoutPanel;
    }
}
