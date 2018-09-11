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

public class Transcript {

    DBTransactions database;
    StudentData student;
    ObservableList<courses> studcourses;
    int totalUnit = 0;
    int totalUnitPassed = 0;
    double totalWGP = 0;

    public Transcript(StudentData st, ObservableList<courses> cou) {
        student = st;
        studcourses = cou;
        try {
            database = new DBTransactions();
            startCreation();
        } catch (SQLException ex) {
            Logger.getLogger(Transcript.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startCreation() throws SQLException {

        try {
            FileChooser file = new FileChooser();

            file.setTitle("Save File as...");
            // file.setInitialFileName(students.get(0).getLos() + " Level Students - "+LocalDate.now());
            file.getExtensionFilters().add(new FileChooser.ExtensionFilter("MS-Document (.docx)", "*.docx"));
            XWPFDocument document = new XWPFDocument();
            file.setInitialFileName("Transcript for " + student.getMatricNumber());
            FileOutputStream output = new FileOutputStream(file.showSaveDialog(null));
            XWPFParagraph paragraph1 = document.createParagraph();
            XWPFRun run = paragraph1.createRun();
            run.setText((2000 + Integer.parseInt(database.getCurrent())) + "/" + (2001 + Integer.parseInt(database.getCurrent())) + " SESSION");
            run.setBold(true);
            run.addBreak();
            run.setText("PROVISIONAL DEPARTMENTAL TRANSCRIPT");

            run.addBreak();
            run.setText("COMPUTER SCIENCE DEPARTMENT");
            run.addBreak();
            run.setText("FACULTY OF SCIENCE");
            paragraph1.setAlignment(ParagraphAlignment.CENTER);
            XWPFParagraph para2 = document.createParagraph();
            run = para2.createRun();
            run.setText("NAME: " + student.getSurname() + " " + student.getFirstname());
            run.addTab();
            run.addTab();
            run.addTab();
            run.addTab();
            run.addTab();
            run.addTab();
           
            
            run.setText("MATRIC NO: " + student.getMatricNumber());
            para2.setSpacingAfter(10);
            XWPFParagraph par3 = document.createParagraph();
            run = par3.createRun();

            run.setText("YEAR OF ENTRY: " + student.getYoe());
            run.addTab();
            run.addTab();
            run.addTab();
            run.addTab();
            run.addTab();
            run.addTab();
            run.addTab();
            run.setText("SEX: " + student.getSex());

            XWPFTable table = document.createTable(studcourses.size() + 1, 8);

            table.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
            table.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "000000");
            table.getBody().getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
            table.setCellMargins(0, 30, 0, 30);

            XWPFTableRow row0 = table.getRow(0);

            row0.getCell(0).setText("CODE");
            row0.getCell(1).setText("TITLE");
            row0.getCell(2).setText("MARK");
            row0.getCell(3).setText("UNIT");
            row0.getCell(4).setText("GP");
            row0.getCell(5).setText("WGP");
            row0.getCell(6).setText("REMARK");
            row0.getCell(7).setText("SESSION");

            int row = 1;
            for (courses cou : studcourses) {
                row0 = table.getRow(row);
                row0.getCell(0).setText(cou.getCode().trim());
                String title = database.getSingleCourse(cou.getCode()).getTitle();

                row0.getCell(1).setText(title);

                result re = new result();
                re = re.result(cou.getUnit(), cou.getScore());

                row0.getCell(2).setText(String.valueOf(cou.getScore()));
                row0.getCell(3).setText(String.valueOf(cou.getUnit()));
                row0.getCell(4).setText(String.valueOf(re.getGradepoint()));
                row0.getCell(5).setText(String.valueOf(re.getWgp()));
                row0.getCell(6).setText(re.getRemark());
                row0.getCell(7).setText((2000 + Integer.parseInt(database.getCurrent())) + "/" + (2001 + Integer.parseInt(database.getCurrent())));

                row++;
            }
            table.getRows().stream().forEach((XWPFTableRow rowe) -> {
                rowe.getTableCells().stream().forEach((cell) -> {
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                });
            });

            table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(10000));
            XWPFParagraph paragraph4 = document.createParagraph();
            XWPFRun Prun = paragraph4.createRun();
            Prun.addBreak();
            Prun.addTab();

            studcourses.stream().forEach((courses cou) -> {
                totalUnit += cou.getUnit();
            });

            studcourses.stream().forEach((courses cou) -> {
                result re = new result();
                re = re.result(cou.getUnit(), cou.getScore());
                if(re.getRemark().equals("PASSED")){
                totalUnitPassed += cou.getUnit();
                }
            });
            studcourses.stream().forEach((courses cou) -> {
                result re = new result();
                re = re.result(cou.getUnit(), cou.getScore());
                totalWGP+=re.getWgp();
            });
            
            Prun.setText("Total Units Taken = " + totalUnit);
            Prun.addTab();
            Prun.addTab();
            Prun.setText("Total Units Passed = "+totalUnitPassed);
            Prun.addBreak();
            Prun.addTab();
            Prun.setText("Total WGP            = "+totalWGP);
            Prun.addTab();
           
            double CGPA = totalWGP/totalUnit;
            Prun.setText("Cumulative Grade Point Average = "+String.format("%.1f",CGPA));
            Prun.addBreak();
            Prun.addTab();

            document.write(output);
        } catch (IOException ex) {

        }

    }

}
