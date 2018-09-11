/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntg;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.binding.StringFormatter;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mainform.MainController;
import user.User;

/**
 *
 * @author OTHREE
 */
public class layoutController implements Initializable {

    Connection con;
    ResultSet rs;
    PreparedStatement pt;
    StackPane pane;

    private double initX;
    private double initY;
    @FXML
    private JFXTextField usernameText;

    @FXML
    private JFXPasswordField passwordText;

    String username;
    String password;
    String userType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(layoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void pressed(MouseEvent me) {
        Stage stage = (Stage) passwordText.getScene().getWindow();
        initX = me.getScreenX() - stage.getX();
        initY = me.getScreenY() - stage.getY();

    }

    @FXML
    void dragged(MouseEvent me) {
        Stage stage = (Stage) passwordText.getScene().getWindow();
        stage.setX(me.getScreenX() - initX);
        stage.setY(me.getScreenY() - initY);
    }

    @FXML
    void close(ActionEvent e) {
        System.exit(0);
    }

    @FXML
    void doLogin(ActionEvent e) throws SQLException, IOException {
        username = "olaadmin";
        password = "ola";
        con = DriverManager.getConnection("jdbc:sqlite:database");
        pt = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        pt.setString(1, username);
        pt.setString(2, password);

        rs = pt.executeQuery();
        if (rs.next()) {
            //User person = new User();
            String user = rs.getString("lastname") + " " + rs.getString("firstname");
//            TrayIconClass.getMainTrayIcon().displayMessage("NTG", "Logged in \n" + "User :" + user + "\n" + new java.util.Date(), TrayIcon.MessageType.NONE);
            User.username = rs.getString("username");
            User.password = rs.getString("password");
            User.firstname = rs.getString("firstname");
            User.lastname = rs.getString("lastname");
            User.role = rs.getString("role");
            User.firsttime = rs.getString("firsttime");

            Stage stage = (Stage) passwordText.getScene().getWindow();

            stage.close();
            stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/main_layout.fxml"));

            //loader.setController(MainController.class);
            Parent root = (Parent) loader.load();

            JFXDecorator decor = new JFXDecorator(stage, root);
            Scene scene = new Scene(decor);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMaximized(true);
            scene.getStylesheets().add(getClass().getResource("/mainform/style.css").toExternalForm());
            stage.setResizable(false);
            stage.getIcons().add(new Image(getClass().getResource("image.png").toExternalForm()));
            stage.show();

            stage.setOnCloseRequest(exie -> {
                System.exit(0);
            });
            stage.setOnHidden(eg -> System.exit(0));
        } else {
            TrayIconClass.getMainTrayIcon().displayMessage("NTG", "Invalid Login details", TrayIcon.MessageType.ERROR);

        }

        rs.close();
        pt.close();
        con.close();

    }
   

}
