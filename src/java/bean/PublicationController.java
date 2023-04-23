/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package bean;

import common.MiniVitaStore;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.RequestScoped;
import java.io.Serializable;
import models.Publication;

/**
 *
 * @author swapn
 */
@ManagedBean
@SessionScoped
public class PublicationController implements Serializable{
    private Publication publication;

    @ManagedProperty(value="#{facultyController}")
    private FacultyController facultyController;
    @ManagedProperty(value="#{miniVitaStore}")
    private MiniVitaStore miniVitaStore;
    /**
     * Creates a new instance of FundController
     */
    public PublicationController() {
        publication = new Publication();
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
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
    //method to add publication into the current faculty this will eventually become a db logic
    public String addPublication(){
        facultyController.getFaculty().getPublications().add(publication);
        return "/minivita/minivita.xhtml?hash="+facultyController.getFaculty().hashCode();
    }
    //method to remove funding into the current faculty this will eventually become a db logic
    public String deletingPublication(int hashCode){
        //db code here
        if(miniVitaStore.removePublication(facultyController.getFaculty().hashCode(),hashCode)){
               return "/minivita/minivita.xhtml?hash="+facultyController.getFaculty().hashCode();
        }else{
            return "";
        }
    }
}
