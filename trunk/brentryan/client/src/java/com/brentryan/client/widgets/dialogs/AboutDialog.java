package com.brentryan.client.widgets.dialogs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ButtonConfig;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class AboutDialog extends Composite {

    private static final AboutDialogConstants CONSTANTS = (AboutDialogConstants) GWT.create(AboutDialogConstants.class);
    private static final DialogConstants DIALOG_CONSTANTS = (DialogConstants) GWT.create(DialogConstants.class);
    
    private LayoutDialog aboutDialog = new LayoutDialog(new LayoutDialogConfig() {

        {
            setTitle(CONSTANTS.About());
            setModal(true);
            setWidth(500);
            setHeight(300);
            setShadow(true);
            setMinHeight(300);
            setMinHeight(300);
        }
        }, new LayoutRegionConfig() {

        {
            setAutoScroll(true);
        }
        });

    public AboutDialog() {

        aboutDialog.addButton(new Button("aboutOk", new ButtonConfig() {

            {
                setText(DIALOG_CONSTANTS.Ok());
                setButtonListener(new ButtonListenerAdapter() {

                    public void onClick(Button button, EventObject e) {
                        aboutDialog.destroy(true);
                    }
                });
            }
        }));

        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "License.html");
        rb.setTimeoutMillis(30000);

        try {
            rb.sendRequest(null, new RequestCallback() {

                public void onError(Request request, Throwable exception) {
                    aboutDialog.destroy(true);
                    MessageBox.alert(DIALOG_CONSTANTS.ErrorTitle(), CONSTANTS.LicenseError());
                }

                public void onResponseReceived(Request request, Response response) {
                    // add content to the center region
                    BorderLayout layout = aboutDialog.getLayout();
                    ContentPanel contentPanel = new ContentPanel();
                    contentPanel.add(new HTML(response.getText()));
                    layout.add(contentPanel);
                    aboutDialog.show();
                }
            });
        } catch (RequestException e) {
            GWT.log("Error while retrieving BrentRyan license", e);
        }

        initWidget(aboutDialog);
    }

    public void show() {
        if (aboutDialog != null) {
            aboutDialog.show();
        }
    }

    public void hide() {
        if (aboutDialog != null) {
            aboutDialog.hide();
        }
    }

    public void destroy() {
        if (aboutDialog != null) {
            aboutDialog.destroy();
        }
    }
}
