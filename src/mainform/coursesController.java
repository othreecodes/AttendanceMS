/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import models.courses;

/**
 * FXML Controller class
 *
 * @author OTHREE
 */
public class coursesController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private JFXTreeTableView<courses> table;

    @FXML
    private JFXTreeTableColumn<courses, String> codeColumn;

    @FXML
    private JFXTreeTableColumn<courses, Integer> unitColumn;

    @FXML
    private JFXTreeTableColumn<courses, String> titleColumn;

    @FXML
    private JFXTreeTableColumn<courses, String> modeColumn;

    @FXML
    private JFXTreeTableColumn<courses, String> prereqColumn;

    @FXML
    private JFXTreeTableColumn<courses, Integer> levelColumn;

    @FXML
    private JFXTextField nameText;

    @FXML
    private JFXComboBox<Integer> unitBox;

    @FXML
    private JFXTextField titleText;

    @FXML
    private JFXComboBox<String> modeBox;

    @FXML
    private JFXComboBox<String> prereqBox;

    @FXML
    private JFXComboBox<Integer> levelBox;

    @FXML
    private JFXButton newButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton modifyButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXSnackbar snackbar;
    ObservableList<courses> course = null;

    public coursesController() throws SQLException {
        this.database = new DBTransactions();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // TODO
            setCellValueFactory();
            course = new DBTransactions().getAllCourses();

            table.setRoot(new RecursiveTreeItem<>(course, RecursiveTreeObject::getChildren));
            table.setShowRoot(false);
        } catch (SQLException ex) {
            Logger.getLogger(coursesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        registerSnackbar();
        unitBox.getItems().setAll(1, 2, 3, 4, 5, 6, 7);
        modeBox.getItems().setAll("Compulsory", "Elective", "Required");
        levelBox.getItems().setAll(100, 200, 300, 400, 500, 600);
        ObservableList<String> prereqcourses = FXCollections.observableArrayList();
        prereqcourses.add(0, "None");
        int i = 1;
        for (courses c : course) {

            prereqcourses.add(i, c.getCode());
            i++;
        }
        prereqBox.setItems(prereqcourses);

        nameText.setText(course.get(0).getCode());
        nameText.textProperty().addListener((ov, old, nw) -> {
            if (nw.contains(" ")) {
                nameText.setText(old.toUpperCase());
                snackbar.show("Space not allowed", 800);
            } else {
                nameText.setText(nw.toUpperCase());
            }

        });

        unitBox.setValue(course.get(0).getUnit());
        modeBox.setValue(new con().get(course.get(0).getMode()));
        levelBox.setValue(course.get(0).getLevel());
        prereqBox.setValue(course.get(0).getPrereq());
        titleText.setText(course.get(0).getTitle());

        table.getSelectionModel().selectedItemProperty().addListener((e,r,t) -> {
            setCurrentValue(table.getSelectionModel().getSelectedItem().getValue());
            
            
        });
        setDisabled(true);
        
//       JFXTreeTableRow<courses> row = (JFXTreeTableRow<courses>)table.getColumns().get(0)
           
    }

    void setCurrentValue(courses course) {
        nameText.setText(course.getCode());
        unitBox.setValue(course.getUnit());
        modeBox.setValue(new con().get(course.getMode()));
        levelBox.setValue(course.getLevel());
        prereqBox.setValue(course.getPrereq());
        titleText.setText(course.getTitle());
    }

    private void setCellValueFactory() {
        codeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        
        unitColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("unit"));
        titleColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        modeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("mode"));
        prereqColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("prereq"));
        levelColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("level"));
        modeColumn.setCellFactory((TreeTableColumn<courses, String> param) -> {
            TreeTableCell cell = new TreeTableCell<courses, String>(){
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<courses> ttr = getTreeTableRow();
                    if (item == null || empty){
                        setText(null);
                        ttr.setStyle("");
                        setStyle("");
                    } else {
                        if(item.equals("C")){
                            ttr.setStyle("-fx-background-color:red");
                        
                        }else if(item.equals("R")){
                            ttr.setStyle("-fx-background-color:yellow");
                        
                        }else if(item.equals("E")){
                            ttr.setStyle("-fx-background-color:green");
                        
                        }
                        setText(item.toString());
//                        setStyle(item.doubleValue() > 0 
//                                ? "-fx-background-color:green"
//                                : "-fx-background-color:red");
                    }
                }
            };
            return cell;
        });
    }

    boolean check = false;

    @FXML
    void removeSpace(KeyEvent event) {

        if (event.getCode() == KeyCode.SPACE) {
            //   nameText.deleteText(nameText.getLength(), nameText.getLength()-1);
        }
    }

    private void setDisabled(boolean b) {
        nameText.setEditable(!b);
        unitBox.setDisable(b);
        modeBox.setDisable(b);
        levelBox.setDisable(b);
        prereqBox.setDisable(b);
        titleText.setEditable(!b);
        newButton.setDisable(!b);
        modifyButton.setDisable(!b);
        cancelButton.setDisable(b);
//        closeButton.setDisable(!b);
        saveButton.setDisable(b);
        deleteButton.setDisable(!b);
    }

    @FXML
    void cancel(ActionEvent event) {
        if (wasnew == true) {
            initialize(null, null);
        }
        setDisabled(true);

    }

    @FXML
    void close(ActionEvent event) {
        JFXButton n = (JFXButton) event.getSource();
        n.getScene().getWindow().hide();
    }
    boolean wasnew;

    @FXML
    void createNew(ActionEvent event) {
        wasnew = true;
        setDisabled(false);
        nameText.clear();
        titleText.clear();
        unitBox.setValue(null);
        modeBox.setValue(null);
        prereqBox.setValue(null);
        levelBox.setValue(null);

    }

    @FXML
    void delete(ActionEvent event) {
        courses cou = new courses();
        cou.setCode(nameText.getText());
        cou.setLevel(levelBox.getValue());
        cou.setMode(new con().get(modeBox.getValue()));
        cou.setPrereq(prereqBox.getValue());
        cou.setTitle(titleText.getText());
        cou.setUnit(unitBox.getValue());

        try {
            boolean done = database.delete(cou);
            if (done == true) {
                snackbar.show(cou.getCode() + "sucessfully deleted", 1200);
            }
        } catch (SQLException ex) {
            Logger.getLogger(coursesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshTable();
        nameText.setText(course.get(0).getCode());
        unitBox.setValue(course.get(0).getUnit());
        modeBox.setValue(new con().get(course.get(0).getMode()));
        levelBox.setValue(course.get(0).getLevel());
        prereqBox.setValue(course.get(0).getPrereq());
        titleText.setText(course.get(0).getTitle());
    }

    @FXML
    void modify(ActionEvent event) {
        wasnew = false;
        setDisabled(false);
        nameText.setEditable(false);
    }

    DBTransactions database;

    @FXML
    void save(ActionEvent event) throws SQLException {
        courses cou = new courses();
        cou.setCode(nameText.getText());
        cou.setLevel(levelBox.getValue());
        cou.setMode(new con().get(modeBox.getValue()));
        cou.setPrereq(prereqBox.getValue());
        cou.setTitle(titleText.getText());
        cou.setUnit(unitBox.getValue());

        boolean done = false;
        if (wasnew == true) {

            if (database.checkIfExist(cou) == true) {
                snackbar.show(cou.getCode().toUpperCase() + " already Exist", 1500);

            } else {

                try {
                    done = database.insertCourse(cou);

                } catch (SQLException ex) {
                    Logger.getLogger(coursesController.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (done == true) {

                    snackbar.show(cou.getCode() + " Added Sucessfully", 1500);
                    setDisabled(true);
                    refreshTable();

                } else {
                    snackbar.show(cou.getCode() + " An Error Occured", 1500);
                }
            }
        } else {
            try {
                done = database.updateCourse(cou);
            } catch (SQLException ex) {
                Logger.getLogger(coursesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (done == true) {
                snackbar.show(cou.getCode() + " Edited Sucessfully", 1500);
                setDisabled(true);
                refreshTable();
            }

        }

    }
    boolean registered = false;

    private void registerSnackbar() {
        if (registered == false) {
            snackbar.registerSnackbarContainer((AnchorPane) table.getParent().getParent());
            registered = true;
        }
    }

    void refreshTable() {
        try {
            // TODO
            setCellValueFactory();
            course = database.getAllCourses();

            table.setRoot(new RecursiveTreeItem<>(course, RecursiveTreeObject::getChildren));
            table.setShowRoot(false);
        } catch (SQLException ex) {
            Logger.getLogger(coursesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    
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
