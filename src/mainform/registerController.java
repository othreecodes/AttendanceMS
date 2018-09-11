/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.Template;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.imageio.plugins.png.PNGImageReaderSpi;
import com.sun.imageio.plugins.png.PNGImageWriterSpi;
import database.DBTransactions;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import fingerprint.FingerPrint;
import static fingerprint.FingerPrint.template;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ImageWriterSpi;
import models.StudentData;
import ntg.TrayIconClass;
import org.apache.poi.util.IOUtils;

/**
 *
 * @author OTHREE
 */
public class registerController extends FingerPrint implements Initializable {

    @FXML
    private JFXTextField matric;

    @FXML
    private JFXTextField surname;

    @FXML
    private JFXTextField firstname;

    @FXML
    private JFXTextField middlename;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField nextofkin;

    @FXML
    private JFXDatePicker dob;

    @FXML
    private JFXComboBox<String> sex;

    @FXML
    private JFXComboBox<String> levelofstudy;

    @FXML
    private JFXTextArea resaddress;

    @FXML
    private JFXTextArea nextofkinaddress;

    @FXML
    private JFXTextField umescore;

    @FXML
    private JFXTextField prevschool;

    @FXML
    private JFXTextField currenttotal;

    @FXML
    private JFXTextField currentunits;

    @FXML
    private JFXTextField currentWGP;

    //public-init var value : Integer = 0;
    @FXML
    private JFXComboBox<String> modeofentry;

    @FXML
    private JFXComboBox<String> yearofentry;

    @FXML
    private JFXComboBox<String> qualobtained;

    @FXML
    private JFXButton newButton;

    @FXML
    private JFXButton modifyButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXButton firstButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton lastButton;
    @FXML
    private Label datalabel;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXSnackbar snackbar;
    boolean go = true;
    DBTransactions dataTransactions;

    @FXML
    private ImageView fingerprint;

    @FXML
    private JFXButton enrollButton;

    @FXML
    private JFXButton verifyButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private Label flabel;
    boolean imageThere = true;
    boolean enableEnroll = false;
    File currentImageFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            dataTransactions = new DBTransactions();
            //        TextFormatter<Integer> format = new TextFormatter<>(new Integer);
//        matric.setTextFormatter(format);
//   isNumberField(matric);
//validate(matric);
            checkForData();
        } catch (SQLException ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        snackbar.registerSnackbarContainer(root);
        addListeners();
        validate(firstname);
        validate(currentWGP);
        validate(middlename);
        validate(nextofkin);
        validate(surname);
        //validate(phone);
        validate(prevschool);
        validate(currenttotal);
        validate(currentunits);
        validate(nextofkinaddress);
        validate(resaddress);
        numberValidation(matric);
        numberValidation(phone);
        numberValidation(currentWGP);
        numberValidation(currenttotal);
        numberValidation(currentunits);
        numberValidation(umescore);

        sex.getItems().addAll("Male", "Female");
        levelofstudy.getItems().addAll("100", "200", "300", "400");
        modeofentry.getItems().addAll("Direct Entry", "JAMB UME", "Transfer");
        Date t = new Date();
        ObservableList<String> years = FXCollections.observableArrayList();
        for (int y = 0; y < 10; y++) {
            years.add((t.getYear() + 1900 - y) + "/" + (t.getYear() + 1901 - y));
        }
        yearofentry.setItems(years);
        qualobtained.getItems().addAll("WASSCE", "OND", "HND", "NCE", "Bsc");

        String grFingerNativeDirectory = new File(".").getAbsolutePath();

//        Util.setFingerprintSDKNativeDirectory(grFingerNativeDirectory);
//        init();
    }

    public void isNumberField(JFXTextField field) {
        field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

            if (newValue.matches("\\d+")) {
                int value = Integer.parseInt(newValue);

            } else {
                field.setText(oldValue);
            }

        });

    }

    public void validate(JFXTextField validationField) {

        RequiredFieldValidator validator = new RequiredFieldValidator();

        validator.setMessage("Input Required");
        validator.setIcon(new Icon(AwesomeIcon.WARNING, "1em", "-fx-text-fill:red;", "error"));
        validationField.getValidators().add(validator);
        validationField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                validationField.validate();
            }
        });

    }

    public void validate(JFXTextArea validationField) {

        RequiredFieldValidator validator = new RequiredFieldValidator();

        validator.setMessage("Input Required");
        validator.setIcon(new Icon(AwesomeIcon.WARNING, "1em", "-fx-text-fill:red;", "error"));
        validationField.getValidators().add(validator);
        validationField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                validationField.validate();
            }
        });

    }

    public void numberValidation(JFXTextField field) {

        field.textProperty().addListener((ov, old, nw) -> {
            if (nw.matches("^[0-9]*\\.?[0-9]*$")) {
                field.setText(nw);

            } else {
                field.setText(old);
                System.out.println("a");
            }

        });
        NumberValidator validator = new NumberValidator();

        validator.setMessage("6 digit number expected");
        validator.setIcon(new Icon(AwesomeIcon.WARNING, "1em", "-fx-text-fill:red;", "error"));
        field.getValidators().add(validator);
        field.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                field.validate();
            }
        });
        field.onKeyPressedProperty().addListener(e -> {
            field.validate();

        });

    }

    @FXML
    void donew(ActionEvent event) throws SQLException {
        if (newButton.getText().equals("New")) {
            doNewAction();
        } else {
            doSaveAction();

        }

    }
    boolean newAction = false;

    private void doNewAction() {
        newAction = true;
        clearData();
        newButton.setText("Save");
        datalabel.setText("Creating record " + (mydata.size() + 1));
        enableAll(false);
    }

    private void doSaveAction() throws SQLException {
        boolean exist = modify;
        if (exist == true) {
            doEdit();
        } else {
            saveData();
            try {
                checkForData();
            } catch (Exception ex) {
                Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        newButton.setText("New");
        enableAll(true);
        modify = false;

    }

    private void enableAll(boolean enable) {
        matric.setDisable(enable);
        surname.setDisable(enable);
        firstname.setDisable(enable);
        middlename.setDisable(enable);
        phone.setDisable(enable);
        nextofkin.setDisable(enable);
        dob.setDisable(enable);
        sex.setDisable(enable);
        levelofstudy.setDisable(enable);
        resaddress.setDisable(enable);
        nextofkinaddress.setDisable(enable);
        umescore.setDisable(enable);
        prevschool.setDisable(enable);
        currenttotal.setDisable(enable);
        currentunits.setDisable(enable);
        currentWGP.setDisable(enable);
        modeofentry.setDisable(enable);
        yearofentry.setDisable(enable);
        qualobtained.setDisable(enable);
        firstButton.setDisable(!enable);
        nextButton.setDisable(!enable);
        backButton.setDisable(!enable);
        lastButton.setDisable(!enable);
        cancelButton.setDisable(enable);
        modifyButton.setDisable(!enable);

    }

    @FXML
    void doCancel(ActionEvent e) throws SQLException {
        //displayData(new DBTransactions().getAllData());
        if (newAction == false) {
            datalabel.setText("Record " + (currentDisplay + 1) + " of " + mydata.size());
        } else {
            try {
                checkForData();
            } catch (Exception ex) {
                Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        newButton.setText("New");
        enableAll(true);
    }

    private void saveData() throws SQLException {
        // System.out.println("dfgadfggsfg");

        //snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Snackbar Message ","OK",3000,(b)->{snackbar.}));
        StudentData student = new StudentData();
//        boolean inputInDatabase = new DBTransactions().inputInDatabase(student);
        student.setMatricNumber(Integer.parseInt(matric.getText()));
        student.setSurname(surname.getText());
        student.setFirstname(firstname.getText());
        student.setMiddlename(middlename.getText());
        student.setSex(sex.getValue());
        student.setDob(dob.getValue().toString());
        student.setResAddress(resaddress.getText());
        student.setPhone(phone.getText());
        student.setLos(levelofstudy.getValue());
        student.setNok(nextofkin.getText());
        student.setNoka(nextofkinaddress.getText());
        student.setYoe(yearofentry.getValue());
        student.setMoe(modeofentry.getValue());
        student.setUme(Integer.parseInt(umescore.getText()));
        student.setPsa(prevschool.getText());
        student.setQo(qualobtained.getValue());
        student.setTolalUnits(Integer.parseInt(currenttotal.getText()));
        student.setUnitPassed(Integer.parseInt(currentunits.getText()));
        student.setaWGP(Integer.parseInt(currentWGP.getText()));
        boolean inputInDatabase = new DBTransactions().inputInDatabase(student);
        if (inputInDatabase == true) {
            snackbar.show("DATA SAVED SUCCESSFULLY", 1500);

        } else {
            snackbar.show("AN ERROR OCCURED", 1500);
        }
    }

    boolean modify = false;

    @FXML
    void modify(ActionEvent event) {
        newAction = false;
        boolean enable = false;
        modify = true;
        datalabel.setText("Editing record " + (currentDisplay + 1));
        //matric.setDisable(enable);
        surname.setDisable(enable);
        firstname.setDisable(enable);
        middlename.setDisable(enable);
        phone.setDisable(enable);
        nextofkin.setDisable(enable);
        dob.setDisable(enable);
        sex.setDisable(enable);
        levelofstudy.setDisable(enable);
        resaddress.setDisable(enable);
        nextofkinaddress.setDisable(enable);
        umescore.setDisable(enable);
        prevschool.setDisable(enable);
        currenttotal.setDisable(enable);
        currentunits.setDisable(enable);
        currentWGP.setDisable(enable);
        modeofentry.setDisable(enable);
        yearofentry.setDisable(enable);
        qualobtained.setDisable(enable);
        firstButton.setDisable(!enable);
        nextButton.setDisable(!enable);
        backButton.setDisable(!enable);
        lastButton.setDisable(!enable);
        cancelButton.setDisable(enable);
        modifyButton.setDisable(!enable);
        newButton.setText("Save");
    }

    ObservableList<StudentData> mydata;

    private void checkForData() throws SQLException, Exception {
        mydata = dataTransactions.getAllData();

        if (mydata.isEmpty()) {
            datalabel.setText("Record 0 of 0");
            snackbar.show("No records available", 1000);

        } else {

            displayData(mydata);
            FilteredList<StudentData> filt = new FilteredList<>(mydata, p -> true);
            searchfield.textProperty().addListener((observable, oldValue, newValue) -> {

                filt.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (person.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;

                    } else if (person.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(person.getMatricNumber()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
                SortedList<StudentData> sortedData = new SortedList<>(filt);
//             mydata.setAll(sortedData);
                try {
                    displayData(sortedData);
                } catch (IndexOutOfBoundsException e) {
                    datalabel.setText("no record found for " + newValue);
                    // datalabel.setStyle("-fx-text-fill:red");

                } catch (Exception ex) {
                    Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

        }

    }
    int totalRecords = 0;
    int currentDisplay = 0;

    @FXML
    void startEnroll(ActionEvent event) {
        Button theButton = (Button) event.getSource();
        if (theButton.getText().equals("Save")) {
            String msg = "Template extracted successfully. ";
            //write template quality to log
            switch (template.getQuality()) {
                case Template.HIGH_QUALITY:
                    msg += "High quality. ";
                    saveImage();
                    TrayIconClass.getMainTrayIcon().displayMessage("NTG", msg + new Date(), TrayIcon.MessageType.NONE);

                    break;

                case Template.MEDIUM_QUALITY:
                    msg += "Medium quality.";
                    snackbar.show("Finger print quality not sufficient", 2000);

                    break;

                case Template.LOW_QUALITY:
                    msg += "Low quality.";
                    snackbar.show("Finger print quality too low try again", 2000);
                    break;
            }

        } else {
            enableEnroll = true;
            enrollButton.setText("Save");
            TrayIconClass.getMainTrayIcon().displayMessage("NTG", "Enrollment Started\nClick save when done\n" + new Date(), TrayIcon.MessageType.NONE);
        }
    }

    private void displayData(ObservableList<StudentData> data) throws Exception {
        currentDisplay = 0;
        StudentData datum = data.get(currentDisplay);
        datalabel.setText("Record " + (currentDisplay + 1) + " of " + data.size());
        totalRecords = data.size();

        matric.setText(String.valueOf(datum.getMatricNumber()));
        surname.setText(datum.getSurname());
        firstname.setText(datum.getFirstname());
        middlename.setText(datum.getMiddlename());
        sex.setValue(datum.getSex());
        dob.setValue(LocalDate.parse(datum.getDob()));
        resaddress.setText(datum.getResAddress());
        phone.setText(datum.getPhone());
        levelofstudy.setValue(datum.getLos());
        nextofkin.setText(datum.getNok());
        nextofkinaddress.setText(datum.getNoka());
        yearofentry.setValue(datum.getYoe());
        modeofentry.setValue(datum.getMoe());
        umescore.setText(String.valueOf(datum.getUme()));
        prevschool.setText(datum.getPsa());
        qualobtained.setValue(datum.getQo());
        currenttotal.setText(String.valueOf(datum.getTolalUnits()));
        currentunits.setText(String.valueOf(datum.getUnitPassed()));
        currentWGP.setText(String.valueOf(datum.getaWGP()));

        File img = dataTransactions.getFingerPrintImage(datum.getMatricNumber());
        if (img == null) {
            imageThere = false;
            System.out.println("undere");
            flabel.setText("no fingerprint enrolled");
            enrollButton.setDisable(false);
            fingerprint.setImage(null);
        } else {
            fingerprint.setImage(new Image(img.toURL().toString()));
            enrollButton.setDisable(true);
            flabel.setText("fingerprint enrolled");
        }

    }

    void clearData() {

        StudentData datum = new StudentData();
        matric.setText(String.valueOf(datum.getMatricNumber()));
        surname.setText(datum.getSurname());
        firstname.setText(datum.getFirstname());
        middlename.setText(datum.getMiddlename());
        sex.setValue(datum.getSex());
        dob.setValue(LocalDate.now());
        resaddress.setText(datum.getResAddress());
        phone.setText(datum.getPhone());
        levelofstudy.setValue(datum.getLos());
        nextofkin.setText(datum.getNok());
        nextofkinaddress.setText(datum.getNoka());
        yearofentry.setValue(datum.getYoe());
        modeofentry.setValue(datum.getMoe());
        umescore.setText(String.valueOf(datum.getUme()));
        prevschool.setText(datum.getPsa());
        qualobtained.setValue(datum.getQo());
        currenttotal.setText(String.valueOf(datum.getTolalUnits()));
        currentunits.setText(String.valueOf(datum.getUnitPassed()));
        currentWGP.setText(String.valueOf(datum.getaWGP()));
    }

    private void addListeners() {
        try {

            firstButton.setOnAction(e -> {
                try {
                    displayData(mydata);
                } catch (Exception ex) {
                    Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            lastButton.setOnAction(e -> {
                try {
                    doLast(mydata);
                } catch (Exception ex) {
                    Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            nextButton.setOnAction(e -> {
                try {
                    change(mydata, "p");
                } catch (Exception ex) {
                    Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            backButton.setOnAction(e -> {
                try {
                    change(mydata, "t");
                } catch (Exception ex) {
                    Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            closeButton.setOnAction(e -> close(e));

        } catch (Exception e) {
        }

    }

    private void doLast(ObservableList<StudentData> data) throws Exception {
        currentDisplay = data.size() - 1;
        StudentData datum = data.get(currentDisplay);
        datalabel.setText("Record " + (currentDisplay + 1) + " of " + data.size());

        matric.setText(String.valueOf(datum.getMatricNumber()));
        surname.setText(datum.getSurname());
        firstname.setText(datum.getFirstname());
        middlename.setText(datum.getMiddlename());
        sex.setValue(datum.getSex());
        dob.setValue(LocalDate.parse(datum.getDob()));
        resaddress.setText(datum.getResAddress());
        phone.setText(datum.getPhone());
        levelofstudy.setValue(datum.getLos());
        nextofkin.setText(datum.getNok());
        nextofkinaddress.setText(datum.getNoka());
        yearofentry.setValue(datum.getYoe());
        modeofentry.setValue(datum.getMoe());
        umescore.setText(String.valueOf(datum.getUme()));
        prevschool.setText(datum.getPsa());
        qualobtained.setValue(datum.getQo());
        currenttotal.setText(String.valueOf(datum.getTolalUnits()));
        currentunits.setText(String.valueOf(datum.getUnitPassed()));
        currentWGP.setText(String.valueOf(datum.getaWGP()));

        File img = dataTransactions.getFingerPrintImage(datum.getMatricNumber());
        if (img == null) {
            imageThere = false;
            System.out.println("undere");
            flabel.setText("no fingerprint enrolled");
            enrollButton.setDisable(false);
            fingerprint.setImage(null);

        } else {
            fingerprint.setImage(new Image(img.toURL().toString()));
            enrollButton.setDisable(true);
            flabel.setText("fingerprint enrolled");
        }
    }

    private void change(ObservableList<StudentData> data, String what) throws Exception {

        if (what.equals("p")) {
            currentDisplay++;
        } else {
            currentDisplay--;

        }

        if (currentDisplay >= data.size()) {
            currentDisplay = 0;
        } else if (currentDisplay < 0) {
            currentDisplay = data.size() - 1;
        }

        StudentData datum = data.get(currentDisplay);
        datalabel.setText("Record " + (currentDisplay + 1) + " of " + data.size());

        matric.setText(String.valueOf(datum.getMatricNumber()));
        surname.setText(datum.getSurname());
        firstname.setText(datum.getFirstname());
        middlename.setText(datum.getMiddlename());
        sex.setValue(datum.getSex());
        dob.setValue(LocalDate.parse(datum.getDob()));
        resaddress.setText(datum.getResAddress());
        phone.setText(datum.getPhone());
        levelofstudy.setValue(datum.getLos());
        nextofkin.setText(datum.getNok());
        nextofkinaddress.setText(datum.getNoka());
        yearofentry.setValue(datum.getYoe());
        modeofentry.setValue(datum.getMoe());
        umescore.setText(String.valueOf(datum.getUme()));
        prevschool.setText(datum.getPsa());
        qualobtained.setValue(datum.getQo());
        currenttotal.setText(String.valueOf(datum.getTolalUnits()));
        currentunits.setText(String.valueOf(datum.getUnitPassed()));
        currentWGP.setText(String.valueOf(datum.getaWGP()));

        File img = dataTransactions.getFingerPrintImage(datum.getMatricNumber());
        if (img == null) {
            imageThere = false;
            flabel.setText("no fingerprint enrolled");
            enrollButton.setDisable(false);
            fingerprint.setImage(null);
        } else {
            fingerprint.setImage(new Image(img.toURL().toString()));
            enrollButton.setDisable(true);
            flabel.setText("fingerprint enrolled");
        }
    }

    private void doEdit() {
        StudentData student = new StudentData();
//        boolean inputInDatabase = new DBTransactions().inputInDatabase(student);
        student.setMatricNumber(Integer.parseInt(matric.getText()));
        student.setSurname(surname.getText());
        student.setFirstname(firstname.getText());
        student.setMiddlename(middlename.getText());
        student.setSex(sex.getValue());
        student.setDob(dob.getValue().toString());
        student.setResAddress(resaddress.getText());
        student.setPhone(phone.getText());
        student.setLos(levelofstudy.getValue());
        student.setNok(nextofkin.getText());
        student.setNoka(nextofkinaddress.getText());
        student.setYoe(yearofentry.getValue());
        student.setMoe(modeofentry.getValue());
        student.setUme(Integer.parseInt(umescore.getText()));
        student.setPsa(prevschool.getText());
        student.setQo(qualobtained.getValue());
        student.setTolalUnits(Integer.parseInt(currenttotal.getText()));
        student.setUnitPassed(Integer.parseInt(currentunits.getText()));
        student.setaWGP(Integer.parseInt(currentWGP.getText()));

        try {
            boolean done = dataTransactions.updateDatabase(student);
        } catch (SQLException ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        snackbar.show("Record for " + student.getMatricNumber() + " sucessfully updated", 2000);
        datalabel.setText("Record " + (currentDisplay + 1) + " of " + totalRecords);
    }

    @FXML
    private JFXTextField searchfield;

    @FXML
    void close(ActionEvent event) {
        System.out.println("closedclo");
        try {

            GrFingerJava.finalizeCapture();
            fingerprintSDK.destroy();
        } catch (GrFingerJavaException ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFXButton closeBtn = (JFXButton) event.getSource();
        closeBtn.getScene().getWindow().hide();

        System.gc();

    }

//    public FingerprintImage fingerprintimage;
//    public MatchingContext fingerprintSDK;
//    /**
//     * The template extracted from the last acquired image.
//     */
//    private Template template;
//   
//
//    
//    
//    
//    
//    @Override
//    public void onSensorPlug(String string) {
//        System.out.println("Plugged");
//         try {
//           //Start capturing from plugged sensor.
//           GrFingerJava.startCapture(string, this, this);
//             System.out.println("Started");
//       } catch (GrFingerJavaException e) {
//           //write error to log
//         e.printStackTrace();
//       }
//    }
//
//    @Override
//    public void onSensorUnplug(String string) {
//        try {
//            GrFingerJava.stopCapture(string);
//        } catch (GrFingerJavaException ex) {
//            
//        }
//   
//        
//    }
//
//    @Override
//    public void onImageAcquired(String string, FingerprintImage fi) {
//        System.out.println(fi.getData());
//        this.fingerprintimage=fi;
//        BufferedImage ij = null;
//        try {
//            template = fingerprintSDK.extract(fingerprintimage);
//            ij = GrFingerJava.getBiometricImage(template,fingerprintimage);
//        } catch (GrFingerJavaException ex) {
//            
//        }
//         
//        System.out.println(template.getData());
//        ImageWriterSpi spi = new PNGImageWriterSpi();
//
//            File f = null;
//        try {
//            f = File.createTempFile("image", null);
//        } catch (IOException ex) {
//            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            saveToFile(f,spi);
//        
//        System.out.println(f.toURI().toString());
//        try {
//            fingerprint.setImage(new Image(f.toURL().toString()));
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
//    
//    public void saveToFile(File file, ImageWriterSpi spi) {
//       try {
//           //Creates a image writer.
//           ImageWriter writer = spi.createWriterInstance();
//           ImageOutputStream output = ImageIO.createImageOutputStream(file);
//           writer.setOutput(output);
//
//           //Writes the image.
//           writer.write(fingerprintimage);
//
//           //Closes the stream.
//           output.close();
//           writer.dispose();
//       } catch (IOException e) {
//           // write error to log
//           
//       }
//
//   }
//
//    @Override
//    public void onFingerDown(String string) {
//        System.out.println("Finget Placed on "+string);
//    }
//
//    @Override
//    public void onFingerUp(String string) {
//        System.out.println("Finger removed from "+string);
//    }
//
//
//
//    private void init() {
//           try {
//           fingerprintSDK = new MatchingContext();
//           //Starts fingerprint capture.
//           GrFingerJava.initializeCapture(this);
//                System.out.println("Started");
//                
//           
//
//       } catch (Exception e) {
//           //If any error ocurred while initializing GrFinger,
//           //writes the error to log'
//           e.printStackTrace();
//          
//       }
//    }
    @Override
    public void onImageAcquired(String string, FingerprintImage fi) {
        fingerprintimage = fi;
        BufferedImage ij = null;
        try {
            template = fingerprintSDK.extract(fingerprintimage);
            ij = GrFingerJava.getBiometricImage(template, fingerprintimage);
        } catch (GrFingerJavaException ex) {

        }
        if (enableEnroll == true) {

            ImageWriterSpi spi = new PNGImageWriterSpi();

            File f = null;
            try {
                f = File.createTempFile("image", null);
            } catch (IOException ex) {
                Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            saveToFile(f, spi);

            try {
                fingerprint.setImage(new Image(f.toURL().toString()));
                currentImageFile = f;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String msg = "";
                        switch (template.getQuality()) {
                            case Template.HIGH_QUALITY:
                                msg += "High quality. ";
                                break;

                            case Template.MEDIUM_QUALITY:
                                msg += "Medium quality.";
                                break;

                            case Template.LOW_QUALITY:
                                msg += "Low quality.";
                                break;
                        }
                        snackbar.show("Finger print quality - " + msg, 2000);
                    }
                });

            } catch (MalformedURLException ex) {
                Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        if(verifying){
        try {
            String msg = dataTransactions.verifyFingerPrint(Integer.parseInt(matric.getText()), template);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TrayIconClass.getMainToolkit().beep();
                    snackbar.show("See result : "+msg, 2000);
                     
                }
            });
          
        } catch (SQLException ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        verifying = false;
        verifyButton.setDisable(false);
        }
    }

    
    boolean verifying = false;
    @FXML
    void verifyFinger(ActionEvent event){
        verifying = true;
        snackbar.show("Place a finger on the reader now", 3000);
        verifyButton.setDisable(true);
    
    }
    private void saveImage() {
        boolean result = false;
        try {
            result = dataTransactions.enrollFingerPrint(Integer.parseInt(matric.getText()), template.getData(), Files.readAllBytes(currentImageFile.toPath()));
            //System.out.println(FingerPrint.template.getData().length);
            // System.out.println(template.getData().length);
        } catch (Exception ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result == true) {
            enableEnroll = false;
            imageThere = true;
            flabel.setText("fingerprint enrolled");
            enrollButton.setText("Enroll");
            enrollButton.setDisable(true);

        }
    }

    @Override
    public void onFingerDown(String string) {
        super.onFingerDown(string); 
        
        
    }
    
    @FXML
    public void deletePrint(ActionEvent event){
        try {
            int matricNumber = Integer.parseInt(matric.getText());
            dataTransactions.deleteFingerPrint(matricNumber);
            displayData(mydata);
        } catch (SQLException ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }

    
}
