/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.spi.SyncProviderException;
import java.util.Enumeration;

/**
 *
 * @author GIANG
 */
public class StudentCacheRowset {

    private int id;
    private String rollNumber;
    private String name;
    private String address;
    private String phoneNumber;
    private int gender;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=qlsv";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "1";
    static Connection conn = null;
    PreparedStatement pstmt = null;
    CachedRowSet rowset = null;

    public StudentCacheRowset() {
    }

    public StudentCacheRowset(int id, String rollNumber, String name, String address, String phoneNumber, int gender) {
        this.id = id;
        this.rollNumber = rollNumber;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public static Connection getConn() {
        return conn;
    }

    /**
     *
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void setConn() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        System.out.println("Ket noi thanh cong");

    }

    public void disconectJDBC() throws SQLException {
        conn.close();
        rowset.close();
    }

    public void setCachedRowSet(String sql) throws SQLException {

        RowSetFactory factory = RowSetProvider.newFactory();
        rowset = factory.createCachedRowSet();
        rowset.setCommand(sql);
        rowset.execute(conn);

    }

    public void getAllRowSet() throws SQLException {
        rowset.beforeFirst();
        while (rowset.next()) {
            if (rowset.isFirst()) {
                System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", "STT", "MA SV", "Ten", "Dia chi", "So dien thoai", "Gioi tinh");
                System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", rowset.getRow(), rowset.getString("rollNumber"), rowset.getString("name"), rowset.getString("address"), rowset.getString("phone"), (rowset.getBoolean("gender")) ? "Nam" : "Nu");
            } else {
                System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", rowset.getRow(), rowset.getString("rollNumber"), rowset.getString("name"), rowset.getString("address"), rowset.getString("phone"), (rowset.getBoolean("gender")) ? "Nam" : "Nu");
            }
        }
    }

    public void insertData() throws SQLException {
        StudentCacheRowset st = new StudentCacheRowset();
        System.out.println("Nhập thông tin sinh viên:");
        Scanner nhap = new Scanner(System.in);
        System.out.println("Mã sinh viên: ");
        st.setRollNumber(nhap.nextLine());
        System.out.println("Họ và Tên: ");
        st.setName(nhap.nextLine());
        System.out.println("Địa chỉ: ");
        st.setAddress(nhap.nextLine());
        System.out.println("SĐT: ");
        st.setPhoneNumber(nhap.nextLine());
        System.out.println("Giới tính(Nam = 1 | Nữ = 0): ");
        st.setGender(nhap.nextInt());

        conn.setAutoCommit(false); // Mặc định là true. 

        rowset.moveToInsertRow();
        rowset.updateNull("id");
        rowset.updateString("rollNumber", st.getRollNumber());
        rowset.updateString("name", st.getName());
        rowset.updateString("address", st.getAddress());
        rowset.updateString("phone", st.getPhoneNumber());
        rowset.updateInt("gender", st.getGender());

        rowset.insertRow();
        rowset.moveToCurrentRow();
        try {
            rowset.acceptChanges();

        } catch (Exception e) {
        }

        rowset.beforeFirst();
        while (rowset.next()) {
            if (rowset.isFirst()) {
                System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", "STT", "MA SV", "Ten", "Dia chi", "So dien thoai", "Gioi tinh");
                System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", rowset.getRow(), rowset.getString("rollNumber"), rowset.getString("name"), rowset.getString("address"), rowset.getString("phone"), (rowset.getBoolean("gender")) ? "Nam" : "Nu");
            } else {
                System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", rowset.getRow(), rowset.getString("rollNumber"), rowset.getString("name"), rowset.getString("address"), rowset.getString("phone"), (rowset.getBoolean("gender")) ? "Nam" : "Nu");
            }
        }

    }

}
