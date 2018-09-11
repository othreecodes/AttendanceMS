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
public class result {

    int unit;
    double score;

    double gradepoint;
    double wgp;
    String Remark;
    
    
    public result() {
    }

    public result result(int unit, double score) {
        gpSystem sys = new gpSystem("7");
        gradepoint = sys.setMARK(score);
        wgp = gradepoint * unit;
        Remark = getRemark(score);
        result theResult = new result();
        theResult.setGradepoint(gradepoint);
        theResult.setWgp(wgp);
        theResult.setRemark(Remark);
        return theResult;

    }

    public double getGradepoint() {
        return gradepoint;
    }

    public void setGradepoint(double gradepoint) {
        this.gradepoint = gradepoint;
    }

    public double getWgp() {
        return wgp;
    }

    public void setWgp(double wgp) {
        this.wgp = wgp;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    private String getRemark(double score) {
        if (score >= 49) {
            return "PASSED";
        } else {
            return "FAILED";
        }

    }

}
