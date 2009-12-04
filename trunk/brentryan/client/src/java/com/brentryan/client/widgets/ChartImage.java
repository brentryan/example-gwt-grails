/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brentryan.client.widgets;

import com.brentryan.client.widgets.dialogs.DialogConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.Image;
import com.gwtext.client.widgets.MessageBox;

/**
 *
 * @author bryan
 */
public class ChartImage extends Image implements WindowResizeListener {

    private static final DialogConstants DIALOG_CONSTANTS = (DialogConstants) GWT.create(DialogConstants.class);
    private static final ChartImageMessages MESSAGES = (ChartImageMessages) GWT.create(ChartImageMessages.class);
    
    public ChartImage(final String type) {
        super();

        Window.addWindowResizeListener(this);
        int width = (Window.getClientWidth() - 175) / 2;
        int height = (Window.getClientHeight() - 50) / 2;
        setWidth(Integer.toString(width));
        setHeight(Integer.toString(height));

        RequestCallback callback = new RequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                String chartName = (String) response.getText();
                String imageUrl = "./displayChart?filename=" + chartName;
                setUrl(imageUrl);
            }

            public void onError(Request request, Throwable response) {
                MessageBox.alert(DIALOG_CONSTANTS.ErrorTitle(), MESSAGES.ChartCreationError(type));
            }
        };

        try {
            RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/server/chart/" + type + "?width=" + width + "&height=" + height);
            rb.setTimeoutMillis(30000);
            rb.sendRequest(null, callback);
        } catch (RequestException e) {
            GWT.log("Error while creating chart", e);
        }
    }

    public void onWindowResized(int width, int height) {
        setWidth(Integer.toString((width - 175) / 2));
        setHeight(Integer.toString((height - 50) / 2));
    }
}
