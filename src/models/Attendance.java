/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author OTHREE
 */
public class Attendance {
    static int id;
    static String courseCode;
    static String date;
    static String startTime;
    static String endTime;

    public static String getCourseCode() {
        return courseCode;
    }

    public static void setCourseCode(String courseCode) {
        Attendance.courseCode = courseCode;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Attendance.date = date;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Attendance.id = id;
    }

    public static String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String startTime) {
        Attendance.startTime = startTime;
    }

    public static String getEndTime() {
        return endTime;
    }

    public static void setEndTime(String endTime) {
        Attendance.endTime = endTime;
    }
    
  
    
    
}
