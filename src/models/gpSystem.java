/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author OTHREE
 */
public class gpSystem {

    static double MARK;
    String GPSYSTEM;
    static int unitGrade;

    public gpSystem(String point) {
        if (point.equals("7")) {
            GPSYSTEM = "7";
        }

    }

    public static double getMARK() {
        return MARK;
    }

    public int setMARK(double MARK) {
        gpSystem.MARK = MARK;
        return setgrades();
    }

    int setgrades() {
        if (GPSYSTEM.equals("7")) {
            return set7pointSystem();
        } else {
            return 0;

        }

    }

    private int set7pointSystem() {
        if (MARK >= 70) {
            unitGrade = 7;
        } else if (MARK >= 65) {
            unitGrade = 6;
        } else if (MARK >= 60) {
            unitGrade = 5;
        } else if (MARK >= 55) {
            unitGrade = 4;
        } else if (MARK >= 50) {
            unitGrade = 3;
        } else if (MARK >= 45) {
            unitGrade = 2;
        } else if (MARK >= 40) {
            unitGrade = 1;
        } else {
            unitGrade = 0;
        }
        return unitGrade;
    }
    
    public String getGradeRemark(){
    
    if(GPSYSTEM.equals("7")){
    
    return "" 
+ 
"   GRADE        GRADE POINT         MARK            CUM. GRADE POINT AVERAGE\n" +
"   *****        ***********         *****           ************************\n" +
"     A              7               70&Above        60 & Above First Class\n" +
"     A-             6               65-69           4.6 - 5.9 2nd Class(Upper)\n" +
"     B+             5               60-64           2.5 - 4.5 2nd Class(Lower)\n" +
"     B              4               55-59           1.6-2.5 3rd Class\n" +
"     B-             3               50-54           1.0-1.5 Pass\n" +
"     C+             2               45-49           0-0.9 Fail\n" +
"     C              1               40-44           \n" +
"     D              0               0-39            ";
    }
    else {
    return null;
    }
    }

}
