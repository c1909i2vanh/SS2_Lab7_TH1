/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ss2_lab7_th1;

import java.sql.SQLException;
import java.util.Scanner;
import model.StudentCacheRowset;

/**
 *1
 * 
 * @author GIANG
 */
public class DemoCachedRowset {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DemoCachedRowset main = new DemoCachedRowset();
        StudentCacheRowset st = new StudentCacheRowset();
        Scanner scan = new Scanner(System.in);

        int choice = 0;
        try {
            String sql = "SELECT * FROM tblsinhvien";
            main.connectDB(st);
            main.setCacheRowSet(st, sql);
            do {
                main.displayMenu();
                System.out.println("Nhap lua chon cua ban");
                do {
                    try {
                        choice = Integer.parseInt(scan.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Xin nhap vao 1 so nguyen!");
                    }
                } while (true);

                switch (choice) {
                    case 1:
                        st.getAllRowSet();
                        break;
                    case 2:
                        main.inputData(st);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            } while (choice != 5);

           

        } finally {
            st.disconectJDBC();
        }
    }

    public void displayMenu() {
        System.out.println("===== MENU =====");
        System.out.println("1. Danh sách sinh viên");
        System.out.println("2. Thêm sinh viên");
        System.out.println("3. Sửa sinh viên");
        System.out.println("4. Xóa sinh viên");
        System.out.println("5. Thoát");
        System.out.println("Vui lòng chọn từ 1 -> 5");
    }

    public void connectDB(StudentCacheRowset st) throws SQLException, ClassNotFoundException {
        st.setConn();
    }

    public void setCacheRowSet(StudentCacheRowset st, String sql) throws SQLException {
        st.setCachedRowSet(sql);
    }

    public void inputData(StudentCacheRowset st) throws SQLException {
        st.insertData();
    }
}
