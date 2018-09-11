/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.DBTransactions;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Role;
import models.StudentData;
import models.courses;
import user.User;

/**
 * FXML Controller class
 *
 * @author OTHREE
 */
public class CourseRegController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXListView<String> available;

    @FXML
    private JFXListView<String> chosen;

    ObservableList<StudentData> student;
    @FXML
    private JFXComboBox<Integer> matricBox;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField moeField;

    @FXML
    private JFXTextField levelField;
    @FXML
    private JFXTextField totalUnitsField;
    @FXML
    private JFXTextArea detailsArea;
    @FXML
    private Label regLabel;
    @FXML
    private StackPane root;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton sendButton;

    @FXML
    private JFXButton recieveButton;
    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton addButton;

    String session;
    ObservableList<String> studentnames;
    ObservableList<courses> courses;
    ObservableList<String> course;
    ObservableList<Integer> matricNumber;
    JFXSnackbar toastMessage;
    DBTransactions database;
    String role;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Role r = new Role();
        role = r.get(User.role).toString();

        try {
            database = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(CourseRegController.class.getName()).log(Level.SEVERE, null, ex);
        }
        toastMessage = new JFXSnackbar(root);
        try {
            student = database.getAllData();
            //chosen.depthProperty().set(1);
            //available.depthProperty().set(1);

            courses = database.getAllCourses();

            course = FXCollections.observableArrayList();

            courses.stream().forEach((cou) -> {
                course.add(cou.getCode());
            });

            studentnames = FXCollections.observableArrayList();
            student.stream().forEach((stu) -> {
                studentnames.add(stu.getSurname() + " " + stu.getFirstname());
            });
            matricNumber = FXCollections.observableArrayList();
            student.stream().forEach((stu) -> {
                matricNumber.add(stu.getMatricNumber());
            });
            matricBox.setValue(matricNumber.get(0));
            setContent(0);
            matricBox.setOnAction(e -> {
                int number = matricBox.getSelectionModel().getSelectedIndex();
                System.out.println(number);
                available.getItems().clear();
                chosen.getItems().clear();
                detailsArea.clear();
                setContent(number);
            });

            matricBox.setItems(matricNumber);

        } catch (SQLException ex) {
            Logger.getLogger(CourseRegController.class.getName()).log(Level.SEVERE, null, ex);
        }

        available.getSelectionModel().selectedItemProperty().addListener(e -> {
            //chosen.getSelectionModel().clearSelection();
            try {
                String selcourse = available.getSelectionModel().getSelectedItem();
                courseDetails = "";
                courses cou = database.getSingleCourse(selcourse);
                courseDetails += selcourse + ": " + cou.getTitle() + "\n";
                courseDetails += "Units: " + cou.getUnit() + "\n";
                courseDetails += "Type: " + new con().get(cou.getMode()) + "\n";
                courseDetails += "Pre-requisite: " + cou.getPrereq();
                detailsArea.setText(courseDetails);
            } catch (SQLException ex) {
                Logger.getLogger(CourseRegController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        chosen.getSelectionModel().selectedItemProperty().addListener(e -> {
            //available.getSelectionModel().clearSelection();
            try {
                String selcourse = chosen.getSelectionModel().getSelectedItem();
                courseDetails = "";
                courses cou = database.getSingleCourse(selcourse);
                courseDetails += selcourse + ": " + cou.getTitle() + "\n";
                courseDetails += "Units: " + cou.getUnit() + "\n";
                courseDetails += "Type: " + new con().get(cou.getMode()) + "\n";
                courseDetails += "Pre-requisite: " + cou.getPrereq();
                detailsArea.setText(courseDetails);
            } catch (SQLException ex) {
                Logger.getLogger(CourseRegController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        if (User.role.equals("st")) {
            try {
                createStudentEnvironment();
            } catch (Exception e) {
            }

        }
    }
    ObservableList<courses> studentCourses;

    public void setContent(int index) {
        totalUnits = 0;
        totalUnitsField.setText(String.valueOf(totalUnits));
        try {
            nameField.setText(studentnames.get(index));
            moeField.setText(student.get(index).getMoe());
            levelField.setText(student.get(index).getLos());
            studentCourses = database.getStudentCourses(matricBox.getValue());
            ObservableList<String> courseCodes = FXCollections.observableArrayList();

            for (courses stc : studentCourses) {
                courseCodes.add(stc.getCode());
                totalUnits += stc.getUnit();
            }
            if (!courseCodes.isEmpty()) {
                chosen.setItems(courseCodes);
                regLabel.setText("registered");
                regLabel.setStyle("-fx-text-fill:green;");
                available.setDisable(true);
                sendButton.setDisable(true);
                recieveButton.setDisable(true);
                saveButton.setDisable(true);
                totalUnitsField.setText(String.valueOf(totalUnits));
                available.getItems().clear();
            } else {
                available.setDisable(false);
                recieveButton.setDisable(false);
                sendButton.setDisable(false);
                saveButton.setDisable(false);
                chosen.setDisable(false);
                regLabel.setText("unregistered");
                regLabel.setStyle("-fx-text-fill:red;");
                studentCourses = database.getStudentCoursesForLevel(Integer.parseInt(student.get(index).getLos()));
                courseCodes = FXCollections.observableArrayList();
                for (courses stc : studentCourses) {
                    courseCodes.add(stc.getCode());
                }
                available.setItems(courseCodes);
                chosen.getItems().clear();

            }

        } catch (SQLException ex) {
            Logger.getLogger(CourseRegController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (regLabel.getText().equals("unregistered")) {
            resetButton.setDisable(true);
            addButton.setDisable(false);
        } else {
            addButton.setDisable(true);
            resetButton.setDisable(false);
        }
    }

    int totalUnits;
    String courseDetails = "";

    @FXML
    void addCourse(ActionEvent event) throws SQLException {
        if (!available.getSelectionModel().isEmpty()) {
            String selcourse = available.getSelectionModel().getSelectedItem();
            available.getItems().remove(selcourse);
            chosen.getItems().add(selcourse);
            totalUnits += database.getSingleCourse(selcourse).getUnit();
            totalUnitsField.setText(String.valueOf(totalUnits));

        }
    }

    @FXML
    void removeCourse(ActionEvent event) throws SQLException {
        if (!chosen.getSelectionModel().isEmpty()) {
            String selcourse = chosen.getSelectionModel().getSelectedItem();
            chosen.getItems().remove(selcourse);
            available.getItems().add(selcourse);
            totalUnits -= database.getSingleCourse(selcourse).getUnit();
            totalUnitsField.setText(String.valueOf(totalUnits));

        }
    }

    @FXML
    void closeForm(ActionEvent event) {
        JFXButton btnsource = (JFXButton) event.getSource();
        Stage stage = (Stage) btnsource.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveCourses(ActionEvent event) throws SQLException {
        ObservableList<String> selectedCourses = chosen.getItems();
        ObservableList<courses> coursesToDatabase = FXCollections.observableArrayList();
        for (String selCou : selectedCourses) {
            courses c = database.getSingleCourse(selCou);
            coursesToDatabase.add(c);
        }
        boolean done = database.saveStudentCourses(matricBox.getValue(), coursesToDatabase);
        if (done == true) {
            toastMessage.show("Student Registered Successfully", 2000);

            int number = matricBox.getSelectionModel().getSelectedIndex();
            System.out.println(number);
            setContent(number);
            available.setDisable(true);

            saveButton.setDisable(true);
            recieveButton.setDisable(true);
            saveButton.setDisable(true);
        }

    }

    @FXML
    void resetReg(ActionEvent event) throws SQLException {
        boolean done = database.resetReg(matricBox.getValue());
        if (done == true) {
            toastMessage.show("Course Regsistration has been reset", 2000);
            int number = matricBox.getSelectionModel().getSelectedIndex();
            System.out.println(number);
            setContent(number);
        }
    }

    @FXML
    void addRegCourse(ActionEvent event) throws SQLException {
        ObservableList<courses> allCourses = database.getAllCourses();
        ObservableList<String> allCoursesString = FXCollections.observableArrayList();
        for (courses stc : allCourses) {
            allCoursesString.add(stc.getCode());
        }

        allCoursesString.removeAll(available.getItems());
        allCoursesString.removeAll(chosen.getItems());
        Dialog sessionDialog = new ChoiceDialog(allCoursesString.get(0), allCoursesString);
        sessionDialog.setHeaderText("Add a Course from the dropdown below");
        sessionDialog.setContentText("Choose Course");
        sessionDialog.setTitle("Add Course");
        sessionDialog.initOwner(root.getScene().getWindow());
        sessionDialog.initModality(Modality.APPLICATION_MODAL);
        sessionDialog.show();
       

        sessionDialog.resultProperty().addListener(e -> {
            
            available.getItems().add(0,sessionDialog.getResult().toString());
            toastMessage.show("Course Has been added", 2000);
        });

    }

    private void createStudentEnvironment() {
        
        
        matricBox.setDisable(true);
        matricBox.setValue(Integer.parseInt(User.username));
        matricBox.getSelectionModel().select(Integer.parseInt(User.username));
        int number = matricBox.getSelectionModel().getSelectedIndex();
        System.out.println(number);
        available.getItems().clear();
        chosen.getItems().clear();
        detailsArea.clear();
        setContent(number);
        resetButton.setDisable(true);
    }

    private class con extends HashMap<String, String> {

        public con() {
            this.put("C", "Compulsory");
            this.put("R", "Required");
            this.put("E", "Elective");
            this.put("Compulsory", "C");
            this.put("Required", "R");
            this.put("Elective", "E");
        }

        @Override
        public String get(Object key) {
            return super.get(key); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
