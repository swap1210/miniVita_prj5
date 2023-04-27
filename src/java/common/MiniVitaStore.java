/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package common;

import db.MyDB;
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

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.FundingEntity;
import models.Publisher;

/**
 *
 * @author swapn
 */
//main class that lives on during the entire life span of application and keeps track of all the data 
@ManagedBean
@ApplicationScoped
public final class MiniVitaStore implements Serializable, AutoCloseable {

    private Set<User> users;
    private Set<Faculty> faculties;
    private Connection con;

    /**
     * Creates a new instance of MiniVitaStore
     */
    public MiniVitaStore() throws SQLException {
        System.out.println("MiniVitaStore Cons");
        loadConnection();
        loadUser();
        loadFaultiesFromDB();
    }

    public void loadConnection() throws SQLException {
        con = MyDB.getConnection();
    }

    public void reloadConnection() throws SQLException {
        System.out.println("closeConnection");
        try {
            con.close();
            loadConnection();
        } catch (SQLException e) {
            System.out.println("Error in close");
        }
    }

    public void closeConnection() throws SQLException {
        System.out.println("closeConnection");
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error in close");
        }
    }

    public void loadUser() throws SQLException {
        users = new HashSet<>();
        ResultSet rs = con.createStatement().executeQuery("select * from credentials");
        while (rs.next()) {
            users.add(new User(rs.getString("username"), rs.getString("password")));
        }
    }

    public void loadFaultiesFromDB() throws SQLException {
        System.out.println("Called faculty reload");
        faculties = new HashSet<>();
        ResultSet rs = con.createStatement().executeQuery("select * from minivita");
        while (rs.next()) {
            System.out.println(" Faculty name " + rs.getString("name"));
            Faculty tempFaculty = new Faculty(rs.getString("name"), rs.getString("email"), rs.getInt("year"));
            loadCoursesFromDB(tempFaculty);
            loadPublicationFromDB(tempFaculty);
            loadFundingFromDB(tempFaculty);
            faculties.add(tempFaculty);
        }
    }

    public void loadCoursesFromDB(Faculty faculty) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from course where facultyId = " + faculty.hashCode());
        faculty.setCourses(new ArrayList<>());
        while (rs.next()) {
            faculty.getCourses().add(new Course(rs.getString("code"), rs.getString("name"), rs.getInt("creditHours"), rs.getString("department")));
        }
    }
    
    public void loadFundingFromDB(Faculty faculty) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from Funding where faculty_Id = " + faculty.hashCode());
        faculty.setFundings(new ArrayList<>());
        while (rs.next()) {
            FundingEntity fe = loadFundingEntityFromDB(rs.getInt("id"));
            Funding tempFund = new Funding(rs.getString("name"), rs.getDouble("amount"), fe);
            faculty.getFundings().add(tempFund);
        }
    }
    
    public FundingEntity loadFundingEntityFromDB(int fundingEntityId) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from funding_entity where funding_id = " + fundingEntityId);
        FundingEntity fe = null;
        while (rs.next()) {
            fe = new FundingEntity(rs.getString("name"),rs.getInt("establishedIn"));
        }
        return fe;
    }
    
    public void loadPublicationFromDB(Faculty faculty) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from publication where faculty_Id = " + faculty.hashCode());
        faculty.setPublications(new ArrayList<>());
        while (rs.next()) {
            Publisher pr = loadPublisherFromDB(rs.getInt("id"));
            Publication tempPub = new Publication(rs.getString("title"), rs.getString("content"), pr,rs.getInt("year"));
            faculty.getPublications().add(tempPub);
        }
    }
    
    public Publisher loadPublisherFromDB(int publisherId) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from publisher where pub_id = " + publisherId);
        Publisher pr = null;
        while (rs.next()) {
            pr = new Publisher(rs.getString("name"),rs.getString("email"));
        }
        return pr;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
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

    public Faculty getFaculty(int findCode) {
        for (Faculty f : faculties) {
            System.out.println("Looking for "+findCode+" have "+f.hashCode());
            if (f.hashCode() == findCode) {
                return f;
            }
        }
        System.out.println("Couldn't find faculty with hash "+findCode);
        return null;
    }

    public void addFaculty(Faculty f) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `minivita`(`id`, `name`, `email`, `year`) VALUES (?,?,?,?)");
        stmt1.setInt(1, f.hashCode());
        stmt1.setString(2, f.getName());
        stmt1.setString(3, f.getEmail());
        stmt1.setInt(4, f.getYear());
        int r = stmt1.executeUpdate();
    }

    public boolean removeFaculty(int findCode) {
        //db code here
        boolean flag = false;
        try {
            //remove faculty and other cascaded records
            PreparedStatement stmt1 = con.prepareStatement("DELETE FROM `minivita` WHERE id = ?");
            stmt1.setInt(1, findCode);
            int r = stmt1.executeUpdate();
            flag = true;
            stmt1.close();
        } catch (SQLException e) {

        }

        return flag;
    }

    public void addCourse(int facultyCode, Course c) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `course`(`id`, `code`, `name`, `creditHours`, `department`, `facultyId`) VALUES (?,?,?,?,?,?)");
        stmt1.setInt(1, c.hashCode());
        stmt1.setString(2, c.getCode());
        stmt1.setString(3, c.getName());
        stmt1.setInt(4, c.getCreditHours());
        stmt1.setString(5, c.getDepartment());
        stmt1.setInt(6, facultyCode);
        int r = stmt1.executeUpdate();
        stmt1.close();
    }

    public boolean removeCourse(int facultyCode, int courseHash) throws Exception {
        //db code here
        boolean flag = false;
        try {
            PreparedStatement stmt1 = con.prepareStatement("DELETE FROM course WHERE id = ? and facultyId= ?");
            stmt1.setInt(1, courseHash);
            stmt1.setInt(2, facultyCode);
            int r = stmt1.executeUpdate();
            stmt1.close();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error exec statement "+e);
        }
        return flag;
    }

    
    public void addFunding(int facultyCode, Funding f) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `Funding`(`id`, `name`, `amount`, `faculty_Id`) VALUES (?,?,?,?)");
        stmt1.setInt(1, f.hashCode());
        stmt1.setString(2, f.getName());
        stmt1.setDouble(3, f.getAmount());
        stmt1.setInt(4, facultyCode);
        int r = stmt1.executeUpdate();
        stmt1.close();
        addFundingEntity(f.getFundingEnity(),f.hashCode());
    }
    
    public void addFundingEntity(FundingEntity fe,int funding_id) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `funding_entity`(`id`, `name`, `establishedIn`, `funding_id`) VALUES (?,?,?,?)");
        stmt1.setInt(1, fe.hashCode());
        stmt1.setString(2, fe.getName());
        stmt1.setInt(3, fe.getEstablishedIn());
        stmt1.setInt(4, funding_id);
        int r = stmt1.executeUpdate();
        stmt1.close();
    }
    
    public boolean removeFunding(int facultyCode, int fundingHash) {
        boolean flag = false;
        try {
            PreparedStatement stmt1 = con.prepareStatement("DELETE FROM Funding WHERE id = ? and faculty_Id= ?");
            stmt1.setInt(1, fundingHash);
            stmt1.setInt(2, facultyCode);
            int r = stmt1.executeUpdate();
            stmt1.close();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error exec statement "+e);
        }
        return flag;
    }

    public void addPublication(int facultyCode, Publication p) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `publication`(`id`, `title`, `content`, `year`, `faculty_Id`) VALUES (?,?,?,?,?)");
        stmt1.setInt(1, p.hashCode());
        stmt1.setString(2, p.getTitle());
        stmt1.setString(3, p.getContent());
        stmt1.setInt(4, p.getYear());
        stmt1.setInt(5, facultyCode);
        int r = stmt1.executeUpdate();
        stmt1.close();
        addPublisher(p.getPublisher(),p.hashCode());
    }
    
    public void addPublisher(Publisher pr,int pub_id)throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `Publisher`(`id`, `name`, `email`, `pub_id`) VALUES (?,?,?,?)");
        stmt1.setInt(1, pr.hashCode());
        stmt1.setString(2, pr.getName());
        stmt1.setString(3, pr.getEmail());
        stmt1.setInt(4, pub_id);
        int r = stmt1.executeUpdate();
        stmt1.close();
    }
    
    public boolean removePublication(int facultyCode, int publicationHash) {
        boolean flag = false;
        try {
            PreparedStatement stmt1 = con.prepareStatement("DELETE FROM publication WHERE id = ? and faculty_Id= ?");
            stmt1.setInt(1, publicationHash);
            stmt1.setInt(2, facultyCode);
            int r = stmt1.executeUpdate();
            stmt1.close();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error exec statement "+e);
        }
        return flag;
    }

    @Override
    public void close() throws Exception {
        closeConnection();
    }

}
