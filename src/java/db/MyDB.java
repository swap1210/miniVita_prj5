/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;


/**
 *
 * @author swapn
 */
public class MyDB {
    public static Connection getConnection(){
        Connection con = null;
        try{  
            Class.forName("com.mysql.jdbc.Driver");     
            con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/minivita_db","root","");  
//            PreparedStatement stmt = con.prepareStatement("insert into user(name,email) values(?,?)");  
//            stmt.setString(1, this.getUserName());  
//            stmt.setString(2, this.getEmail());  
//            result = stmt.executeUpdate();  
                return con;
            }catch(ClassNotFoundException | SQLException e){  
            System.out.println(e);  
          }finally{
            return con;
        }
    }
}
