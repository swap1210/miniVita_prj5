/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;


public class User implements Serializable{  
String username;
String password;

    public User() {
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    @Override
    public int hashCode() {
        return this.username.hashCode()*this.password.hashCode();
    }
        
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        User objToCompare = (User) o;
        return (this.username.equals(objToCompare.username) 
          && this.password.equals(objToCompare.password));
    }
    
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}  