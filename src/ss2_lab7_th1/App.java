/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ss2_lab7_th1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Student;

/**
 *
 * @author Admin
 */
public class App {

    /**
     * @param args the command line arguments
     */
    static Connection conn = null;
    static DriverManager abc= null;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=qlsv";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "1";

    public static void main(String[] args) {
        try {
            Student st = new Student();
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            System.out.println("dong");
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
