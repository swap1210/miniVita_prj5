/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package bean;

import bean.view.StageManager;
import common.MiniVitaStore;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.SessionScoped;
import java.io.Serializable;
import models.Course;
import models.Faculty;

/**
 *
 * @author swapn
 */
@ManagedBean
@SessionScoped
public class CourseController implements Serializable{
    private Faculty faculty;
    private Course course;
    @ManagedProperty(value="#{miniVitaStore}")
    private MiniVitaStore miniVitaStore;
    @ManagedProperty(value="#{facultyController}")
    private FacultyController facultyController;
    /**
     * Creates a new instance of CourseController
     */
    public CourseController() {
        course = new Course();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public MiniVitaStore getMiniVitaStore() {
        return miniVitaStore;
    }

    public void setMiniVitaStore(MiniVitaStore miniVitaStore) {
        this.miniVitaStore = miniVitaStore;
    }

    public FacultyController getFacultyController() {
        return facultyController;
    }

    public void setFacultyController(FacultyController facultyController) {
        this.facultyController = facultyController;
    }

    //method to add this course to current minivita
    public String addCourse(){
        facultyController.getFaculty().getCourses().add(course);
        return "/minivita/minivita.xhtml?hash="+facultyController.getFaculty().hashCode();
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
    //method to delete sepcific course from current minivita
    public String tryDeletingCourse(int hashCode) throws Exception{
        //db code here
        if(miniVitaStore.removeCourse(facultyController.getFaculty().hashCode(),hashCode)){
               return "/minivita/minivita.xhtml?hash="+facultyController.getFaculty().hashCode();
        }else{
            return "";
        }
    }
    
}
