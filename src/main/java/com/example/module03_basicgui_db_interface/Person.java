package com.example.module03_basicgui_db_interface;

public class Person {


    private int id;
    private String firstName;
    private String lastName;
    private String dept;
    private String major;
    private String course;
    private String email;
    private String DOB;
    private int zipCode;
    private String password;

    public Person() {
    }


    public Person(int id, String firstName, String lastName, String dept, String major, String course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.dept = dept;
        this.course = course;
    }

    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Person(String firstName, String lastName, String email, String DOB, int zipCode, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.DOB = DOB;
        this.zipCode = zipCode;
        this.password = password;
    }

    public Person(int id, String firstName, String lastName, String dept, String major, String course, String email, String DOB, int zipCode, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dept = dept;
        this.major = major;
        this.course = course;
        this.email = email;
        this.DOB = DOB;
        this.zipCode = zipCode;
        this.password = password;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}