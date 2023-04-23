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
import models.Funding;

/**
 *
 * @author swapn
 */
@ManagedBean
@SessionScoped
public class FundController implements Serializable{
    private Funding funding;

    @ManagedProperty(value="#{facultyController}")
    private FacultyController facultyController;
    @ManagedProperty(value="#{miniVitaStore}")
    private MiniVitaStore miniVitaStore;
    /**
     * Creates a new instance of FundController
     */
    public FundController() {
        funding = new Funding();
    }

    public Funding getFunding() {
        return funding;
    }

    public void setFunding(Funding funding) {
        this.funding = funding;
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

    //method to add funding into the current faculty this will eventually become a db logic
    public String addFunding(){
        System.out.println("/minivita/minivita.xhtml?hash="+facultyController.getFaculty().hashCode());
        facultyController.getFaculty().getFundings().add(funding);
        return "/minivita/minivita.xhtml?hash="+facultyController.getFaculty().hashCode();
    }
    
    //method to remove funding from the current faculty this will eventually become a db logic
    public String tryDeletingFunding(int hashCode){
        //db code here
        if(miniVitaStore.removeFunding(facultyController.getFaculty().hashCode(),hashCode)){
               return "/minivita/minivita.xhtml?hash="+facultyController.getFaculty().hashCode();
        }else{
            return "";
        }
    }
}
