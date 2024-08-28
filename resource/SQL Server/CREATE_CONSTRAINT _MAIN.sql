CREATE DATABASE QuanLyRapPhim
GO

USE QuanLyRapPhim
GO

CREATE TABLE ChiTietChoNgoi(
	MaChoNgoi int not null,
	PhongChieuID char(1) not null,
	[Status] int not null 
)
GO




CREATE TABLE KhachHang(
	ID char(10) primary key not null,
	HoTen nvarchar(100) null,
	SDT char(13) null UNIQUE,
)
 GO

CREATE TABLE NhanVien(
	ID char(10) primary key not null,
	HoTen nvarchar(100) not null,
	CMND varchar(9) not null UNIQUE,
	DiaChi nvarchar(100) not null,
	Email varchar (100) not null,
	SDT char(13) not null UNIQUE,
	NgaySinh date not null,
	GioiTinh bit not null,
)
 GO

 CREATE TABLE TaiKhoan (
	NhanVienID char(10) not null,
	MatKhau char(20) not null,
	VaiTro bit not null
 )
 GO

CREATE TABLE PhongChieu(
	ID char(1) primary key not null,
	LoaiManHinh nvarchar(100) not null,
	TongSoChoNgoi int DEFAULT 100
)
 GO

 CREATE TABLE Phim(
	ID char(10) primary key not null,
	LoaiPHimID char(10) not null,
	TenPhim nvarchar(100) not null,
	NSX date not null,
	DaoDien nvarchar(100) not null,
	HinhAnh nvarchar(100) not null,
	NgayCongChieu date not null,
	NgayHetHan date not null,
	ThoiLuong int not null
)
 GO

 CREATE TABLE LoaiPhim(
	ID char(10) primary key not null,
	LoaiPhim nvarchar(100) not null
)
 GO



CREATE TABLE Ve(
	ID int primary key IDENTITY(1,1),
	KhachHangID char(10) not null,
	LoaiVeID char(10) not null,
	NhanVienID char(10) not null,
	NgayBanVe datetime default getdate()
)
GO

  -- ------------------------------------------


CREATE TABLE LoaiVe(
	ID char(10) primary key not null,
	TenLV nvarchar(20) not null,
	DonGia int
)
 GO


CREATE TABLE ChiTietChieuPhim(
	ID int primary key IDENTITY(1,1),
	VeID int null,
	PhongChieuID char(1) not null,
	PhimID char(10) not null,
	SuatChieuID int not null,
	NgayChieu date not null
)
 GO

 CREATE TABLE SuatChieu (
	ID int IDENTITY (1,1) primary key check (ID<= 5),
	ThoiGianTu varchar(10) not null,
	ThoiGianDen varchar(10) not null
 )
 GO

 ALTER TABLE Ve
 ADD CONSTRAINT FK_Ve_LV FOREIGN KEY (LoaiVeID) REFERENCES LoaiVe(ID) ON DELETE CASCADE ON UPDATE CASCADE;
  ALTER TABLE Ve
 ADD CONSTRAINT FK_Ve_NV FOREIGN KEY (NhanVienID) REFERENCES NhanVien(ID);
  ALTER TABLE Ve
 ADD CONSTRAINT FK_Ve_KH FOREIGN KEY (KhachHangID) REFERENCES KhachHang(ID);


  ALTER TABLE Phim
 ADD CONSTRAINT FK_Phim_LP FOREIGN KEY (LoaiPhimID) REFERENCES LoaiPhim(ID) ON DELETE CASCADE ON UPDATE CASCADE;


  ALTER TABLE ChiTietChieuPhim
 ADD CONSTRAINT FK_Chitiet_Phong FOREIGN KEY (PhongChieuID) REFERENCES PhongChieu(ID);
  ALTER TABLE ChiTietChieuPhim
 ADD CONSTRAINT FK_Chitiet_Phim FOREIGN KEY (PhimID) REFERENCES Phim(ID);
   ALTER TABLE ChiTietChieuPhim
 ADD CONSTRAINT FK_Chitiet_SC FOREIGN KEY (SuatChieuID) REFERENCES SuatChieu(ID);
 ALTER TABLE ChiTietChieuPhim
 ADD CONSTRAINT FK_Chitiet_V FOREIGN KEY (VeID) REFERENCES Ve(ID);

 ALTER TABLE TaiKhoan
ADD CONSTRAINT FK_MaNV_NV_TK FOREIGN KEY (NhanVienID) REFERENCES NhanVien(ID);


-- ALTER TABLE ChiTietChoNgoi
-- ADD CONSTRAINT FK_ChiTiet_Ve FOREIGN KEY (VeID) REFERENCES Ve(ID);
 ALTER TABLE ChiTietChoNgoi
ADD CONSTRAINT FK_MaPhong_CTCN_PC FOREIGN KEY (PhongChieuID) REFERENCES PhongChieu(ID);


