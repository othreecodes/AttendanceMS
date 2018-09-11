/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import database.DBTransactions;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.Role;
import models.User;

/**
 * FXML Controller class
 *
 * @author OTHREE
 */
public class CreateUserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane root;
    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField lname;

    @FXML
    private JFXComboBox<String> usertype;

    @FXML
    private JFXTextField uname;

    @FXML
    private JFXPasswordField pass;

    DBTransactions database;

    @FXML
    void create(ActionEvent event) {
        User user = new User();
        user.setFirstname(fname.getText());
        user.setLastname(lname.getText());
        user.setUsername(uname.getText());
        user.setFirsttime("y");
        user.setPassword(pass.getText());
        Role r = new Role();
        String role = r.get(usertype.getValue()).toString();
        user.setRole(role);

        try {
            boolean done = database.insertUser(user);
            if (done == true) {
                fname.clear();
                lname.clear();
                uname.clear();
                pass.clear();
                pass.clear();
                JFXSnackbar snack = new JFXSnackbar((Pane) root.getParent().getParent());
                snack.show("User created sucessfully".toUpperCase(), 3000);
            } else {
                JFXSnackbar snack = new JFXSnackbar((Pane) root.getParent().getParent());
                snack.show("an error occured".toUpperCase(), 3000);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreateUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usertype.getItems().clear();
        usertype.getItems().add("Lecturer");
        usertype.getItems().add("Sub Administrator");
        usertype.getItems().add("Super Administrator");

        try {
            // TODO
            database = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(CreateUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
