/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package bean.view;

import bean.CourseController;
import bean.FacultyController;
import bean.FundController;
import bean.PublicationController;
import common.MiniVitaStore;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.RequestScoped;
import models.Course;
import models.Faculty;
import models.Funding;
import models.Publication;

/**
 *
 * @author swapn
 */
//class responsible for all the tricky redirections and keeping track of current hash objects
@ManagedBean
@RequestScoped
public class StageManager {

    @ManagedProperty(value="#{miniVitaStore}")
    private MiniVitaStore miniVitaStore;
    @ManagedProperty(value="#{facultyController}")
    private FacultyController facultyController;
    @ManagedProperty(value="#{courseController}")
    private CourseController courseController;   
    @ManagedProperty(value="#{fundController}") 
    private FundController fundController;
    @ManagedProperty(value="#{publicationController}") 
    private PublicationController publicationController;

    /**
     * Creates a new instance of StageManager
     */
    public StageManager() {
    }
    
    public String openNewMinivita(){
        facultyController.setFaculty(new Faculty());
        return "/minivita/newMinivita.xhtml";
    }
    
    public String openMinivita(){
//        System.out.println("Opening F "+facultyHash);
//        facultyController.setFaculty(miniVitaStore.getFaculty(facultyHash));
        return "/minivita/minivita.xhtml";
    }
    
    public String openNewCourse(){
        courseController.setCourse(new Course());
        return "/minivita/course/newCourse.xhtml";
    }
    
    public String openCourse(){
//        courseController.setCourse(facultyController.getFaculty().getCourses(courseHash));
        return "/minivita/course/course.xhtml";
    }
    
    public String openNewPublication(){
        publicationController.setPublication(new Publication());
        return "/minivita/publication/newPublication.xhtml";
    }
    
    public String openPublication(){
//        courseController.setCourse(facultyController.getFaculty().getCourses(courseHash));
        return "/minivita/publication/publication.xhtml";
    }

    public CourseController getCourseController() {
        return courseController;
    }

    public void setCourseController(CourseController courseController) {
        this.courseController = courseController;
    }

    public FundController getFundController() {
        return fundController;
    }

    public void setFundController(FundController fundController) {
        this.fundController = fundController;
    }

    public FacultyController getFacultyController() {
        return facultyController;
    }

    public void setFacultyController(FacultyController facultyController) {
        this.facultyController = facultyController;
    }
    
    public MiniVitaStore getMiniVitaStore() {
        return miniVitaStore;
    }

    public void setMiniVitaStore(MiniVitaStore miniVitaStore) {
        this.miniVitaStore = miniVitaStore;
    }
    
    public String openNewFunding(){
        fundController.setFunding(new Funding());
        return "funding/newFunding.xhtml";
    }
    
    public String openFunding(){
//        courseController.setCourse(facultyController.getFaculty().getCourses(courseHash));
        return "funding/funding.xhtml";
    }

    public PublicationController getPublicationController() {
        return publicationController;
    }

    public void setPublicationController(PublicationController publicationController) {
        this.publicationController = publicationController;
    }

}
