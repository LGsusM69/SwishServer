package com.LGsus.server.views;

import com.LGsus.server.controllers.ServerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


public class ServerGUI extends Application {
    private ServerController controller;
    private WebEngine webEngine;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        webEngine = webView.getEngine();

        webEngine.load(getClass().getResource("/gui/index.html").toExternalForm());

        controller = new ServerController(this);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                System.out.println("Page loaded successfully");

                JSObject window = (JSObject) webEngine.executeScript("window");

                window.setMember("app", controller);

                webEngine.executeScript("console.log('Java object bound as app:', app);");
            }
        });
        webEngine.setOnAlert(event -> {
            System.out.println("JavaScript alert: " + event.getData());
        });

// Add a listener for JavaScript errors
        webEngine.setOnError(event -> {
            System.err.println("JavaScript error: " + event.getMessage());
        });

// Inject a custom console.log function to redirect output to Java
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                // Override console.log in JavaScript
                webEngine.executeScript(
                        "console.log = function(message) {" +
                                "    alert(message);" +  // Send the log to Java using the alert mechanism
                                "};"
                );
            }
        });


        primaryStage.setTitle("Swish Server");
        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.show();
    }
    public void addClient(String nickName, String ipAddress) {
        Platform.runLater(() -> {
            String escapedNickName = nickName.replace("'", "\\'");
            //String escapedIpAddress = ipAddress.replace("'", "\\'");
            String escapedIpAddress = ipAddress.replace("/", "");
            webEngine.executeScript("addClient('" + escapedNickName + "', '" + escapedIpAddress + "');");
        });
    }

    public void removeClient(String nickName) {
        Platform.runLater(() -> {
            webEngine.executeScript("removeClient('" + nickName + "')");
        });
    }
}
