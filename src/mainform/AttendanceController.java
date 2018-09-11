/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.jfoenix.controls.JFXSnackbar;
import database.DBTransactions;
import fingerprint.FingerPrint;
import static fingerprint.FingerPrint.template;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import models.Attendance;
import models.StudentData;
import ntg.TrayIconClass;

/**
 *
 * @author OTHREE
 */
public class AttendanceController extends FingerPrint implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private Label isValid;

    @FXML
    private Label matric;

    @FXML
    private Label name;

    @FXML
    private Label info;

    @FXML
    private JFXSnackbar snackbar;
    DBTransactions dataTransactions;

    ArrayList<Integer> registered;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registered = new ArrayList<>();

        try {
            dataTransactions = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        snackbar.registerSnackbarContainer(root);

    }

    @Override
    public void onImageAcquired(String string, FingerprintImage fi) {
        try {
            template = fingerprintSDK.extract(fi);
            String msg = dataTransactions.identifyFingerPrint(template);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TrayIconClass.getMainToolkit().beep();

                    try {
                        int matricNumber = Integer.parseInt(msg);
                        if (!msg.isEmpty()) {
                            StudentData student = dataTransactions.getDataForStudent(matricNumber);
                            name.setText(student.getFirstname() + " " + student.getSurname());
                            matric.setText(msg);
                            doAttendance(matricNumber, Attendance.getCourseCode());
                        } else {
                            snackbar.show("Finger print not recognised", 2000);
                        }
                    } catch (SQLException ex) {

                    } catch (NumberFormatException es) {
                        snackbar.show("Finger print not recognised", 2000);

                    }

                }
            });
        } catch (GrFingerJavaException ex) {
            Logger.getLogger(AttendanceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {

        } catch (NumberFormatException ex) {

        }
    }

    public void doAttendance(int matricNumber, String course) throws SQLException {

        if (dataTransactions.checkForStudentCourse(matricNumber, course)) {
            info.setText("Done");

            if (!registered.contains(matricNumber)) {

                registered.add(matricNumber);
                dataTransactions.markAttendance(matricNumber);
                snackbar.show("Attendance Taken for " + matricNumber, 2000);
            } else {
                snackbar.show(matricNumber + ": Attendance already taken ", 2000);

            }

        } else {
            snackbar.show("You did not register for this course", 2000);

        }

    }

}
