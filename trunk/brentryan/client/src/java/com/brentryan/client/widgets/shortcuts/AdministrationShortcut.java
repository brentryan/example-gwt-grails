/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brentryan.client.widgets.shortcuts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.Connection;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.JsonReaderConfig;
import com.gwtext.client.data.Node;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StoreLoadConfig;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGrid;
import com.gwtext.client.widgets.grid.Grid;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.TreePanelConfig;
import com.gwtext.client.widgets.tree.event.TreeNodeListenerAdapter;
import com.brentryan.client.widgets.MainPanel;
import java.util.Date;

/**
 *
 * @author bryan
 */
public class AdministrationShortcut extends AbstractShortcut {

    private static final AdministrationShortcutConstants CONSTANTS = (AdministrationShortcutConstants) GWT.create(AdministrationShortcutConstants.class);
    private static final String HOST_SIMULATOR_NODE_ID = "hostSimulator";
    private static final String USER_SETUP_NODE_ID = "userSetupNode";

    public AdministrationShortcut(MainPanel mainPanel) {
        super(mainPanel);

        this.treePanel = new TreePanel("admin-tree", new TreePanelConfig() {

            {
                setContainerScroll(true);
                setAnimate(true);
                setRootVisible(false);
            }
        });
        root = new TreeNode(CONSTANTS.AdministrationTitle());
        treePanel.setRootNode(root);
        treePanel.render();

        root.expand(false, true);

        TreeNode userSetupNode = this.addShortcut(USER_SETUP_NODE_ID, CONSTANTS.UserSetupTitle(), false, true);
        userSetupNode.addTreeNodeListener(new UserSetupListener());
        setDefaultNode(userSetupNode);

        TreeNode hostSimulatorNode = this.addShortcut(HOST_SIMULATOR_NODE_ID, CONSTANTS.HostSimulatorTitle(), true, true);
        hostSimulatorNode.addTreeNodeListener(new HostSimulatorListener());

        VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");
        vp.add(treePanel);
        initWidget(vp);
        this.setTitle(CONSTANTS.AdministrationTitle());
    }

    private class UserSetupListener extends TreeNodeListenerAdapter {

        public void onClick(Node node, EventObject event) {
            showUserSetup();
        }
    }

    private void showUserSetup() {
        BorderLayout innerLayout = this.getMainPanel().getInnerLayout();

        //clear out the old content
        innerLayout.remove(LayoutRegionConfig.CENTER, MainPanel.CONTENT_ID);
        ContentPanel p = new ContentPanel(MainPanel.CONTENT_ID);

        HttpProxy proxy = new HttpProxy("/server/user/list", Connection.GET);
        JsonReader reader = new JsonReader(new JsonReaderConfig() {

            {
                setRoot("users");
                setTotalProperty("totalCount");
            }
        }, new RecordDef(new FieldDef[]{
            new StringFieldDef("username"),
            new StringFieldDef("password"),
            new BooleanFieldDef("enabled")
        }));

        Store store = new Store(proxy, reader);

        ColumnModel columnModel = new ColumnModel(new ColumnConfig[]{
            new ColumnConfig() {

                {
                    setHeader("User Name");
                    setDataIndex("username");
                    setEditor(new GridEditor(new TextField(new TextFieldConfig() {

                        {
                            setAllowBlank(false);
                        }
                    })));
                }
            },
            new ColumnConfig() {

                {
                    setHeader("Password");
                    setDataIndex("password");
                    //use custom checkbox renderer
                    setRenderer(new Renderer() {

                        public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store) {
                            return "*****";
                        }
                    });
                    setEditor(new GridEditor(new TextField(new TextFieldConfig() {

                        {
                            setPassword(true);
                            setAllowBlank(false);
                        }
                    })));
                }
            },
            new ColumnConfig() {

                {
                    setHeader("Enabled?");
                    setDataIndex("enabled");

                    //use custom checkbox renderer
                    setRenderer(new Renderer() {

                        public String render(Object value, CellMetadata cellMetadata, Record record, int rowIndex, int colNum, Store store) {
                            boolean checked = ((Boolean) value).booleanValue();
                            return "<img class=\"checkbox\" src=\"js/ext/resources/images/default/menu/" + (checked ? "checked.gif" : "unchecked.gif") + "\"/>";
                        }
                    });
                }
            }
        });
        columnModel.setDefaultSortable(true);

        final EditorGrid grid = new EditorGrid("grid-example2", "100%", "100%", store, columnModel);

        grid.addGridCellListener(new GridCellListenerAdapter() {

            public void onCellClick(Grid g, int rowIndex, int colIndex, EventObject e) {
                if (grid.getColumnModel().getDataIndex(colIndex).equals("enabled") && e.getTarget(".checkbox", 1) != null) {
                    Record record = grid.getStore().getAt(rowIndex);
                    record.set("enabled", !record.getAsBoolean("enabled"));
                }
            }
        });

        grid.render();


        store.load(
                new StoreLoadConfig() {

                    {
                        setParams(new UrlParam[]{new UrlParam("rnd", new Date().getTime() + "")});
                    }
                });

        p.add(grid);

        innerLayout.add(LayoutRegionConfig.CENTER, p);
    }

    private class HostSimulatorListener extends TreeNodeListenerAdapter {

        public void onClick(Node node, EventObject event) {
            showUserSetup();
        }
    }

    public void showDefault() {
        super.showDefault();
        showUserSetup();
    }
}
