package com.sfeir.oauth.client;

import com.google.api.gwt.oauth2.client.Auth;
import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OAuth implements EntryPoint {

    private static final Auth AUTH = Auth.get();

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */


    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        addGoogleAuth();

        addClearTokens();
    }

    // //////////////////////////////////////////////////////////////////////////
    // AUTHENTICATING WITH GOOGLE ///////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////

    private static final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";

    // This app's personal client ID assigned by the Google APIs Console
    // (http://code.google.com/apis/console).
    private static final String GOOGLE_CLIENT_ID = "578834462784.apps.googleusercontent.com";

    // The auth scope being requested. This scope will allow the application to
    // identify who the authenticated user is.
    private static final String PLUS_ME_SCOPE = "https://www.googleapis.com/auth/plus.me";

    // Adds a button to the page that asks for authentication from Google.
    private void addGoogleAuth() {
        // Since the auth flow requires opening a popup window, it must be started
        // as a direct result of a user action, such as clicking a button or link.
        // Otherwise, a browser's popup blocker may block the popup.
        Button button = new Button("Authenticate with Google");
        button.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                final AuthRequest req = new AuthRequest(GOOGLE_AUTH_URL, GOOGLE_CLIENT_ID)
                        .withScopes(PLUS_ME_SCOPE);

                // Calling login() will display a popup to the user the first time it is
                // called. Once the user has granted access to the application,
                // subsequent calls to login() will not display the popup, and will
                // immediately result in the callback being given the token to use.
                AUTH.login(req, new Callback<String, Throwable>() {

                    public void onSuccess(String token) {
                        Window.alert("Got an OAuth token:\n" + token + "\n"
                                + "Token expires in " + AUTH.expiresIn(req) + " ms\n");
                    }


                    public void onFailure(Throwable caught) {
                        Window.alert("blzblzblzError:\n" + caught.getMessage());
                    }
                });
            }
        });
        RootPanel.get("btnGoogleAuth").add(button);
    }

    // //////////////////////////////////////////////////////////////////////////
    // CLEARING STORED TOKENS ///////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////

    // Clears all tokens stored in the browser by this library. Subsequent calls
    // to login() will result in the popup being shown, though it may immediately
    // disappear if the token has not expired.
    private void addClearTokens() {
        Button button = new Button("Clear stored tokens");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Auth.get().clearAllTokens();
                Window.alert("All tokens cleared");
            }
        });
        RootPanel.get("btnClearTokens").add(button);
    }
}
