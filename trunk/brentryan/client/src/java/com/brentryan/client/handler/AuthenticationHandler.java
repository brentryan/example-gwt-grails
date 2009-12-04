/**
 * 
 */
package com.brentryan.client.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.event.FormListener;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.layout.NestedLayoutPanel;
import com.brentryan.client.Main;
import com.brentryan.client.widgets.dialogs.DialogConstants;
import com.brentryan.client.widgets.dialogs.LoginDialog;
import com.brentryan.client.widgets.menus.BrentRyanMenu;
import com.brentryan.client.widgets.shortcuts.ShortcutPanel;
import com.brentryan.client.widgets.MainPanel;
import com.brentryan.client.widgets.shortcuts.OverviewShortcut;
import com.brentryan.client.widgets.shortcuts.AdministrationShortcut;
import com.brentryan.client.widgets.shortcuts.ReportsShortcut;

/**
 * @author bryan
 * 
 */
public class AuthenticationHandler implements RequestCallback {

    private static final AuthenticationHandlerConstants CONSTANTS = (AuthenticationHandlerConstants) GWT.create(AuthenticationHandlerConstants.class);
    private static final DialogConstants DIALOG_CONSTANTS = (DialogConstants) GWT.create(DialogConstants.class);
    
    private NestedLayoutPanel mainContentPanel;

    public void onError(Request request, Throwable exception) {
        GWT.log("Error response received during authentication of user", exception);
    }

    public void onResponseReceived(Request request, Response response) {
        if (response.getStatusCode() == 200) {
            // If response was successful
            if (response.getText().equalsIgnoreCase(Main.SUCCESS)) {
                launchBrentRyan();
            } else // We have not authenticated yet or some failure occured
            {
                login();
            }
        } else {
            MessageBox.alert(DIALOG_CONSTANTS.ErrorTitle(), response.getStatusText() + " " + response.getText());
            MessageBox.alert(DIALOG_CONSTANTS.ErrorTitle(), response.getStatusCode() + " - " + CONSTANTS.UnknownError());
        }

    }

    private BorderLayout createLayout() {
        LayoutRegionConfig northConfig = new LayoutRegionConfig();

        LayoutRegionConfig eastConfig = new LayoutRegionConfig();

        LayoutRegionConfig southConfig = new LayoutRegionConfig();

        LayoutRegionConfig westConfig = new LayoutRegionConfig() {

            {
                setSplit(true);
                setTitlebar(true);
                setInitialSize(150);
                setMinSize(150);
                setMaxSize(150);
                setCollapsible(true);
                setAnimate(true);
            }
        };

        LayoutRegionConfig centerConfig = new LayoutRegionConfig();

        return new BorderLayout("100%", "100%", northConfig, southConfig, westConfig, eastConfig, centerConfig);
    }

    private void launchBrentRyan() {
        //setup main layout
        BorderLayout layout = createLayout();

        //create the main panel that shows the content details
        final MainPanel mainPanel = new MainPanel();

        //add toolbar menu at the top
        ContentPanel northContentPanel = new ContentPanel(Ext.generateId(), "Toolbar");
        VerticalPanel northPanel = new VerticalPanel();
        northPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        northPanel.setHeight("100%");
        northPanel.setWidth("100%");
        northPanel.add(new BrentRyanMenu());
        northContentPanel.add(northPanel);
        layout.add(LayoutRegionConfig.NORTH, northContentPanel);

        //create the shortcut panel for the west region
        ShortcutPanel shortcuts = new ShortcutPanel();

        OverviewShortcut o = new OverviewShortcut(mainPanel);

        shortcuts.add(o);
        shortcuts.add(new AdministrationShortcut(mainPanel));
        shortcuts.add(new ReportsShortcut(mainPanel));

        //add the shortcuts to the west region
        ContentPanel westContentPanel = new ContentPanel(Ext.generateId(), "Shortcuts");
        westContentPanel.add(shortcuts);
        layout.add(LayoutRegionConfig.WEST, westContentPanel);

        //add content details panel to center region
        mainContentPanel = mainPanel.getNestedLayoutPanel();
        layout.add(LayoutRegionConfig.CENTER, mainContentPanel);

        //Display the OverviewShortcut by default
        o.showDefault();

        //add main layout to root panel
        RootPanel.get().add(layout);
    }

    private void login() {
        final LoginDialog login = new LoginDialog();
        login.addSignInFormListener(new FormListener() {

            public boolean doBeforeAction(Form form) {
                return true;
            }

            public void onActionComplete(Form form, int httpStatus, String responseText) {
                login.destroy();
                launchBrentRyan();
            }

            public void onActionFailed(Form form, int httpStatus, String responseText) {
                MessageBox.alert(CONSTANTS.AuthenticationFailedTitle(), CONSTANTS.AuthenticationFailed());
            }

            public void onClientValidation(Form form, boolean valid) {
            }
        });
        login.show();
    }
}
