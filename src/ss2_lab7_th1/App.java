/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ss2_lab7_th1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
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
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        App myApp = new App();
        Student st = new Student();
        st.setConnection();
        int choice = 0;
        try {
            do {
                myApp.displayMenu(scan);
                choice = getIntChoice(scan);
                switch (choice) {
                    case 1:
                        myApp.inputData(st);
                        break;
                    case 2:
                        myApp.displayData(st);
                        break;
                    case 3:
                        myApp.updateData(st);
                        break;
                    case 4:
                        myApp.deleteStudentById(st, scan);
                        break;
                    case 5:
                        myApp.searchStudentById(st, scan);
                        break;
                    case 6:
                        break;

                }
            } while (choice != 6);

        } finally {
            myApp.disconectDB(st);
            System.exit(0);
        }

    }

    private static int getIntChoice(Scanner scan) {
        int number = 0;
        do {
            try {
                number = Integer.parseInt(scan.nextLine());
                if (number > 0) {
                    break;
                } else {
                    System.err.println("Vui long nhap vao 1 so lon hon 0!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui long nhap vao 1 so nguyen");
            }
        } while (true);
        return number;
    }

    public void displayMenu(Scanner scan) {
        System.out.println("****************MENU****************");
        System.out.println("1. Nhập thông tin sinh viên");
        System.out.println("2. Danh sách sinh viên");
        System.out.println("3. Sửa sinh viên");
        System.out.println("4. Xóa sinh viên");
        System.out.println("5. Tìm sinh viên");
        System.out.println("6. Thoát");
        System.out.println("Vui lòng chọn từ 1 -> 6");
    }

    /**
     *
     ** Function display data of tblsinhvien table
     *
     * @param st
     */
    public void displayData(Student st) {
        try {
            st.displayData();
        } catch (SQLException ex) {
        }
    }

    /**
     * @throws java.sql.SQLException
     * @ Function inputData to table tblsinhvien
     * @param st
     */
    public void inputData(Student st) throws SQLException {
        st.inputData();
    }

    /**
     * * Function update Data
     *
     * @param st
     * @throws java.sql.SQLException
     */
    public void updateData(Student st)  throws SQLException {
    
            st.updateData();     
      
    }

    /**
     *
     ** Function delete data of student by ID
     *
     * @param scan
     * @param st
     */
    public void deleteStudentById(Student st, Scanner scan) {
        st.deleteData(scan);
    }

    /**
     *  * Function search Student by ID
     *
     * @param st
     * @param scan
     * @throws java.sql.SQLException
     */
    public void searchStudentById(Student st, Scanner scan) throws SQLException {
        st.searchStudentById(scan);
    }

    /**
     * * Function disconnect to server
     *
     * @param st
     */
    public void disconectDB(Student st) {
        st.disconectJDBC();
    }
}
