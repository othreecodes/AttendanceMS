/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.DBTransactions;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import models.courses;
import models.result;
import user.User;

/**
 * FXML Controller class
 *
 * @author OTHREE
 */
public class ResultsController implements Initializable {

    @FXML
    private JFXComboBox<Integer> levelBox;

    @FXML
    private JFXComboBox<String> courseBox;

    @FXML
    private JFXComboBox<Integer> matricBox;

    @FXML
    private JFXTextField unitField;

    @FXML
    private JFXTextField scoreField;

    @FXML
    private JFXTextField GPField;

    @FXML
    private JFXTextField WGPField;

    @FXML
    private JFXTextField remarkField;

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXButton updateButton;

    @FXML
    private JFXButton cancelButton;

    DBTransactions database;
    int usermatric = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        scoreField.textProperty().addListener((ov, old, nw) -> {
            if (nw.matches("^[0-9]*\\.?[0-9]*$")) {
                scoreField.setText(nw);

            } else {
                scoreField.setText(old);
                // old.equals(index);
            }

        });

        try {
            // TODO
            database = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (User.role.equals("st")) {
            try {
                CreateUserEnvironment();
            } catch (SQLException ex) {
                Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            createNormalEnviromnent();

        }
    }
    int index = 0;

    private void CreateUserEnvironment() throws SQLException {
        updateButton.setDisable(true);
        usermatric = Integer.parseInt(User.username);
        matricBox.setValue(usermatric);
        System.out.println(usermatric);
        ObservableList<courses> studentCourses = database.getStudentCourses(usermatric);
        String Level = database.getDataForStudent(usermatric).getLos();

        int studentLevel = Integer.parseInt(Level);
        levelBox.setValue(studentLevel);
        for (courses cou : studentCourses) {
            courseBox.getItems().add(cou.getCode());
        }
        courseBox.getSelectionModel().select(index);
        unitField.setText(String.valueOf(studentCourses.get(index).getUnit()));
        scoreField.setText(String.valueOf(studentCourses.get(index).getScore()));
        result r = new result();
        r = r.result(studentCourses.get(index).getUnit(), studentCourses.get(index).getScore());
        WGPField.setText(String.valueOf(r.getWgp()));
        remarkField.setText(String.valueOf(r.getRemark()));
        GPField.setText(String.valueOf(r.getGradepoint()));

        courseBox.getSelectionModel().selectedItemProperty().addListener(e -> {
            index = courseBox.getSelectionModel().getSelectedIndex();
            unitField.setText(String.valueOf(studentCourses.get(index).getUnit()));
            scoreField.setText(String.valueOf(studentCourses.get(index).getScore()));
            result re = new result();
            re = re.result(studentCourses.get(index).getUnit(), studentCourses.get(index).getScore());
            WGPField.setText(String.valueOf(re.getWgp()));
            remarkField.setText(String.valueOf(re.getRemark()));
            GPField.setText(String.valueOf(re.getGradepoint()));

        });

    }

    @FXML
    void updateAction(ActionEvent event) {
        if (updateButton.getText().equals("UPDATE")) {
            updateButton.setText("SAVE");
            scoreField.setEditable(true);
            scoreField.clear();
            cancelButton.setDisable(false);
        } else {
            saveGrade();
            scoreField.setEditable(false);
            updateButton.setText("UPDATE");

        }

    }

    private void saveGrade() {

        try {
            database.updateStudentCourse(matricBox.getValue(), courseBox.getValue(), Integer.parseInt(scoreField.getText()));
            courses studentCourse = database.getStudentCourses(matricBox.getValue(), courseBox.getValue());
            unitField.setText(String.valueOf(studentCourse.getUnit()));
            scoreField.setText(String.valueOf(studentCourse.getScore()));
            result re = new result();
            re = re.result(studentCourse.getUnit(), studentCourse.getScore());
            WGPField.setText(String.valueOf(re.getWgp()));
            remarkField.setText(String.valueOf(re.getRemark()));
            GPField.setText(String.valueOf(re.getGradepoint()));
        } catch (SQLException ex) {
            Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createNormalEnviromnent() {

        levelBox.getItems().addAll(100, 200, 300, 400, 500, 600);

        levelBox.setOnAction(e -> {
            unitField.clear();
            scoreField.clear();
            WGPField.clear();
            remarkField.clear();
            GPField.clear();
            try {
                ObservableList<courses> list = database.getStudentCoursesForLevel(levelBox.getValue());
                courseBox.getItems().clear();
                for (courses cou : list) {
                    courseBox.getItems().add(cou.getCode());
                }

            } catch (SQLException ex) {
                Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        courseBox.setOnAction(e -> {
            unitField.clear();
            scoreField.clear();
            WGPField.clear();
            remarkField.clear();
            GPField.clear();
            try {
                ObservableList<Integer> list = database.getStudentOfferingCourse(courseBox.getValue());
                matricBox.getItems().clear();
                matricBox.setItems(list);

            } catch (SQLException ex) {
                Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        matricBox.setOnAction(e -> {
            unitField.clear();
            scoreField.clear();
            WGPField.clear();
            remarkField.clear();
            GPField.clear();
            try {
                courses studentCourse = database.getStudentCourses(matricBox.getValue(), courseBox.getValue());
                unitField.setText(String.valueOf(studentCourse.getUnit()));
                scoreField.setText(String.valueOf(studentCourse.getScore()));
                result re = new result();
                re = re.result(studentCourse.getUnit(), studentCourse.getScore());
                WGPField.setText(String.valueOf(re.getWgp()));
                remarkField.setText(String.valueOf(re.getRemark()));
                GPField.setText(String.valueOf(re.getGradepoint()));

            } catch (SQLException ex) {
                Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }
    
    @FXML
    void closeForm(ActionEvent event) {
        JFXButton btnsource = (JFXButton) event.getSource();
        Stage stage = (Stage) btnsource.getScene().getWindow();
        stage.close();
    }

}
