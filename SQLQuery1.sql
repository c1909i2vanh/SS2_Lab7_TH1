CREATE DATABASE qlsv
GO
USE qlsv 
GO
CREATE TABLE tblsinhvien(
id INT NOT NULL PRIMARY KEY IDENTITY(1,1),

rollNumber varchar(50) NOT NULL unique,
name nvarchar(50) NOT NULL,
address varchar(200) DEFAULT NULL,
phone varchar(11) DEFAULT NULL,
gender bit NOT NULL
);
GO
INSERT INTO tblsinhvien (rollnumber, name, address, phone, gender) VALUES
('C1505001', 'Ma Sieu', 'Tay Luong', '19001265', 1),
('C1505002', 'Dieu Thuyen', 'To Chau', '19001001', 0),
('C1505003', 'Ma Sieu', 'Tay Luong', '19001265', 1);
select * from tblsinhvien