/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.Template;
import fingerprint.FingerPrint;
import static fingerprint.FingerPrint.fingerprintSDK;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import models.Attendance;
import models.Register;
import models.Role;
import models.StudentData;
import models.courses;
import models.session;
import models.User;

/**
 *
 * @author OTHREE
 */
public class DBTransactions {

    Connection con;
    PreparedStatement pt;
    ResultSet rs;

    /**
     This is the default constructor
     **/
    public DBTransactions() throws SQLException {

    }

    /**
     * @param student the student model to insert into the database 
     * @throws SQLException Thrown if the database connection is not set
     * @return returns true if the data has been inputed and false if not
     * This inputs a new Student record in the student record database
     * 
     */
    public boolean inputInDatabase(StudentData student) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("INSERT INTO studentdata VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pt.setInt(2, student.getMatricNumber());
        pt.setString(3, student.getSurname());
        pt.setString(4, student.getFirstname());
        pt.setString(5, student.getMiddlename());
        pt.setString(6, student.getSex());
        pt.setString(7, student.getDob());
        pt.setString(8, student.getResAddress());
        pt.setString(9, student.getPhone());
        pt.setString(10, student.getLos());
        pt.setString(11, student.getNok());
        pt.setString(12, student.getNoka());
        pt.setString(13, student.getYoe());
        pt.setString(14, student.getMoe());
        pt.setInt(15, student.getUme());
        pt.setString(16, student.getPsa());
        pt.setString(17, student.getQo());
        pt.setInt(18, student.getTolalUnits());
        pt.setInt(19, student.getUnitPassed());
        pt.setInt(20, student.getaWGP());
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();
        if (value >= 1) {
            insertUser(student);

        }
        return done;
    }

    /**
     * @return All Students in the database
     * @throws SQLException if the database connection is not set
     * Gets all Student records from database
     * 
    */
    public ObservableList<StudentData> getAllData() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM studentdata");
        rs = pt.executeQuery();
        ObservableList<StudentData> allData = FXCollections.observableArrayList();
        while (rs.next()) {
            StudentData data = new StudentData();
            data.setMatricNumber(rs.getInt("matric_no"));
            data.setSurname(rs.getString("surname"));
            data.setFirstname(rs.getString("firstname"));
            data.setMiddlename(rs.getString("middlename"));
            data.setSex(rs.getString("sex"));
            data.setDob(rs.getString("dob"));
            data.setResAddress(rs.getString("res_address"));
            data.setPhone(rs.getString("phone"));
            data.setLos(rs.getString("level_study"));
            data.setNok(rs.getString("next_kin"));
            data.setNoka(rs.getString("next_kin_address"));
            data.setYoe(rs.getString("year_entry"));
            data.setMoe(rs.getString("mode_entry"));
            data.setUme(rs.getInt("jamb_score"));
            data.setPsa(rs.getString("prev_school"));
            data.setQo(rs.getString("qual_obtained"));
            data.setTolalUnits(rs.getInt("current_total"));
            data.setUme(rs.getInt("current_units"));
            data.setaWGP(rs.getInt("current_wgp"));
            allData.add(data);

        }
        rs.close();
        pt.close();
        con.commit();
        con.close();

        return allData;
    }

    /**
     * @param student the student model to update
     * @throws SQLException Thrown if the database connection is not set
     * @return returns true if the data has been updated and false if not
     * This updates Student record in the student record database
     * 
     */
    public boolean updateDatabase(StudentData student) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("UPDATE studentdata SET matric_no = ?,surname = ?,firstname = ?,middlename = ?,sex = ?,dob = ?,res_address = ?,phone = ?,level_study = ?,next_kin = ?,next_kin_address = ?,year_entry = ?,mode_entry = ?,jamb_score = ?,prev_school = ?,qual_obtained = ?,current_total = ?,current_units = ?,current_wgp = ? where matric_no = " + "'" + student.getMatricNumber() + "'");
        pt.setInt(1, student.getMatricNumber());
        pt.setString(2, student.getSurname());
        pt.setString(3, student.getFirstname());
        pt.setString(4, student.getMiddlename());
        pt.setString(5, student.getSex());
        pt.setString(6, student.getDob());
        pt.setString(7, student.getResAddress());
        pt.setString(8, student.getPhone());
        pt.setString(9, student.getLos());
        pt.setString(10, student.getNok());
        pt.setString(11, student.getNoka());
        pt.setString(12, student.getYoe());
        pt.setString(13, student.getMoe());
        pt.setInt(14, student.getUme());
        pt.setString(15, student.getPsa());
        pt.setString(16, student.getQo());
        pt.setInt(17, student.getTolalUnits());
        pt.setInt(18, student.getUnitPassed());
        pt.setInt(19, student.getaWGP());
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;
    }
    
    /**
     * @return All courses in the database
     * @throws SQLException if the database connection is not set
     * Gets all courses from database
     * 
    */
    public ObservableList<courses> getAllCourses() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM courses ORDER BY `code`");
        rs = pt.executeQuery();
        ObservableList<courses> allCourses = FXCollections.observableArrayList();
        while (rs.next()) {
            courses course = new courses();
            course.setCode(rs.getString("code"));
            course.setUnit(rs.getInt("unit"));
            course.setTitle(rs.getString("title"));
            course.setMode(rs.getString("mode"));
            course.setPrereq(rs.getString("prereq"));
            course.setLevel(rs.getInt("level"));
            allCourses.add(course);

        }
        pt.close();
        con.commit();
        con.close();

        return allCourses;
    }
    
    /**
     * @param course the course to insert into the database
     * @throws SQLException Thrown if the database connection is not set
     * @return returns true if the data has been inserted and false if not
     * This inserts a course record in the courses record database
     * 
     */
    public boolean insertCourse(courses course) throws SQLException {

        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("INSERT INTO `courses`(`code`,`unit`,`title`,`mode`,`prereq`,`level`) VALUES (?,?,?,?,?,?);");
        pt.setString(1, course.getCode().replace(" ", ""));
        pt.setInt(2, course.getUnit());
        pt.setString(3, course.getTitle());
        pt.setString(4, course.getMode());
        pt.setString(5, course.getPrereq());
        pt.setInt(6, course.getLevel());

        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;
    }

    public boolean checkIfExist(courses course) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM courses WHERE code = " + "'" + course.getCode() + "'");
        rs = pt.executeQuery();
        boolean is = false;
        if (rs.next()) {
            is = true;
        }
        pt.close();
        con.commit();
        con.close();

        return is;
    }

    public boolean updateCourse(courses course) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("UPDATE courses SET code = ?,unit = ?,title = ?,mode = ?,prereq = ?,level = ? WHERE code = " + "'" + course.getCode() + "'");
        pt.setString(1, course.getCode().replace(" ", ""));
        pt.setInt(2, course.getUnit());
        pt.setString(3, course.getTitle());
        pt.setString(4, course.getMode());
        pt.setString(5, course.getPrereq());
        pt.setInt(6, course.getLevel());

        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;

    }

    public boolean delete(courses cou) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("DELETE FROM courses WHERE code = " + "'" + cou.getCode() + "'");

        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;

    }

    public String changePassword(String username, String oldP, String newP, String confP) {
        String message = "";
        try {
            con = DriverManager.getConnection("jdbc:sqlite:database");
            con.setAutoCommit(false);
            pt = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            pt.setString(1, username);
            pt.setString(2, oldP);
            System.out.println(username + " " + oldP);
            rs = pt.executeQuery();
            if (rs.next()) {
                System.out.println("Exists");
                if (!newP.equals(confP)) {
                    message = "Confirmation password doesn't match";
                } else {
                    pt = con.prepareStatement("UPDATE users SET password = ? WHERE username = '" + username + "'");
                    pt.setString(1, newP);

                    int value = pt.executeUpdate();
                    if (value >= 1) {
                        message = "done";
                    } else {
                        message = "An error occured";
                    }
                }

            } else {
                message = "Wrong current password";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pt.close();
            con.commit();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }

        return message;
    }

    public ObservableList<session> getAllSessions() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM session");
        rs = pt.executeQuery();
        ObservableList<session> allSessions = FXCollections.observableArrayList();
        while (rs.next()) {
            session sess = new session();
            sess.setId(rs.getInt("id"));
            sess.setCode(rs.getString("code"));
            sess.setCurrent(Boolean.parseBoolean(rs.getString("current")));
            sess.setClosed(Boolean.parseBoolean(rs.getString("closed")));
            sess.setYear(rs.getString("session"));
            sess.setStatus(rs.getString("status"));
            allSessions.add(sess);

        }
        rs.close();
        pt.close();
        con.close();

        return allSessions;

    }

    public boolean checkopen() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM session where closed = 'false' ");
        rs = pt.executeQuery();
        if (rs.next()) {
            rs.close();
            pt.close();
            con.close();
            return true;

        } else {
            rs.close();
            pt.close();
            con.close();
            return false;
        }

    }

    public boolean checksesion(String session) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM session where session = '" + session + "'");
        rs = pt.executeQuery();

        if (rs.next()) {
            rs.close();
            pt.close();
            con.close();
            return true;
        } else {

            rs.close();
            pt.close();
            con.close();
            return false;
        }

    }

    public boolean createSession(session sess) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("INSERT INTO `session`(`code`,`session`,`status`,`current`,`closed`) VALUES (?,?,?,?,?);");
        pt.setString(1, sess.getCode());
        pt.setString(2, sess.getYear());
        pt.setString(3, sess.getStatus());
        pt.setString(4, String.valueOf(sess.isCurrent()));
        pt.setString(5, String.valueOf(sess.isClosed()));

        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;

        }
        pt.close();
        con.commit();
        con.close();
        if (value >= 1) {
            createSessionTables(sess.getCode());

        }
        return done;

    }

    private void createSessionTables(String code) {
        System.out.println("i got here shaa");
        try {
            con = DriverManager.getConnection("jdbc:sqlite:database");
            con.setAutoCommit(false);
            pt = con.prepareStatement("CREATE TABLE `" + code + "_resultdetails` (\n"
                    + "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                    + "	`matricnum`	INTEGER,\n"
                    + "	`coursecode`	TEXT,\n"
                    + "	`unit`	INTEGER,\n"
                    + "	`score`	NUMERIC\n"
                    + ");");
            pt.executeUpdate();

            pt = con.prepareStatement("CREATE TABLE `" + code + "_summary` (\n"
                    + "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                    + "	`matricnum`	INTEGER,\n"
                    + "	`currenttotalunits`	INTEGER,\n"
                    + "	`currentunitspassed`	INTEGER,\n"
                    + "	`currentwgp`	INTEGER,\n"
                    + "	`accumtotalunits`	INTEGER,\n"
                    + "	`accumunitspassed`	INTEGER,\n"
                    + "	`accumpwgp`	INTEGER,\n"
                    + "	`level`	INTEGER\n"
                    + ");");
            pt.executeUpdate();
            con.commit();
            pt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void insertUser(StudentData student) {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:database");
            con.setAutoCommit(false);
            pt = con.prepareStatement("INSERT INTO `users`(`username`,`password`,`role`,`firstname`,`lastname`,`firsttime`) VALUES (?,?,?,?,?,?);");
            pt.setString(1, String.valueOf(student.getMatricNumber()));
            pt.setString(2, String.valueOf(student.getMatricNumber()));
            pt.setString(3, "st");
            pt.setString(4, student.getFirstname());
            pt.setString(5, student.getSurname());
            pt.setString(6, "y");
            pt.executeUpdate();
            pt.close();
            con.commit();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public StudentData getDataForStudent(int matricNum) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM studentdata where matric_no = " + matricNum);
        rs = pt.executeQuery();
        StudentData data = new StudentData();
        while (rs.next()) {

            data.setMatricNumber(rs.getInt("matric_no"));
            data.setSurname(rs.getString("surname"));
            data.setFirstname(rs.getString("firstname"));
            data.setMiddlename(rs.getString("middlename"));
            data.setSex(rs.getString("sex"));
            data.setDob(rs.getString("dob"));
            data.setResAddress(rs.getString("res_address"));
            data.setPhone(rs.getString("phone"));
            data.setLos(rs.getString("level_study"));
            data.setNok(rs.getString("next_kin"));
            data.setNoka(rs.getString("next_kin_address"));
            data.setYoe(rs.getString("year_entry"));
            data.setMoe(rs.getString("mode_entry"));
            data.setUme(rs.getInt("jamb_score"));
            data.setPsa(rs.getString("prev_school"));
            data.setQo(rs.getString("qual_obtained"));
            data.setTolalUnits(rs.getInt("current_total"));
            data.setUme(rs.getInt("current_units"));
            data.setaWGP(rs.getInt("current_wgp"));
            System.out.println(data.getLos());
        }
        rs.close();
        pt.close();
        con.commit();
        con.close();

        return data;
    }

    public ObservableList<courses> getStudentCourses(int matricNumber) throws SQLException {
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM `" + current + "_resultdetails`" + " WHERE matricnum = " + matricNumber + " ORDER BY `coursecode`");
        rs = pt.executeQuery();
        ObservableList<courses> allCourses = FXCollections.observableArrayList();
        while (rs.next()) {
            System.out.println("foung");
            courses course = new courses();
            course.setCode(rs.getString("coursecode"));
            course.setUnit(rs.getInt("unit"));
            course.setScore(rs.getDouble("score"));

            allCourses.add(course);

        }
        pt.close();
        con.commit();
        con.close();

        return allCourses;
    }

    public ObservableList<courses> getStudentCoursesForLevel(int Level) throws SQLException {

        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM `courses`" + " WHERE level = " + Level + " ORDER BY `code`");
        rs = pt.executeQuery();
        ObservableList<courses> allCourses = FXCollections.observableArrayList();
        while (rs.next()) {
            courses course = new courses();
            course.setCode(rs.getString("code"));
            course.setUnit(rs.getInt("unit"));
            course.setTitle(rs.getString("title"));
            course.setMode(rs.getString("mode"));
            course.setPrereq(rs.getString("prereq"));
            course.setLevel(rs.getInt("level"));
            allCourses.add(course);

        }
        pt.close();
        con.commit();
        con.close();

        return allCourses;
    }

    public String getCurrent() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM session where current = 'true' ");
        rs = pt.executeQuery();
        String session = "";
        if (rs.next()) {
            session = rs.getString("code");
        } else {

            return "";
        }

        rs.close();
        pt.close();
        con.close();
        return session;
    }

    public courses getSingleCourse(String code) throws SQLException {

        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM `courses`" + " WHERE code = '" + code + "' ORDER BY `code`");
        rs = pt.executeQuery();
        courses course = new courses();
        while (rs.next()) {

            course.setCode(rs.getString("code"));
            course.setUnit(rs.getInt("unit"));
            course.setTitle(rs.getString("title"));
            course.setMode(rs.getString("mode"));
            course.setPrereq(rs.getString("prereq"));
            course.setLevel(rs.getInt("level"));

        }
        pt.close();
        con.commit();
        con.close();

        return course;
    }

    public boolean saveStudentCourses(int matricNumber, ObservableList<courses> cou) throws SQLException {
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("INSERT INTO `" + current + "_resultdetails`" + "(matricnum, coursecode, unit, score) VALUES (?, ?, ?, ?) ");
        for (courses course : cou) {
            pt.setInt(1, matricNumber);
            pt.setString(2, course.getCode());
            pt.setInt(3, course.getUnit());
            pt.setInt(4, 0);
            pt.addBatch();
        }

        boolean done = false;
        int[] value = pt.executeBatch();
        if (value.length >= 1) {
            done = true;

        }
        pt.close();
        con.commit();
        con.close();

        return done;

    }

    public boolean resetReg(Integer matricNumber) throws SQLException {
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("DELETE FROM `" + current + "_resultdetails`" + " WHERE matricnum = " + "" + matricNumber + "");

        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;

    }

    public ObservableList<Integer> getStudentInLevel(int level) throws SQLException {

        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM studentdata where level_study = " + level);
        rs = pt.executeQuery();
        ObservableList<Integer> list = FXCollections.observableArrayList();
        while (rs.next()) {

            list.add(rs.getInt("matric_no"));
        }
        rs.close();
        pt.close();
        con.commit();
        con.close();

        return list;
    }

    public ObservableList<Integer> getStudentOfferingCourse(String courseCode) throws SQLException {
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM `" + current + "_resultdetails`" + " WHERE coursecode = '" + courseCode + "' ORDER BY `coursecode`");
        rs = pt.executeQuery();
        ObservableList<Integer> list = FXCollections.observableArrayList();
        while (rs.next()) {

            list.add(rs.getInt("matricnum"));
        }
        pt.close();
        con.commit();
        con.close();

        return list;
    }

    public courses getStudentCourses(int usermatric, String value) throws SQLException {
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM `" + current + "_resultdetails`" + " WHERE matricnum = " + usermatric + " AND coursecode = '" + value + "' ORDER BY `coursecode`");
        rs = pt.executeQuery();
        courses course = new courses();
        while (rs.next()) {
            System.out.println("foung");

            course.setCode(rs.getString("coursecode"));
            course.setUnit(rs.getInt("unit"));
            course.setScore(rs.getDouble("score"));

        }
        pt.close();
        con.commit();
        con.close();

        return course;
    }

    public boolean updateStudentCourse(int matricNumber, String courseCode, double score) throws SQLException {
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("UPDATE `" + current + "_resultdetails`" + " SET score = " + score + " WHERE matricnum = " + matricNumber + " AND coursecode = '" + courseCode + "'");
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;

    }
    
    /**
     * @param courseCode the course code to check for
     * @param matricNumber the student who who are checking the matric number
     * @return true if the students offers the course and false if not
     * 
     * This method checks if a particular student offers a course
     **/
    public boolean checkForStudentCourse(int matricNumber, String courseCode) throws SQLException {
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
//          System.out.println("SELECT * FROM `" + current + "_resultdetails`" + " WHERE matricnum = " + matricNumber + " AND coursecode = " + courseCode);
      
        pt = con.prepareStatement("SELECT * FROM `" + current + "_resultdetails`" + " WHERE matricnum = '" + matricNumber + "' AND coursecode = '" + courseCode+"'");

//        System.out.println("SELECT * FROM `" + current + "_resultdetails`" + " WHERE matricnum = " + matricNumber + " AND coursecode = " + courseCode);
        
        rs = pt.executeQuery();

        if (rs.next()) {

            rs.close();
            pt.close();

            con.close();
            return true;

        } else {
            rs.close();
            pt.close();

            con.close();
            return false;
        }

    }

    public ObservableList<StudentData> getStudentDataForLevel(int level) throws SQLException {
        System.out.println("got here");
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM studentdata where level_study = '" + String.valueOf(level) + "'");
        rs = pt.executeQuery();
        ObservableList<StudentData> allData = FXCollections.observableArrayList();
        while (rs.next()) {
            StudentData data = new StudentData();
            data.setMatricNumber(rs.getInt("matric_no"));
            data.setSurname(rs.getString("surname"));
            data.setFirstname(rs.getString("firstname"));
            data.setMiddlename(rs.getString("middlename"));
            data.setSex(rs.getString("sex"));
            data.setDob(rs.getString("dob"));
            data.setResAddress(rs.getString("res_address"));
            data.setPhone(rs.getString("phone"));
            data.setLos(rs.getString("level_study"));
            data.setNok(rs.getString("next_kin"));
            data.setNoka(rs.getString("next_kin_address"));
            data.setYoe(rs.getString("year_entry"));
            data.setMoe(rs.getString("mode_entry"));
            data.setUme(rs.getInt("jamb_score"));
            data.setPsa(rs.getString("prev_school"));
            data.setQo(rs.getString("qual_obtained"));
            data.setTolalUnits(rs.getInt("current_total"));
            data.setUme(rs.getInt("current_units"));
            data.setaWGP(rs.getInt("current_wgp"));
            allData.add(data);

        }
        rs.close();
        pt.close();
        con.commit();
        con.close();

        return allData;

    }

    public boolean checkSessionClosed() throws SQLException {

        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM session where current = 'true' AND closed = 'true'; ");
        rs = pt.executeQuery();

        return rs.next();

    }

    public boolean insertUser(User user) throws SQLException {

        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("INSERT INTO `users`(`username`,`password`,`role`,`firstname`,`lastname`,`firsttime`) VALUES (?,?,?,?,?,?);");
        pt.setString(1, user.getUsername());
        pt.setString(2, user.getPassword());
        pt.setString(3, user.getRole());
        pt.setString(4, user.getFirstname());
        pt.setString(5, user.getLastname());
        pt.setString(6, "y");
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;
    }

    public boolean checkForPrint(int matricNumber) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM fingerprint where matricnum = " + matricNumber);
        rs = pt.executeQuery();

        return rs.next();

    }

    public File getFingerPrintImage(int matricNumber) throws Exception {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT * FROM fingerprint where matricnum = " + matricNumber);
        rs = pt.executeQuery();

        File fileimage = null;
        try {
            fileimage = File.createTempFile("image", null);
        } catch (IOException ex) {

        }
        if (rs.next()) {
            byte[] img = rs.getBytes("image");
            FileOutputStream fo = new FileOutputStream(fileimage);
            fo.write(img);
            fo.flush();
            fo.close();
            System.out.println("We found Print");
        } else {
            fileimage = null;
            System.out.println("Mba ooo...u neva register");
        }

        rs.close();
        pt.close();
        con.close();

        return fileimage;

    }

    public boolean enrollFingerPrint(int matricNumber, byte[] imageBytes, byte[] pictureBytes) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("INSERT INTO fingerprint (matricnum, print, image) VALUES (?, ?, ?) ");
        pt.setInt(1, matricNumber);
        pt.setBytes(2, imageBytes);
        pt.setBytes(3, pictureBytes);

        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }
        pt.close();
        con.commit();
        con.close();

        return done;
    }

    public String verifyFingerPrint(int matricNumber, Template template) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("SELECT print FROM fingerprint where matricnum = " + matricNumber);
        rs = pt.executeQuery();
        String msg = "";
        if (rs.next()) {
            byte templateBuffer[] = rs.getBytes("print");
            //Creates a new Template
            Template referenceTemplate = new Template(templateBuffer);
            boolean matched = false;
            try {
                matched = FingerPrint.fingerprintSDK.verify(template, referenceTemplate);
            } catch (GrFingerJavaException ex) {
                Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (matched) {
                try {
                    msg += "Matched with score = " + FingerPrint.fingerprintSDK.getScore();
                } catch (GrFingerJavaException ex) {
                    Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    msg += "Did not Match with score = " + FingerPrint.fingerprintSDK.getScore();
                } catch (GrFingerJavaException ex) {
                    Logger.getLogger(DBTransactions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        rs.close();
        pt.close();
        con.close();
        return msg;
    }

    public String identifyFingerPrint(Template template) throws SQLException, GrFingerJavaException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        fingerprintSDK.prepareForIdentification(template);
        pt = con.prepareStatement("SELECT * FROM fingerprint ");
        rs = pt.executeQuery();
        String msg = "";

        while (rs.next()) {

            byte[] templateBuffer = rs.getBytes("print");
            //And creates a new Template
            Template referenceTemplate = new Template(templateBuffer);

            //Compares current template.
            boolean matched = fingerprintSDK.identify(referenceTemplate);
            if (matched) {
                String number = String.valueOf(rs.getInt("matricnum"));
                rs.close();
                pt.close();
                con.close();
                //Stops searching

                return number;
            }

        }

        rs.close();
        pt.close();
        con.close();
        return msg;
    }

    public boolean deleteFingerPrint(int matricNumber) throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        pt = con.prepareStatement("DELETE FROM fingerprint where matricnum = " + matricNumber);
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }

        pt.close();
        con.commit();
        con.close();
        return done;
    }

    
    public boolean startAttendance(String courseCode) throws SQLException {
         String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        java.util.Date date = new java.util.Date();
        String sendDate = new SimpleDateFormat("MM-dd-yyyy").format(date);
        String startTime = new SimpleDateFormat("h:mm a").format(date);

        pt = con.prepareStatement("INSERT INTO `" + current + "_attendance` (course, date, start_time ) VALUES (?, ?, ?) ",PreparedStatement.RETURN_GENERATED_KEYS);
        
        pt.setString(1, courseCode);
        pt.setString(2, sendDate);
        pt.setString(3, startTime);
        
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
           
            
        }

        
        con.commit();
        rs = pt.getGeneratedKeys();
        Attendance.setId(rs.getInt(1));
        pt.close();
        con.close();
        return done;
        
        
    }
    
    
    public boolean endAttendance(int id) throws SQLException {
         String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        java.util.Date date = new java.util.Date();
        String endTime = new SimpleDateFormat("h:mm a").format(date);

        pt = con.prepareStatement("UPDATE `" + current + "_attendance` SET end_time = '"+endTime +"' WHERE ID = '"+id+"'");
        
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
        }

        pt.close();
        con.commit();
        con.close();
        return done;
        
        
    }
    
    
    public boolean markAttendance(int matricNum) throws SQLException{
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        con.setAutoCommit(false);
        
        String course = Attendance.getCourseCode();
        int id = Attendance.getId();
        java.util.Date date = new java.util.Date();
        String time = new SimpleDateFormat("h:mm a").format(date);

        pt = con.prepareStatement("INSERT INTO `" + current + "_register` (student, course, time, attendance_id ) VALUES (?, ?, ?, ?) ");
        pt.setInt(1, matricNum);
        pt.setString(2, course);
        pt.setString(3, time);
        pt.setInt(4, id);
        boolean done = false;
        int value = pt.executeUpdate();
        if (value >= 1) {
            done = true;
            System.out.println("added");
        }

        pt.close();
        con.commit();
        con.close();
        return done;
    
    }
    
    
    public ObservableList<Register> generateAttendance(String course) throws SQLException{
        String current = getCurrent();
        con = DriverManager.getConnection("jdbc:sqlite:database");
        
//        pt = con.prepareStatement("SELECT student, COUNT(*) FROM "+ current+"_register where course = ? GROUP BY student ");
        
        
 
        return null;
    }
    
    
}
