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
public class StudentData {

    int matricNumber;
    String surname;
    String firstname;
    String middlename;
    String sex;
    String dob;
    String resAddress;
    String phone;
    String los;
    String nok;
    String noka;
    String yoe;
    String moe;
    int ume;
    String psa;
    String qo;
    int tolalUnits;
    int unitPassed;
    int aWGP;

    public StudentData(int matricNumber, String surname, String firstname, String middlename, String sex, String dob, String resAddress, String phone, String los, String nok, String noka, String yoe, String moe, int ume, String psa, String qo, int tolalUnits, int unitPassed, int aWGP) {
        this.matricNumber = matricNumber;
        this.surname = surname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.sex = sex;
        this.dob = dob;
        this.resAddress = resAddress;
        this.phone = phone;
        this.los = los;
        this.nok = nok;
        this.noka = noka;
        this.yoe = yoe;
        this.moe = moe;
        this.ume = ume;
        this.psa = psa;
        this.qo = qo;
        this.tolalUnits = tolalUnits;
        this.unitPassed = unitPassed;
        this.aWGP = aWGP;
    }

    public StudentData() {
  
    }

    public int getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(int matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getResAddress() {
        return resAddress;
    }

    public void setResAddress(String resAddress) {
        this.resAddress = resAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLos() {
        return los;
    }

    public void setLos(String los) {
        this.los = los;
    }

    public String getNok() {
        return nok;
    }

    public void setNok(String nok) {
        this.nok = nok;
    }

    public String getNoka() {
        return noka;
    }

    public void setNoka(String noka) {
        this.noka = noka;
    }

    public String getYoe() {
        return yoe;
    }

    public void setYoe(String yoe) {
        this.yoe = yoe;
    }

    public String getMoe() {
        return moe;
    }

    public void setMoe(String moe) {
        this.moe = moe;
    }

    public int getUme() {
        return ume;
    }

    public void setUme(int ume) {
        this.ume = ume;
    }

    public String getPsa() {
        return psa;
    }

    public void setPsa(String psa) {
        this.psa = psa;
    }

    public String getQo() {
        return qo;
    }

    public void setQo(String qo) {
        this.qo = qo;
    }

    public int getTolalUnits() {
        return tolalUnits;
    }

    public void setTolalUnits(int tolalUnits) {
        this.tolalUnits = tolalUnits;
    }

    public int getUnitPassed() {
        return unitPassed;
    }

    public void setUnitPassed(int unitPassed) {
        this.unitPassed = unitPassed;
    }

    public int getaWGP() {
        return aWGP;
    }

    public void setaWGP(int aWGP) {
        this.aWGP = aWGP;
    }

}
