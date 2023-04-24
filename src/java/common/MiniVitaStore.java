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
            System.out.println(" Faculty name "+rs.getString("name"));
            Faculty tempFaculty = new Faculty(rs.getString("name"), rs.getString("email"), rs.getInt("year"));
            loadCoursesFromDB(tempFaculty);
            //add publication
            //add fundings
            faculties.add(tempFaculty);
        }
    }

    public void loadCoursesFromDB(Faculty faculty) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("select * from course where facultyId = " + faculty.hashCode());
        faculty.setCourses(new ArrayList<>());
        while (rs.next()) {
            System.out.println("  Course found "+rs.getString("code"));
            faculty.getCourses().add(new Course(rs.getString("code"), rs.getString("name"), rs.getInt("creditHours"), rs.getString("department")));
        }
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
            if (f.hashCode() == findCode) {
                return f;
            }
        }
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
    }

    public boolean removeCourse(int facultyCode, int courseHash) throws Exception {
        //db code here
        boolean flag = false;
        try {
            PreparedStatement stmt1 = con.prepareStatement("DELETE FROM course WHERE id = ? and facultyId= ?");
            stmt1.setInt(1, courseHash);
            stmt1.setInt(1, facultyCode);
            stmt1.close();
            flag = true;
        } catch (SQLException e) {

        }
        return flag;
    }

    public boolean removeFunding(int facultyCode, int fundingHash) {
        //db code here
        Faculty fac = getFaculty(facultyCode);
        for (Funding f : fac.getFundings()) {
            if (f.hashCode() == fundingHash) {
                fac.getFundings().remove(f);
                return true;
            }
        }
        return false;
    }

    public boolean removePublication(int facultyCode, int fundingHash) {
        //db code here
        Faculty fac = getFaculty(facultyCode);
        for (Publication f : fac.getPublications()) {
            if (f.hashCode() == fundingHash) {
                fac.getPublications().remove(f);
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        closeConnection();
    }

}
