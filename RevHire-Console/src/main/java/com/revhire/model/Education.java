package com.revhire.model;

public class Education {
    private int id;
    private int userId;
    private String institution;
    private String degree;
    private int year;
    private double gpa;

    public Education() {
    }

    public Education(int id, int userId, String institution, String degree, int year, double gpa) {
        this.id = id;
        this.userId = userId;
        this.institution = institution;
        this.degree = degree;
        this.year = year;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return degree + " from " + institution + " (" + year + ")";
    }
}
