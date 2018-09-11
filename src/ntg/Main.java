/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author OTHREE
 */
public class Main extends Application {
    
    Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        Parent root = (Parent) loader.load();
        loader.setController(layoutController.class);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root, 400, 500);
       
//        TrayIconClass classt = new TrayIconClass();
//        TrayIconClass.mainTrayIcon.addActionListener(e -> {
//            System.out.println("ssssssss");
//            Platform.runLater(this::stageShow);
//        });
        stage.setTitle("NTG");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource("image.png").toExternalForm()));
        primaryStage = stage;
        stage.show();
        
       
        
        
    }
    
    void stageShow() {
        
        primaryStage.show();
        primaryStage.toFront();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
