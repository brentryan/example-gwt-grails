/**
 * 
 */
package com.brentryan.client.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.MessageBox;
import com.brentryan.client.Main;
import com.brentryan.client.widgets.dialogs.DialogConstants;

/**
 * @author bryan
 *
 */
public class LogoutHandler implements RequestCallback {

    private static final DialogConstants DIALOG_CONSTANTS = (DialogConstants) GWT.create(DialogConstants.class);
    private static final LogoutHandlerConstants CONSTANTS = (LogoutHandlerConstants) GWT.create(LogoutHandlerConstants.class);

    private ExtElement extElement;

    public LogoutHandler(final ExtElement extElement) {
        super();
        this.extElement = extElement;
    }

    public void onError(Request request, Throwable exception) {
        extElement.unmask();
        GWT.log("Error response received during logout of user", exception);
    }

    public void onResponseReceived(Request request, Response response) {
        extElement.unmask();

        if (response.getStatusCode() == 200) {
            // If response was successful
            if (response.getText().equalsIgnoreCase("success")) {
                Main.refreshBrowser();
            } else {
                MessageBox.alert(DIALOG_CONSTANTS.ErrorTitle(), CONSTANTS.LogoutError());
            }
        }
    }
}
