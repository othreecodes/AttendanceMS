/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printout;

import database.DBTransactions;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import models.StudentData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 *
 * @author OTHREE
 */
public class StudentList {

    DBTransactions database;
    ObservableList<StudentData> students;

    public StudentList() throws SQLException {

    }

    public StudentList(int level) {
        try {
            database = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(StudentList.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            students = database.getStudentDataForLevel(level);
            startCreation();
        } catch (SQLException ex) {
            Logger.getLogger(StudentList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void startCreation() throws SQLException {

        try {
            FileChooser file = new FileChooser();

            file.setTitle("Save File as...");
            file.setInitialFileName(students.get(0).getLos() + " Level Students - " + LocalDate.now());
            file.getExtensionFilters().add(new FileChooser.ExtensionFilter("MS-EXCEL (.xls)", "*.xls"));
            Workbook workbook = new HSSFWorkbook();
            FileOutputStream output = new FileOutputStream(file.showSaveDialog(null));

            Sheet sheet = workbook.createSheet("newsheet");
            sheet.setPrintGridlines(true);

            sheet.getHeader().setCenter("UNIVERSITY OF IBADAN\n"
                    + "COMPUTER SCIENCE\n"
                    + students.get(0).getLos() + " LEVEL STUDENT LIST FOR " + (2000 + Integer.parseInt(database.getCurrent())) + "/" + (2001 + Integer.parseInt(database.getCurrent())) + " SESSION");

            int iter = 1;
            Row row1 = sheet.createRow(0);
            row1.createCell(0)
                    .setCellValue("S/N");
            row1.createCell(1).setCellValue("Matric No");

            row1.createCell(2).setCellValue("Name");

            row1.createCell(3).setCellValue("Sex");
            row1.createCell(4).setCellValue("Date of Birth");
            row1.createCell(5).setCellValue("Year of Entry");
            row1.createCell(6).setCellValue("Mode of Entry");
            sheet.setColumnWidth(0, 1000);
            sheet.setColumnWidth(1, 2500);
            sheet.setColumnWidth(2, 7000);
            sheet.setColumnWidth(3, 2000);
            sheet.setColumnWidth(4, 3000);
            sheet.setColumnWidth(5, 3000);
            sheet.setColumnWidth(6, 3500);

            for (StudentData stu : students) {

                Row row = sheet.createRow(iter);
                row.createCell(0)
                        .setCellValue(iter);
                row.createCell(1).setCellValue(stu.getMatricNumber());

                row.createCell(2).setCellValue(stu.getSurname() + " " + stu.getMiddlename() + " " + stu.getFirstname());
                row.createCell(3).setCellValue(stu.getSex());
                row.createCell(4).setCellValue(stu.getDob());
                row.createCell(5).setCellValue(stu.getYoe());
                row.createCell(6).setCellValue(stu.getMoe());
                System.out.println(iter);
                iter++;

            }
            workbook.write(output);
        } catch (IOException ex) {

        }

    }

}
