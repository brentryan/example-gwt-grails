package com.brentryan.client.widgets.menus;

import com.brentryan.client.widgets.dialogs.AboutDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.SplitButtonConfig;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarMenuButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.ItemConfig;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuConfig;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;
import com.brentryan.client.Main;

public class BrentRyanMenu extends Composite {

    private static final BrentRyanMenuConstants CONSTANTS = (BrentRyanMenuConstants) GWT.create(BrentRyanMenuConstants.class);
    private Toolbar tb = new Toolbar(Ext.generateId());

    public BrentRyanMenu() {
        Menu fileMenu = new Menu("fileMenu", new MenuConfig() {

            {
                setShadow(true);
            }
        });

        final BaseItemListenerAdapter logoutListener = new BaseItemListenerAdapter() {

            public void onClick(BaseItem item, EventObject event) {
                Main.logout();
            }
        };

        fileMenu.addSeparator();
        fileMenu.addItem(new Item(CONSTANTS.Logout(), new ItemConfig() {

            {
                setBaseItemListener(logoutListener);
            }
        }));

        ToolbarMenuButton fileMenuButton = new ToolbarMenuButton(CONSTANTS.File(), fileMenu, new SplitButtonConfig());
        tb.addButton(fileMenuButton);

        Menu toolsMenu = new Menu("toolsMenu", new MenuConfig() {

            {
                setShadow(true);
            }
        });
        toolsMenu.addItem(new Item(CONSTANTS.Options(), new ItemConfig()));

        ToolbarMenuButton toolsMenuButton = new ToolbarMenuButton(CONSTANTS.Tools(), toolsMenu, new SplitButtonConfig());
        tb.addButton(toolsMenuButton);

        final BaseItemListenerAdapter aboutListener = new BaseItemListenerAdapter() {

            public void onClick(BaseItem item, EventObject event) {
                new AboutDialog();
            }
        };

        Menu helpMenu = new Menu("helpMenu", new MenuConfig() {

            {
                setShadow(true);
            }
        });
        helpMenu.addItem(new Item(CONSTANTS.About(), new ItemConfig() {

            {
                setBaseItemListener(aboutListener);
            }
        }));

        ToolbarMenuButton helpMenuButton = new ToolbarMenuButton(CONSTANTS.Help(), helpMenu, new SplitButtonConfig());
        tb.addButton(helpMenuButton);

        final ToolbarButton logoutButton = new ToolbarButton(CONSTANTS.Logout(), new ButtonConfig() {

            {
                setTooltip(CONSTANTS.LogoutTooltip());
                setButtonListener(new ButtonListenerAdapter() {

                    public void onClick(Button button, EventObject event) {
                        Main.logout();
                    }
                });
            }
        });

        tb.addFill();
        tb.addButton(logoutButton);

        initWidget(tb);
    }
}
