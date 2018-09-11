/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.jfoenix.controls.JFXComboBox;
import database.DBTransactions;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.StudentData;
import models.courses;
import printout.Transcript;

/**
 * FXML Controller class
 *
 * @author OTHREE
 */
public class TranscriptController implements Initializable {
@FXML
    private JFXComboBox<Integer> levelBox;

    @FXML
    private JFXComboBox<Integer> matricBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
 
        levelBox.getItems().addAll(100, 200, 300, 400, 500, 600);
         levelBox.setOnAction(e->{
            try {
                ObservableList<Integer> matrics = new DBTransactions().getStudentInLevel(levelBox.getValue());
                matricBox.setItems(matrics);
            } catch (SQLException ex) {
                Logger.getLogger(TranscriptController.class.getName()).log(Level.SEVERE, null, ex);
            }
         
         });
    }  
    
    
    @FXML
    void generate(ActionEvent event) throws SQLException {

        ObservableList<courses> studentCourses = new DBTransactions().getStudentCourses(matricBox.getValue());
        StudentData data = new DBTransactions().getDataForStudent(matricBox.getValue());
        Transcript re = new Transcript(data,studentCourses);
        
    }

    
}
