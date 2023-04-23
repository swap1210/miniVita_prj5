/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package common;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ApplicationScoped;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import models.Course;
import models.Faculty;
import models.Funding;
import models.Publication;
import models.User;

/**
 *
 * @author swapn
 */
//main class that lives on during the entire life span of application and keeps track of all the data 
@ManagedBean
@ApplicationScoped
public class MiniVitaStore implements Serializable{

    private Set<User> users;
    private Set<Faculty> faculties;
    /**
     * Creates a new instance of MiniVitaStore
     */
    public MiniVitaStore() {
        System.out.println("MiniVitaStore Cons");
        final User[] usersArr = {new User("u1","p"),new User("u2","p"),new User("u3","p")};
        users = new HashSet<>();
        faculties = new HashSet<>();
        users.add(usersArr[0]);
        users.add(usersArr[1]);
        users.add(usersArr[2]);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }
    
    public Faculty getFaculty(int findCode){
        for (Faculty f : faculties) {
            if (f.hashCode() == findCode)
                return f;
        }
        return null;
    }
  
    public boolean removeFaculty(int findCode){
        //db code here
        
        for (Faculty f : faculties) {
            if (f.hashCode() == findCode){ 
                faculties.remove(f);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeCourse(int facultyCode,int courseHash) throws Exception{
        //db code here
        Faculty fac = getFaculty(facultyCode);
        if (fac == null){
            throw new Exception("Faculty not found");
        }
        for (Course c : fac.getCourses()) {
            if (c.hashCode() == courseHash){ 
                fac.getCourses().remove(c);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeFunding(int facultyCode,int fundingHash){
        //db code here
        Faculty fac = getFaculty(facultyCode);
        for (Funding f : fac.getFundings()) {
            if (f.hashCode() == fundingHash){ 
                fac.getFundings().remove(f);
                return true;
            }
        }
        return false;
    }
    
    public boolean removePublication(int facultyCode,int fundingHash){
        //db code here
        Faculty fac = getFaculty(facultyCode);
        for (Publication f : fac.getPublications()) {
            if (f.hashCode() == fundingHash){ 
                fac.getPublications().remove(f);
                return true;
            }
        }
        return false;
    }
}
