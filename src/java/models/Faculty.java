/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author swapn
 */
public class Faculty  implements Serializable{
    private String name;
    private String email;
    private int year;
    private List<Course> courses;
    private List<Funding> fundings;
    private List<Publication> publications;
    
    public Faculty(){
        name = "";
        email = "";
        courses = new ArrayList<>();
        fundings = new ArrayList<>();
        publications = new ArrayList<>();
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode()*this.email.hashCode();
    }
        
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Faculty objToCompare = (Faculty) o;
        return (this.name.equals(objToCompare.name) 
          && this.email.equals(objToCompare.email));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourses() {
        return courses;
    }
    
    public Course getCourse(int findCode){
        for (Course c : courses) {
            if (c.hashCode() == findCode)
                return c;
        }
        return null;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public void addCourse(Course course){
        this.courses.add(course);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Funding> getFundings() {
        return fundings;
    }

    public void setFundings(List<Funding> fundings) {
        this.fundings = fundings;
    }
    
    public void addFunding(Funding funding){
        this.fundings.add(funding);
    }
    
    public Funding getFunding(int findCode){
        for (Funding f : fundings) {
            if (f.hashCode() == findCode)
                return f;
        }
        return null;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }
    
    public Publication getPublication(int findCode){
        for (Publication p : publications) {
            if (p.hashCode() == findCode)
                return p;
        }
        return null;
    }
}
