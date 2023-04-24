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

/**
 *
 * @author swapn
 */
//main class that lives on during the entire life span of application and keeps track of all the data 
@ManagedBean
@ApplicationScoped
public final class MiniVitaStore implements Serializable,AutoCloseable {

    private Set<User> users;
    private Set<Faculty> faculties;
    private Connection con;
    private Statement stmt;

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
        stmt = con.createStatement();
    }
    public void closeConnection() throws SQLException {
        try {
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error in close");
        }
    }


    public void loadUser() throws SQLException {
        users = new HashSet<>();
        ResultSet rs = stmt.executeQuery("select * from credentials");
        while (rs.next()) {
            users.add(new User(rs.getString("username"), rs.getString("password")));
        }
    }

    public void loadFaultiesFromDB() throws SQLException {
        faculties = new HashSet<>();
        ResultSet rs = stmt.executeQuery("select * from minivita");
        while (rs.next()) {
//            System.out.println(rs.getString("name") + "-" + rs.getString("email") + "-" + rs.getString("year"));
            Faculty tempFaculty = new Faculty(rs.getString("name"), rs.getString("email"), rs.getInt("year"));

            //add courses
            //add publication
            //add publisher
            //add fundings
            //add funding entity
            faculties.add(tempFaculty);
        }
    }

    public void addFaculty(Faculty f) throws SQLException {
        PreparedStatement stmt1 = con.prepareStatement("INSERT INTO `minivita`(`id`, `name`, `email`, `year`) VALUES (?,?,?,?)");
        stmt1.setInt(1, f.hashCode());
        stmt1.setString(2, f.getName());
        stmt1.setString(3, f.getEmail());
        stmt1.setInt(4, f.getYear());
        int r = stmt1.executeUpdate();
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
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

    public boolean removeFaculty(int findCode) {
        //db code here
        boolean flag = false;
        try {
            con.setAutoCommit(false);
            String stmtSql = "";
            //remove funding entity
            //remove fundings

            //add courses
            //add publication
            //add publisher
            //remove faculty hopefully cascade
            stmtSql = "DELETE FROM `minivita` WHERE id = " + findCode;
            stmt.addBatch(stmtSql);
            stmt.executeBatch();

            loadFaultiesFromDB();
            con.commit();
            System.out.println("trying commit");
            flag = true;
        } catch (SQLException e) {

        }

        return flag;
    }

    public boolean removeCourse(int facultyCode, int courseHash) throws Exception {
        //db code here
        Faculty fac = getFaculty(facultyCode);
        if (fac == null) {
            throw new Exception("Faculty not found");
        }
        for (Course c : fac.getCourses()) {
            if (c.hashCode() == courseHash) {
                fac.getCourses().remove(c);
                return true;
            }
        }
        return false;
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
