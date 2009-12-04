package com.brentryan.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.QuickTips;
import com.gwtext.client.widgets.form.Field;
import com.brentryan.client.handler.AuthenticationHandler;
import com.brentryan.client.handler.LogoutHandler;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint
{

    private static final MainConstants CONSTANTS = (MainConstants) GWT.create(MainConstants.class);
    
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
        
    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
        Field.setMsgTarget("side");
        QuickTips.init();

        try
        {
            RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/server/user/login");
            rb.setTimeoutMillis(30000);
            rb.sendRequest(null, new AuthenticationHandler());
        }
        catch (RequestException e)
        {
            GWT.log("Error while checking for authentication of user", e);
        }
    }

    public static void logout()
    {
        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, "/server/user/logout");
        rb.setTimeoutMillis(30000);

        try
        {
            final ExtElement extElement = new ExtElement(RootPanel.getBodyElement());
            extElement.mask(CONSTANTS.LogoutMask(), "x-mask-loading");
            rb.sendRequest(null, new LogoutHandler(extElement));
        }
        catch (RequestException e)
        {
            GWT.log("Error while logging out user", e);
        }
    }
    
    public native static void refreshBrowser()
    /*-{
     $wnd.location.reload();
     }-*/;

}
