/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printout;

import database.DBTransactions;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import models.StudentData;
import models.courses;
import models.result;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 *
 * @author OTHREE
 */
public class SenateList {

    ObservableList<StudentData> students;
    ObservableList<courses> studentcourses;
    DBTransactions database;
    int totalUnit = 0;
    int totalUnitPassed = 0;
    double totalWGP = 0;
    int studPassed;
    int studFailed;
    int studWarning;

    public SenateList() {
        try {
            database = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(SenateList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SenateList(ObservableList<StudentData> students, ObservableList<courses> studentcourses) {
        this.students = students;
        this.studentcourses = studentcourses;

    }

    public SenateList(ObservableList<StudentData> students) {
        try {
            database = new DBTransactions();
        } catch (SQLException ex) {
            Logger.getLogger(SenateList.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.students = students;
        try {
            startCreation();
        } catch (SQLException ex) {
            Logger.getLogger(SenateList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void startCreation() throws SQLException {

        try {
            FileChooser file = new FileChooser();

            file.setTitle("Save File as...");
            // file.setInitialFileName(students.get(0).getLos() + " Level Students - "+LocalDate.now());
            file.getExtensionFilters().add(new FileChooser.ExtensionFilter("MS-Document (.docx)", "*.docx"));
            XWPFDocument document = new XWPFDocument();
            file.setInitialFileName("SUMMARY OF RESULTS for " + students.get(0).getLos());
            FileOutputStream output = new FileOutputStream(file.showSaveDialog(null));
            XWPFParagraph paragraph1 = document.createParagraph();
            XWPFRun run = paragraph1.createRun();
            run.setText("UNIVERSITY OF IBADAN");
            run.setBold(true);
            run.addBreak();
            run.setText("PRESENTATION OF NON-FINAL YEAR STUDENTS' RESULTS");

            run.addBreak();
            run.setText("SUMMARY OF RESULTS TABLE " + (2000 + Integer.parseInt(database.getCurrent())) + "/" + (2001 + Integer.parseInt(database.getCurrent())) + " SESSION");
            run.addBreak();
            run.setText("FACULTY OF SCIENCE");
            run.addBreak();
            run.setText("DEPARTMENT OF COMPUTER SCIENCE LEVEL: " + students.get(0).getLos());
            paragraph1.setAlignment(ParagraphAlignment.CENTER);

            XWPFTable table = document.createTable(students.size() + 1, 9);

            table.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
            table.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
            table.getBody().getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            table.setCellMargins(0, 30, 0, 30);

            XWPFTableRow row0 = table.getRow(0);

            row0.getCell(0).setText("S/N");
            row0.getCell(1).setText("MATRIC NO");
            row0.getCell(2).setText("YEAR OF ENTRY");
            row0.getCell(3).setText("MODE OF ENTRY");
            row0.getCell(4).setText("UNITS REGISTERED");
            row0.getCell(5).setText("UNITS PASSED");
            row0.getCell(6).setText("WGP");
            row0.getCell(7).setText("CGPA");
            row0.getCell(8).setText("REMARK");

            int row = 1;
            for (StudentData stu : students) {
                totalUnit = 0;
                totalWGP = 0;
                totalUnitPassed = 0;
                row0 = table.getRow(row);
                row0.getCell(0).setText(row + ".");
                row0.getCell(1).setText(String.valueOf(stu.getMatricNumber()));
                row0.getCell(2).setText(stu.getYoe());
                row0.getCell(3).setText(stu.getMoe());

                studentcourses = new DBTransactions().getStudentCourses(stu.getMatricNumber());

                for (courses cou : studentcourses) {

                }
                studentcourses.stream().forEach((courses cou) -> {
                    totalUnit += cou.getUnit();

                });

                studentcourses.stream().forEach((courses cou) -> {
                    result re = new result();
                    re = re.result(cou.getUnit(), cou.getScore());
                    if (re.getRemark().equals("PASSED")) {
                        totalUnitPassed += cou.getUnit();
                    }

                });

                studentcourses.stream().forEach((courses cou) -> {

                    result re = new result();
                    re = re.result(cou.getUnit(), cou.getScore());
                    totalWGP += re.getWgp();
                });
                row0.getCell(4).setText(String.valueOf(totalUnit));
                row0.getCell(5).setText(String.valueOf(totalUnitPassed));
                row0.getCell(6).setText(String.valueOf(totalWGP));
                double CGPA = totalWGP / totalUnit;
                row0.getCell(7).setText(String.format("%.1f", CGPA));
                String remark = "PASSED";
                if (CGPA < 1.0) {
                    remark = "FAILED";
                    studFailed++;
                } else if (CGPA < 2.0) {
                    remark = "WARNING";
                    studWarning++;
                } else {
                    remark = "PASSED";
                    studPassed++;
                }
                row0.getCell(8).setText(remark);
                row++;

            }

            table.getRows().stream().forEach((XWPFTableRow rowe) -> {
                rowe.getTableCells().stream().forEach((cell) -> {
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

                });
            });

            table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(10000));

            XWPFParagraph statPara = document.createParagraph();
            statPara.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runstat = statPara.createRun();
            runstat.addBreak();
            runstat.addBreak();
            runstat.addBreak();
            runstat.setText("SUMMARY: ");
            runstat.setText("PASSED :"+studPassed);
            runstat.addTab();
            runstat.setText("WARNING :"+studWarning);
            runstat.addTab();
            runstat.setText("WITHDRAWAL :"+studFailed);
            runstat.addTab();
            runstat.setText("TOTAL :" + students.size());
            runstat.addTab();
            runstat.addBreak();
            runstat.addBreak();

            
            
            runstat.setText("_________________________");
            runstat.addTab();
            runstat.addTab();
            runstat.addTab();
            runstat.setText("_________________________");
            runstat.addTab();
            runstat.addBreak();
            runstat.addBreak();
            runstat.setText("----Head of Department---");
            runstat.addTab();
            runstat.addTab();
            runstat.addTab();
            runstat.setText("----Dean of Faculty------");
            
            document.write(output);
            
            
        } catch (IOException ex) {

        }

    }

}
