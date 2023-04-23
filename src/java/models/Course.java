/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;

/**
 *
 * @author swapn
 */
public class Course implements Serializable{
    // Attributes of the UniversityCourse class
    private String code;
    private String name;
    private int creditHours;
    private String department;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Course objToCompare = (Course) obj;
        return (this.code.equals(objToCompare.code) 
          && this.name.equals(objToCompare.name)
          && this.department.equals(objToCompare.department))
                && this.creditHours == objToCompare.creditHours;
    }

    @Override
    public int hashCode() {
        return this.department.hashCode()*this.name.hashCode()*this.code.hashCode()*(this.creditHours+20); 
    }

    public Course() {
        this.code = "";
        this.name = "";
        this.creditHours = 0;
        this.department = "";
    }
    
    public Course(String code, String name, int creditHours, String department) {
        this.code = code;
        this.name = name;
        this.creditHours = creditHours;
        this.department = department;
    }
    
    // Getters and setters for the attributes of the UniversityCourse class
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
