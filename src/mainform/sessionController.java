/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainform;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import database.DBTransactions;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.session;

/**
 *
 * @author OTHREE
 */
public class sessionController implements Initializable {

    @FXML
    private StackPane root;

    //Session Table Configuration
    @FXML
    private JFXTreeTableView<session> sessionTable;
    @FXML
    private JFXTreeTableColumn<session, Integer> idColumn;
    @FXML
    private JFXTreeTableColumn<session, String> codeColumn;
    @FXML
    private JFXTreeTableColumn<session, String> yearColumn;
    @FXML
    private JFXTreeTableColumn<session, String> statusColumn;
    @FXML
    private JFXTreeTableColumn<session, Boolean> currentColumn;
    @FXML
    private JFXTreeTableColumn<session, Boolean> closedColumn;

    ObservableList<session> theSessions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactoryValues();
        try {
            theSessions = new DBTransactions().getAllSessions();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionTable.setRoot(new RecursiveTreeItem<>(theSessions, RecursiveTreeObject::getChildren));
        sessionTable.setShowRoot(false);

    }

    private void setCellFactoryValues() {
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));

        codeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        yearColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("year"));
        statusColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));
        currentColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("current"));

        currentColumn.setCellFactory((TreeTableColumn<session, Boolean> param) -> {
            TreeTableCell cell = new TreeTableCell<session, Boolean>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<session> ttr = getTreeTableRow();
                    if (item == null || empty) {
                        setText(null);
                        ttr.setStyle("");
                        setStyle("");
                    } else {
                        System.out.println(item);
                        if (item == true) {

                            JFXCheckBox check = new JFXCheckBox();
                            check.setSelected(true);
                            check.setDisable(true);
                            setGraphic(check);

                            // setText("knhjhj");
                        } else if (item == false) {
                            JFXCheckBox check = new JFXCheckBox();
                            check.setSelected(false);
                            check.setDisable(true);
                            setGraphic(check);
                        }

//                        setStyle(item.doubleValue() > 0 
//                                ? "-fx-background-color:green"
//                                : "-fx-background-color:red");
                    }
                }
            };
            return cell;
        });

        closedColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("closed"));
        closedColumn.setCellFactory((TreeTableColumn<session, Boolean> param) -> {
            TreeTableCell cell = new TreeTableCell<session, Boolean>() {
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<session> ttr = getTreeTableRow();
                    if (item == null || empty) {
                        setText(null);
                        ttr.setStyle("");
                        setStyle("");
                    } else {
                        System.out.println(item);
                        if (item == true) {

                            JFXCheckBox check = new JFXCheckBox();
                            check.setSelected(true);
                            check.setDisable(true);
                            setGraphic(check);

                            // setText("knhjhj");
                        } else if (item == false) {
                            JFXCheckBox check = new JFXCheckBox();
                            check.setSelected(false);
                            check.setDisable(true);
                            setGraphic(check);
                        }

//                        setStyle(item.doubleValue() > 0 
//                                ? "-fx-background-color:green"
//                                : "-fx-background-color:red");
                    }
                }
            };
            return cell;
        });
    }

    @FXML
    void sessionCreate(ActionEvent event) throws SQLException {
        boolean exist = new DBTransactions().checkopen();
        JFXSnackbar snack = new JFXSnackbar((Pane) root.getParent().getParent());

        if (exist == true) {
            snack.show("A session is currently open.\nYou need to close a session before you create a new one".toUpperCase(), 3000);

        } else {
            createSesion();
           
        }

    }

    @FXML
    void setSess(ActionEvent event) {

    }

    private void createSesion() {
        int prev = new Date().getYear() + 1900;
        int next = prev + 1;

        ObservableList<String> years = FXCollections.observableArrayList();
        for (int i = 0; i < 20; i++) {
            String year = (prev + i) + "/" + (next + i);
            years.add(year);
        }

        Dialog sessionDialog = new ChoiceDialog(years.get(0), years);
        sessionDialog.setHeaderText("Create Session");
        sessionDialog.setContentText("Select Session year");
        sessionDialog.setTitle("Create Session");
        sessionDialog.show();
        sessionDialog.resultProperty().addListener(e -> {
            JFXSnackbar snack = new JFXSnackbar((Pane) root.getParent().getParent());
            try {
                System.out.println(sessionDialog.getResult());
                boolean exists = checkIfSessionHasBeenCreated(sessionDialog.getResult().toString());
                if (exists == true) {

                    snack.show(sessionDialog.getResult() + " Has been created before\nChoose another".toUpperCase(), 3000);
                    createSesion();
                } else {
                    session sess = new session();
                    sess.setYear(sessionDialog.getResult().toString());
                    sess.setCode(sess.getYear().substring(2, 4));
                    sess.setCurrent(true);
                    sess.setStatus("set");
                    sess.setClosed(false);
                    boolean done = new DBTransactions().createSession(sess);
                    if (done == true) {
                        refreshTable();
                        snack.show(sessionDialog.getResult() + " Session Created Successfully".toUpperCase(), 3000);
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(sessionController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    boolean checkIfSessionHasBeenCreated(String session) throws SQLException {

        return new DBTransactions().checksesion(session);
    }

    private void refreshTable() {
        initialize(null, null);
    }

}
