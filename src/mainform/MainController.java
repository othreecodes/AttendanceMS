/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.jfoenix.controls.*;
import database.DBTransactions;
import fingerprint.FingerPrint;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Attendance;
import models.Role;
import models.StudentData;
import models.courses;
import org.controlsfx.control.StatusBar;
import printout.SenateList;
import printout.StudentList;
import user.User;

/**
 * FXML Controller class
 *
 * @author OTHREE
 */
public class MainController  implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StatusBar status;
    String role;
    @FXML
    StackPane root;

    @FXML
    JFXDialogLayout dialogLayout;
    @FXML
    Label dialogHeading;

    private DBTransactions database;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Role r = new Role();
        role = r.get(User.role).toString();
        if (User.role.equals("st")) {
            try {
                createStudentEnvironment();
            } catch (Exception e) {
            }

        } 
        try {
            status.getRightItems().addAll(new Label("User: " + User.firstname + " " + User.lastname), new Label("\t\t\t"), new Separator(Orientation.VERTICAL), new Label("\t\t\t"), new Label("Role: " + role));
        } catch (Exception e) {
        }
        
        try {
            database = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void close(ActionEvent event) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setHeaderText("Exit ?");
        dialog.setContentText("Are you sure you want to exit ?");
        dialog.setTitle("Quit?");
        dialog.show();

        dialog.resultProperty().addListener(e -> {
            if (dialog.getResult() == ButtonType.OK) {
                System.exit(0);
            }

        });

    }

    @FXML
    void registerStudent(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/register_students.fxml"));

        //loader.setController(MainController.class);
        Parent root = (Parent) loader.load();

//            JFXDecorator decor = new JFXDecorator(stage, root);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//         scene.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
        //scene.setFill(Color.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource("image.png").toExternalForm()));
        //stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);

        stage.setAlwaysOnTop(true);
        stage.setTitle("Student Record");
        stage.centerOnScreen();
        stage.show();
        

    }

    @FXML
    void coursesReg(ActionEvent event) throws IOException, SQLException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/course_reg_student.fxml"));

        //loader.setController(MainController.class);
        Parent root = (Parent) loader.load();

//            JFXDecorator decor = new JFXDecorator(stage, root);
        Scene scene = new Scene(root, 456, 661);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
        //scene.setFill(Color.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource("image.png").toExternalForm()));
        //stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);

        stage.setAlwaysOnTop(true);
        stage.setTitle("Course Registration for '" + new DBTransactions().getCurrent());
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    void registerCourses(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/register_courses.fxml"));

        //loader.setController(MainController.class);
        Parent root = (Parent) loader.load();

//            JFXDecorator decor = new JFXDecorator(stage, root);
        Scene scene = new Scene(root, 700, 700);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
        //scene.setFill(Color.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource("image.png").toExternalForm()));
        //stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);

        stage.setAlwaysOnTop(true);
        stage.setTitle("Course Listing");
        stage.show();
        stage.centerOnScreen();

    }

    @FXML
    private void showDialog(ActionEvent event) {
        try {

           
            // dialog.setPrefSize(658, 400);
            dialog.resize(658, 400);
            // dialog.autosize();
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/session_config.fxml"));

            parent = (Parent) loader.load();
            parent.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
            loader.setController(MainController.class);
            //dialog.setContent();
            dialogHeading.setPrefWidth(dialog.getWidth());
            dialogHeading.autosize();
            dialogLayout.getBody().clear();
            dialogLayout.getBody().add(parent);
            // dialogHeading.setStyle("-fx-background-color:black;");
            dialogHeading.setText("SESSION CONFIGURATION");

            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

            dialog.show(root);
            acceptButton.setText("CLOSE");
             acceptButton.setOnMouseClicked((e) -> {
                dialog.close();
                loader.setController(null);
            });

        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setSess(ActionEvent event) {
        System.out.println("jhagdbs ");

    }

    @FXML
    public void changePassword(ActionEvent event) {
        try {

            acceptButton.setOnMouseClicked((e) -> {
                dialog.close();
            });
            //dialog.setPrefSize(380, 400);
            dialog.resize(380, 400);
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/password_config.fxml"));

            parent = (Parent) loader.load();

            loader.setController(sessionController.class);
            //dialog.setContent();
            dialogHeading.setPrefWidth(dialog.getPrefWidth());
            dialogHeading.autosize();
            dialogLayout.getBody().clear();
            dialogLayout.getBody().add(parent);
            // dialogHeading.setStyle("-fx-background-color:black;");
            dialogHeading.setText("CHANGE PASSWORD");

            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

            dialog.show(root);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    StackPane passwordContainer;
    private JFXPasswordField currentP;

    private JFXPasswordField newP;

    private JFXPasswordField confP;

    public void change(ActionEvent event) {
        try {
            registercon();
            String message = new DBTransactions().changePassword(User.username, currentP.getText(), newP.getText(), confP.getText());

            if (!message.equals("done")) {
                errorBar.show(message, 1500);
            } else {
                errorBar.show("password changed sucessfully", 2000);
                currentP.clear();
                newP.clear();
                confP.clear();

            }
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.gc();

    }
    int regi = 0;

    public void registercon() {
        if (regi == 0) {
            errorBar.registerSnackbarContainer(passwordContainer);
            errorBar.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());

            regi++;
        }
    }

    private JFXSnackbar errorBar;

    @FXML
    private JFXButton acceptButton;

    @FXML
    private JFXDialog dialog;

    @FXML
    private JFXButton regStudButton;
    @FXML
    private JFXButton sessButton;
    @FXML
    private JFXButton gradButton;
    @FXML
    private JFXButton compResultButton;
    @FXML
    private Menu reportMenu;
    @FXML
    private MenuItem resultItem;
    @FXML
    private MenuItem registerItem;
    @FXML
    private MenuItem sessItem;
    @FXML
    private MenuItem coursesItem;
    @FXML
    private MenuItem usersItem;

    private void createStudentEnvironment() {
        regStudButton.setDisable(true);
        //compResultButton.setDisable(true);
        sessButton.setDisable(true);
        gradButton.setDisable(true);
        reportMenu.setDisable(true);
        //resultItem.setDisable(true);
        registerItem.setDisable(true);
        sessItem.setDisable(true);
        coursesItem.setDisable(true);
        usersItem.setDisable(true);
    }

    @FXML
    void setResults(ActionEvent event) throws IOException, SQLException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/results.fxml"));

        //loader.setController(MainController.class);
        Parent root = (Parent) loader.load();

//            JFXDecorator decor = new JFXDecorator(stage, root);
        Scene scene = new Scene(root, 600, 400);
        //  scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
        //scene.setFill(Color.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource("image.png").toExternalForm()));
        //stage.setMaximized(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);

        stage.setAlwaysOnTop(true);
        stage.setTitle("Results for '" + new DBTransactions().getCurrent());
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    void studentList(ActionEvent event) {
        ObservableList<Integer> levels = FXCollections.observableArrayList();
        levels.addAll(100, 200, 300, 400, 500, 600);

        ChoiceDialog<Integer> levelDialog = new ChoiceDialog(100, levels);

        levelDialog.setHeaderText("Choose level");
        levelDialog.setContentText("Select Level of students to Print");
        levelDialog.setTitle("Student List");
        levelDialog.show();

        levelDialog.resultProperty().addListener(e -> {
            System.out.println("here baby");
            StudentList std = new StudentList(levelDialog.getResult());

        });

    }

    @FXML
    void nonGradStudList(ActionEvent event) throws SQLException {
        boolean closed = new DBTransactions().checkSessionClosed();
        if (closed == false) {
            //   alert("Non-Graduating Student List", "Sorry you can not generate this list\nwhile the session is still open", Alert.AlertType.ERROR);

            dolevelPrintClosed();
        } else {
            dolevelPrint();
        }
    }

    @FXML
    void printTranscript(ActionEvent event) throws SQLException {
        boolean closed = new DBTransactions().checkSessionClosed();
        if (closed == false) {
            alert("Generate Transcript", "Sorry you can not generate Transcript\nwhile the session is still open", Alert.AlertType.ERROR);
            dogen();
        } else {
            dogen();
        }

    }

    public void dogen() {
        try {

            acceptButton.setOnMouseClicked((e) -> {
                dialog.close();
            });

            // dialog.setPrefSize(658, 400);
            dialog.resize(430, 300);
            // dialog.autosize();
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/transcript.fxml"));

            parent = (Parent) loader.load();
            parent.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
            loader.setController(TranscriptController.class);
            //dialog.setContent();
            dialogHeading.setPrefWidth(dialog.getWidth());
            dialogHeading.autosize();
            dialogLayout.getBody().clear();
            dialogLayout.getBody().add(parent);
            // dialogHeading.setStyle("-fx-background-color:black;");
            dialogHeading.setText("GENERATE TRANSCRIPT");

            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

            dialog.show(root);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Dialog alert(String title, String message, Alert.AlertType type) {
        Dialog dialog = new Alert(type);
        dialog.setTitle(title);
        dialog.setContentText(message);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
        return dialog;
    }
    
    Dialog input(String title, String message) {
        Dialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText(message);
        
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
        return dialog;
    }

    @FXML
    void printSenateNONG(ActionEvent event) throws SQLException {
        boolean closed = new DBTransactions().checkSessionClosed();
        if (closed == false) {
            alert("Generate Transcript", "Sorry you can not generate Transcript\nwhile the session is still open", Alert.AlertType.ERROR);
            dogen();
        } else {
            dogen();
        }

    }

    private void dolevelPrint() {

    }

    private void dolevelPrintClosed() {

        ObservableList<Integer> levels = FXCollections.observableArrayList();
        levels.addAll(100, 200, 300, 400, 500, 600);

        ChoiceDialog<Integer> levelDialog = new ChoiceDialog(100, levels);

        levelDialog.setHeaderText("Choose level");
        levelDialog.setContentText("Select Level of students to Print");
        levelDialog.setTitle("Student List");
        levelDialog.show();

        levelDialog.resultProperty().addListener(e -> {
            try {

                ObservableList<StudentData> stud = new DBTransactions().getStudentDataForLevel(levelDialog.getResult());
                SenateList list = new SenateList(stud);
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    @FXML
    public void createUser(ActionEvent event) {
        try {

            acceptButton.setOnMouseClicked((e) -> {
                
                dialog.close();
            });
            //dialog.setPrefSize(380, 400);
            dialog.resize(350, 400);
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/create_user.fxml"));

            parent = (Parent) loader.load();
            parent.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
            loader.setController(CreateUserController.class);
            //dialog.setContent();
            dialogHeading.setPrefWidth(dialog.getPrefWidth());
            dialogHeading.autosize();
            dialogLayout.getBody().clear();
            dialogLayout.getBody().add(parent);
            // dialogHeading.setStyle("-fx-background-color:black;");
            dialogHeading.setText("CREATE USER");

            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

            dialog.show(root);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void attendance(ActionEvent event) throws SQLException {
        ObservableList<courses> allCourses = database.getAllCourses();
        ObservableList<String> allCoursesString = FXCollections.observableArrayList();
        for (courses stc : allCourses) {
            allCoursesString.add(stc.getCode());
        }
        Dialog sessionDialog = new ChoiceDialog(allCoursesString.get(0), allCoursesString);
        sessionDialog.setHeaderText("Choose a Course from the dropdown below");
        sessionDialog.setContentText("Choose Course");
        sessionDialog.setTitle("Start Attendance Roaster");
        sessionDialog.initOwner(root.getScene().getWindow());
        sessionDialog.initModality(Modality.APPLICATION_MODAL);
        sessionDialog.show();
       

        sessionDialog.resultProperty().addListener(e -> {
            String selectedCourse = sessionDialog.getResult().toString();
//            alert("Attendance", "OYA NAA OYA "+selectedCourse, Alert.AlertType.INFORMATION);
            openAttendancePanel(selectedCourse);
        });
        
    }
    
    public void openAttendancePanel(String courseCode){
    
        try {
            

            dialog.setPrefSize(658, 400);
            dialog.resize(658, 400);
            // dialog.autosize();
            Parent parent = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainform/attendance.fxml"));

            parent = (Parent) loader.load();
            parent.getStylesheets().add(getClass().getResource("jfoenix-components.css").toExternalForm());
            loader.setController(AttendanceController.class);
            Attendance.setCourseCode(courseCode);
            
            
            database.startAttendance(courseCode);
            //dialog.setContent();
            dialogHeading.setPrefWidth(dialog.getWidth());
            dialogHeading.autosize();
            dialogLayout.getBody().clear();
            dialogLayout.getBody().add(parent);
            // dialogHeading.setStyle("-fx-background-color:black;");
            dialogHeading.setText(courseCode+" ATTENDANCE");

            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);

            dialog.show(root);
            
            
            acceptButton.setText("END ATTENDANCE"); 
            acceptButton.setOnMouseClicked((e) -> {
                try {
                    loader.setController(null);
                    database.endAttendance(Attendance.getId());
                    dialog.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    

}
