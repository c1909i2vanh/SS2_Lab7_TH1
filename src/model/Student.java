/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author Admin
 */
public class Student {

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

    public Student() {

    }

    public Student(int id, String rollNumber, String name, String address, String phoneNumber, int gender) {
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

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
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

    /**
     * * Connection to DB
     *
     * @return instance conn of Connection interface
     */
    private static Connection getConnectionStudent() {

        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Ket noi thanh cong");

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    /**
     *
     */
    public void setConnection() {
        getConnectionStudent();
    }

    /**
     * * display all data in tblsinhvien table
     *
     * @throws SQLException
     */
    public void displayData() throws SQLException {
        try {
            if (conn != null) {
                System.out.println("ok");
            }
            String sql = "SELECT * FROM tblsinhvien";
            pstmt = conn.prepareStatement(sql);
            //rowset = pstmt.execute();
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cr = factory.createCachedRowSet();
            cr.setCommand(sql);
            cr.execute(conn);
            while (cr.next()) {
                if (cr.isFirst()) {
                    System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", "STT", "MA SV", "Ten", "Dia chi", "So dien thoai", "Gioi tinh");
                    System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", cr.getRow(), cr.getString("rollNumber"), cr.getString("name"), cr.getString("address"), cr.getString("phone"), (cr.getBoolean("gender")) ? "Nam" : "Nu");
                } else {
                    System.out.printf("%-5s %-10s %-20s %-20s %-20s %-20s\n", cr.getRow(), cr.getString("rollNumber"), cr.getString("name"), cr.getString("address"), cr.getString("phone"), (cr.getBoolean("gender")) ? "Nam" : "Nu");
                }
            }
        } catch (Exception e) {
        }
    }

    public void validateData() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Nhap ma sinh vien: ");
        do {
            String regexRoll = "^C[0-9]{1,9}$";
            Pattern patternRoll = Pattern.compile(regexRoll);
            String strRoll = scan.nextLine().trim();
            if (strRoll == null) {
                System.err.println("Ma sinh vien khong duoc de trong! ");
            } else {
                Matcher matchRoll = patternRoll.matcher(strRoll);
                if (matchRoll.matches()) {
                    this.setRollNumber(strRoll);
                    break;
                } else {
                    System.err.println("Ma sinh vien co kieu la Cxxx voi x la cac so tu 0-9! Vui long nhap lai!");
                }
            }
        } while (true);

        System.out.println("Nhap ten sinh vien: ");
        do {
            String strName = scan.nextLine().trim();
            if (!strName.isEmpty() || strName != null) {
                this.setName(strName);
                break;
            } else {
                System.err.println("Ten sinh vien khong duoc de trong!");
            }
        } while (true);

        System.out.println("Nhap dia chi: ");
        this.setAddress(scan.nextLine());
        System.out.println("Nhap phone: ");
        do {
            String strPhone = scan.nextLine();
            String regexPhone = "^0[0-9]{9,10}$";
            Pattern p = Pattern.compile(regexPhone);
            Matcher matchPhone = p.matcher(strPhone);
            if (matchPhone.matches()) {
                this.setPhoneNumber(strPhone);
                break;
            } else {
                System.err.println(" So dien thoai khong dung dinh dang! Vui long nhap lai!");
                System.err.println("Vi du: 0123456789");
            }

        } while (true);

        System.out.println("Nhap gioi tinh: 1 Nam | 0 Nu ");
        do {
            try {
                int genderNumber = Integer.parseInt(scan.nextLine());
                if (genderNumber == 1 || genderNumber == 0) {
                    this.setGender(genderNumber);
                    break;
                } else {
                    System.err.println("Vui long chon 1 hoac 0! Xin thu lai");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui long nhap vao 1 so nguyen! Vui long thu lai");
            }
        } while (true);
    }

    /**
     * * Input multiple Student to tblsinhvien table
     *
     * @throws java.sql.SQLException
     */
    public void inputData() {

        try {
            Scanner scan = new Scanner(System.in);

            //Khoa tu dong commit du lieu len server
            boolean isStop = false;
            String sql = "INSERT INTO tblsinhvien(rollNumber,name,address,phone,gender) VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            Student st = new Student();
            do {

                st.validateData();
                ps.setString(1, st.getRollNumber());
                ps.setString(2, st.getName());
                ps.setString(3, st.getAddress());
                ps.setString(4, st.getPhoneNumber());
                ps.setInt(5, st.getGender());
                ps.addBatch();

                System.out.println("Tiep tuc nhap tiep (1: CO| 2: Khong)");
                int choice = 0;
                do {
                    try {
                        choice = Integer.parseInt(scan.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Vui long nhap vao 1 so nguyen!");
                    }
                } while (true);
                if (choice != 1) {
                    isStop = true;
                }
            } while (!isStop);
            System.out.println(sql);
            //Thuc thi thay doi
            ps.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inputDataToUpdate() {

        Scanner scan = new Scanner(System.in);

        System.out.println("Nhap ma sinh vien: ");

        this.setRollNumber(scan.nextLine());

        System.out.println("Nhap ten sinh vien: ");
        do {
            String strName = scan.nextLine().trim();
            if (strName != null) {
                this.setName(strName);
                break;
            } else {
                System.err.println("Ten sinh vien khong duoc de trong!");
            }
        } while (true);

        System.out.println("Nhap dia chi: ");
        this.setAddress(scan.nextLine());
        System.out.println("Nhap phone: ");
        do {
            String strPhone = scan.nextLine();
            String regexPhone = "^0[0-9]{9,10}$";
            Pattern p = Pattern.compile(regexPhone);
            Matcher matchPhone = p.matcher(strPhone);
            if (matchPhone.matches()) {
                this.setPhoneNumber(strPhone);
                break;
            } else {
                System.err.println(" So dien thoai khong dung dinh dang! Vui long nhap lai!");
                System.err.println("Vi du: 0123456789");
            }

        } while (true);

        System.out.println("Nhap gioi tinh: 1 Nam | 0 Nu ");
        do {
            try {
                int genderNumber = Integer.parseInt(scan.nextLine());
                if (genderNumber == 1 | genderNumber == 0) {
                    this.setGender(genderNumber);
                    break;
                } else {
                    System.err.println("Vui long chon 1 hoac 0! Xin thu lai");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui long nhap vao 1 so nguyen! Vui long thu lai");
            }
        } while (true);
    }

    /**
     * * Function update data by rollNumber
     *
     * @throws java.sql.SQLException
     */
    public void updateData() throws SQLException {

        Student st = new Student();
        st.validateData();
        System.out.println("abc");
        String sql = "UPDATE tblsinhvien set name =?,address =?, phone =?,gender =? where rollNumber =?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, st.getName());
        ps.setString(2, st.getAddress());
        ps.setString(3, st.getPhoneNumber());

        ps.setInt(4, st.getGender());
        ps.setString(5, st.getRollNumber());
        int i = ps.executeUpdate();
        System.out.println(i);

        if (i > 0) {
            System.out.println("Sua thanh cong " + i + " ban ghi");
        } else {
            System.out.println("Sua that bai! Vui long kiem tra lai du lieu");
        }

    }

    public void deleteData(Scanner scan) {
        try {

            System.out.println("Nhap ma sinh vien muon xoa: ");
            String rollDelNumber = scan.nextLine();
            String sql = "DELETE FROM tblsinhvien WHERE rollNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rollDelNumber);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Xoa thanh cong " + row + " hang");
            } else {
                System.err.println("Xoa that bai, vui long kiem tra lai du lieu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void searchStudentById(Scanner scan) throws SQLException {
        System.out.println("Nhap ma sinh vien muon tim: ");
        String roll = scan.nextLine();
        String sql = "{CALL sp_getStudent(?)}";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, roll);
        ResultSet rs = ps.executeQuery();
       boolean checkResultSet = false;
      
        while (rs.next()) {
            checkResultSet= true;
            System.out.printf("%-10s %-20s %-20s %-20s %-20s\n", "MA SV", "Ten", "Dia chi", "So dien thoai", "Gioi tinh");
            do {                
                 System.out.printf("%-10s %-20s %-20s %-20s %-20s\n", rs.getString("rollNumber"), rs.getString("name"), rs.getString("address"), rs.getString("phone"), (rs.getBoolean("gender")) ? "Nam" : "Nu");
            } while (rs.next());        
        }
        if(!checkResultSet){
            System.out.println("Khong tim thay sinh vien co ma la "+roll);
        }

    }

    /**
     * * Disconnect Connection JDBC
     */
    public void disconectJDBC() {
        try {
            conn.close();
            System.out.println("Da dong DB! ");
        } catch (Exception e) {
        }

    }
}
