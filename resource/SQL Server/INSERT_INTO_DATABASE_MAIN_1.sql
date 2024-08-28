USE QuanLyRapPhim
GO

INSERT INTO LoaiPhim VALUES
('LP01', N'Kinh dị'),
('LP02', N'Hành động'),
('LP03', N'Tình cảm'),
('LP04', N'Kiếm hiệp cổ trang'),
('LP05', N'Khoa học viễn tưởng'),
('LP06', N'Tài liệu'),
('LP07', N'Phim hài'),
('LP08', N'Hoạt hình'),
('LP09', N'Nhạc kịch')

-- -----------------------------------

INSERT INTO PhongChieu VALUES
('A', '2D', 100),
('B', '3D', 100),
('C', 'IMAX', 100)
GO


INSERT INTO LoaiVe VALUES
('LV01', N'Người Lớn', 75000),
('LV02', N'Trẻ Nhỏ', 50000),
('LV03', N'Sinh Viên', 50000),
('LV04', N'VIP', 85000)
GO

-- 7h-9h, 10-12h, 13h-15h, 16h-18h, 19h-21h
INSERT INTO SuatChieu VALUES
('7:00:00', '9:00:00'),
('10:00:00', '12:00:00'),
('13:00:00', '15:00:00'),
('16:00:00', '18:00:00'),
('19:00:00', '21:00:00')
GO

INSERT INTO NhanVien VALUES
('NV01', N'Nguyễn Thị Thùy An', '261546888', N'123 Hải Phòng, Đà Nẵng', 'thuyan993@gmail.com', '0907718993', '1998-06-15', 1),
('NV02', N'Trần Minh Tâm',  '352101245', N'K18/15 Tôn Đức Thắng, Đà Nẵng',  'minhtam17051998@gmail.com', '0369868741', '1998-05-17', 0),
('NV03', N'Lương Thị Hải',  '102025489', N'67 Ngô Quyền, Đà nẵng',  'luonghai1998@fpt.edu.vn', '+84778852110', '1998-10-21', 1),
('NV04', N'Phạm Thị Thanh Thảo',  '121245463', N'K635 Trần Cao Vân, Đà Nẵng',  'thanhthao30@gmail.com', '0908851220', '1998-03-30', 1),
('NV05', N'Nguyễn Minh Khải',  '185475200', N'11 Trường chinh, Cẩm lệ, Đà nẵng',  'minhkhainguyen1231@gmail.com', '0306620001', '1998-12-31', 0),
('NV06', N'Nguyễn Tiến Thành',  '285413203', N'K101/22 Ngô Quyền, Hòa Minh, TP.HCM',  'tienthanh1999@fpt.edu.vn', '+84809963000', '1999-10-22', 0),
('NV07', N'Phan Công Danh',  '221451263', N'122 Trần Quang Giáp, Cần Thơ',  'congdanh160699@yahoo.com', '+84978874551', '1999-06-16', 0),
('NV08', N'Phạm Quang Sáng',  '200012100', N'54 Nguyễn Quang Diệu, TP.Nha Trang',  'quangsang17@gmail.com', '0718845669', '1999-06-17', 0),
('NV09', N'Mai Tiến Luật',  '201200036', N'K54/180 Trần Liêu, TP.HCM',  'abc@gmail.com', '+84632323632', '1999-08-23', 0),
('NV10', N'Mai Quốc Hoan',  '145569999', N'Hẻm 142 Ngô Đình Thái, TP.Hà Nội',  'maiquochoan1997@gmail.com', '+84620014777', '1997-08-05', 0),
('NV11', N'Phùng Thị Thu Hiền',  '151548403', N'Hà Nội',  'superman@gmail.com', '09087745448', '1997-07-07', 1),
('NV12', N'Phan Thị Thục Trinh',  '163250032', N'54 Hồ Hoàn Kiếm, TP.Hà Nội',  'thuctrinh1171997@gmail.com', '0918563969', '1997-07-11', 1),
('NV13', N'Đoàn Triệu Vĩnh',  '124563331', N'K78/15 Lê Hoàng, TP.Đà Nẵng',  'trieuvinh@gmail.com', '0845565454', '1997-06-11', 0),
('NV14', N'Lê Nguyễn Lan Hương',  '201796161', N'Lê Độ, TP.Đà nẵng',  'huonglnlpd04867@fpt.edu.vn', '0512203693', '1992-11-01', 0),
('NV15', N'Trương Thị Thanh Danh',  '199986512', N'569 Hàm nghi, Hải châu, Đà nẵng',  'thanhdanh26@gmail.com', '+84787787889', '1992-09-26', 1),
('NV16', N'Trương Minh Hiếu',  '261544147', N'96 Trương định, Cần thơ',  'minhhieu3009@gmail.com', '+84969963660', '1993-09-30', 0),
('NV17', N'Nguyễn Ngọc Kha',  '261546755', N'541/56 Lý thái tông, Thanh khê, TP.Đà nẵng',  'nhomchung1999@gmail.com', '0386837394', '1999-06-22', 0)
GO


INSERT INTO TaiKhoan VALUES
('NV01', '0918257468', 1),
('NV02', '123456a', 0),
('NV03', 'hai123456', 0),
('NV04', 'thanhthao3003', 0),
('NV05', '15225478', 0),
('NV06', 'tienthanh', 0),
('NV07', '0907718993', 1),
('NV08', '123456S', 0),
('NV09', '1111000', 0),
('NV10', '79797979', 0),
('NV11', 'idontknow', 0),
('NV12', 'khongthich', 0),
('NV13', 'trieuvinh26', 0),
('NV14', '123456', 0),
('NV15', '96cantho', 0),
('NV16', '123456789', 1),
('NV17', '0907718993', 1)
GO




insert into Phim values
('P01',	'LP04',	N'Thần Điêu Đại Hiệp',		'2013-03-15',	N'Lưu Quốc Hào',			'ThanDieuDaiHiep.jpg',				'2019-03-15',	'2019-05-15',	120),
('P02',	'LP01', N'Tình Người Duyên Ma',		'2015-05-12',	'Banjong Pisanthanakun',	'TinhNguoiDuyenMa.jpg',				'2019-05-12',	'2019-07-12',	110),
('P03', 'LP02', N'Quá Nhanh Quá Nguy Hiểm', '2016-04-03',	'Justin Lin',				'QuaNhanhQuaNguyHiem.jpg',				'2019-02-20',	'2019-04-20',	115),
('P04', 'LP03', N'Pinochio',				'2019-02-12',	'Jo Soo Won',				'Pinochio.jpg',				'2019-04-10',	'2019-06-10',	150),
('P05', 'LP05', N'Cuộc Chiến Tương Lai',	'2020-01-19',	'Chris McKay',				'CuocChienTuongLai.jpg',				'2020-05-01',	'2020-07-01',	120),
('P06', 'LP05', N'Kẻ Vô Hình',				'2020-06-12',	'Leigh Whannell' ,			'KeVoHinh.jpg',				'2021-02-06',	'2020-04-06',	150),
('P07', 'LP05', N'Trí Lực Siêu Phàm',		'2020-09-19',	'Neil Burger',				'TriLucSieuPham.jpg',				'2021-05-09',	'2021-07-09',	200),
('P08', 'LP06', N'The Family Next Door',	'2018-03-19',	'Jenny Popplewell',			'TheFamilyNextDoor.jpg',				'2018-05-19',	'2018-07-19',	96),
('P09', 'LP06', N'The Game Changers',		'2019-01-20',	'Louie Psihoyos',			'TheGameChanges.jpg',				'2020-01-20',	'2020-03-20',	100),
('P10', 'LP01', N'Ấn Quỷ',					'2015-02-12',	'Evan Spiliotopoulos',		'AnQuy.jpg',				'2019-05-26',	'2019-07-26',	115),
('P11', 'LP01', N'Đảo Kinh Hoàng',			'2016-06-19',	'Jeff Wadlow',				'DaoKinhHoang.jpg',				'2019-10-12',	'2019-12-12',	160),
('P12', 'LP01', N'Ác Quỷ Trở về',			'2018-09-30',	'Gary Dauberman',			'AcQuyTroVe.jpg',				'2020-05-09',	'2020-07-09',	106),
('P13', 'LP02', N'Biệt Đội Bất Hảo',		'2019-01-16',	'Son Yong-ho',				'BietDoiBatHao.jpg',				'2020-06-07',	'2020-08-07',	95),
('P14', 'LP02', N'Hoa Mộc Lan',				'2020-09-12',	'Niki Caro',				'HoaMocLan.jpg',				'2020-08-13',	'2020-10-13',	122),
('P15', 'LP03', N'Từ Khi Có Anh',			'2020-12-20',	'Jenny Gage',				'TuKhiCoAnh.jpg',				'2021-04-30',	'2021-06-30',	106),
('P16', 'LP03', N'Tune in for love',		'2019-01-17',	'Jung Ji-woo',				'TuneInForLove.jpg',				'2019-09-11',	'2019-11-11',	119),
('P17', 'LP04', N'Hữu Phỉ',					'2020-12-09',	N'Ngô Cẩm Nguyên',			'HuuPhi.jpg',				'2021-01-29',	'2021-02-28',	152),
('P18', 'LP04', N'Hạo Y Hành',				'2021-02-04',	N'Hà Chú Bồi',				'HaoYHanh.jpg',				'2021-05-03',	'2021-07-03',	134),
('P19', 'LP07', N'Red Notice',				'2021-01-19',	'Rawson Marshall Thurber',	'RedNotice.jpg',				'2021-09-08',	'2021-11-08',	115),
('P20', 'LP07', N'Quái Đản',				'2021-08-12',	'Christopher Landon',		'QuaiDan.jpg',				'2021-10-18',	'2021-11-18',	102),
('P21', 'LP07', N'Trò Chơi Kì Ảo',			'2021-05-06',	'Jake Kasdan',				'TroChoiKiAo.jpg',				'2021-08-20',	'2021-10-20',	123),
('P22', 'LP08', N'Gia Đình Mitchells',		'2020-01-09',	'Michael Rianda',			'GiaDinhMitchells.jpg',				'2020-02-09',	'2020-04-09',	114),
('P23', 'LP08', N'Nữ Hoàng Băng Giá',		'2019-01-08',	'Chris Buck',				'NuHoangBangGia.jpg',				'2019-04-07',	'2019-06-07',	104),
('P24', 'LP08', N'Truy Tìm Phép Thuật',		'2021-02-20',	'Dan Scanlon',				'TruyTimPhepThuat.jpg',				'2021-08-16',	'2021-10-16',	92),
('P25', 'LP09', N'Cinderella',				'2021-09-24',	'Kenneth Branagh',			'Cinderella.jpg',				'2021-10-30',	'2021-11-30',	112),
('P26', 'LP09', N'Hậu Duệ',					'2020-06-07',	'Kenny Ortega',				'HauDue.jpg',				'2021-03-05',	'2021-05-05',	114),
('P27', 'LP05',	N'Harry Porter 7 Part 1',	'2010-07-28',	'Warner Bros',				'HarryPorter7Part 1.jpg',				'2011-07-10',	'2011-09-21',	165),
('P28', 'LP05',	N'Harry Porter 7 Part 2',	'2017-10-10',	'Warner Bros',				'HarryPorter7Part 2.jpg',				'2017-11-23',	'2018-03-17',	172),

('P29', 'LP03',	N'Call Me By Your Name',						'2018-01-18',	'Luca Guadagnino',			'CallMeByYourName.jpg',		'2021-12-05',	'2021-12-11',	181),
('P30', 'LP03',	N'Fragrance of the First Flower',				'2019-04-22',	'Angel Han Teng',			'FragranceOfTheFirstFlower.jpg','2021-12-05',	'2021-12-11',	190),
('P31', 'LP02',	N'Avenger: EndGame',							'2019-04-22',	'Joe Russo',				'EndGame.jpg',				'2021-12-05',	'2021-12-11',	181),
('P32', 'LP02',	N'ShangChi And The Legend Of Ten Rings',		'2019-08-16',	'Destin Daniel Cretton',	'ShangChi.jpg',				'2021-12-12',	'2021-12-12',	132),
('P33', 'LP02',	N'Eternals',									'2019-12-03',	N'Chloé Zhao',				'Eternals.jpg',				'2021-12-12',	'2021-12-18',	181),
('P34', 'LP02',	N'Spider-Man: No Way Home',						'2019-04-22',	'Jon Watts',				'SpiderMan-NoWayHome.jpg',	'2021-12-12',	'2021-12-18',	170),
('P35', 'LP03',	N'Happiest Season',								'2020-11-25',	'Clea DuVall',				'HappiestSeason.jpg',			'2021-12-12',	'2021-12-18',	190),
('P36', 'LP03',	N'Ammonite',									'2020-11-13',	'Francis Lee',				'Ammonite.jpg',				'2021-12-12',	'2021-12-18',	130)
go



Insert into ChiTietChieuPhim values 
---------------------------------------------------5/12/2021
(null, 'A', 'P29', 1, '2021-12-05'),
(null, 'B', 'P30', 1, '2021-12-05'),
(null, 'C', 'P31', 1, '2021-12-05'),
(null, 'A', 'P30', 2, '2021-12-05'),
(null, 'B', 'P29', 2, '2021-12-05'),
(null, 'C', 'P31', 2, '2021-12-05'),
(null, 'A', 'P31', 3, '2021-12-05'),
(null, 'B', 'P30', 3, '2021-12-05'),
(null, 'C', 'P29', 3, '2021-12-05'),
(null, 'A', 'P29', 4, '2021-12-05'),
(null, 'B', 'P31', 4, '2021-12-05'),
(null, 'C', 'P30', 4, '2021-12-05'),
(null, 'A', 'P29', 5, '2021-12-05'),
(null, 'B', 'P30', 5, '2021-12-05'),
(null, 'C', 'P31', 5, '2021-12-05'),

-- -------------------------------------------------6/12/2021
(null, 'A', 'P29', 1, '2021-12-06'),
(null, 'B', 'P30', 1, '2021-12-06'),
(null, 'C', 'P31', 1, '2021-12-06'),
(null, 'A', 'P30', 2, '2021-12-06'),
(null, 'B', 'P29', 2, '2021-12-06'),
(null, 'C', 'P31', 2, '2021-12-06'),
(null, 'A', 'P31', 3, '2021-12-06'),
(null, 'B', 'P30', 3, '2021-12-06'),
(null, 'C', 'P29', 3, '2021-12-06'),
(null, 'A', 'P29', 4, '2021-12-06'),
(null, 'B', 'P31', 4, '2021-12-06'),
(null, 'C', 'P30', 4, '2021-12-06'),
(null, 'A', 'P29', 5, '2021-12-06'),
(null, 'B', 'P30', 5, '2021-12-06'),
(null, 'C', 'P31', 5, '2021-12-06'),

---------------------------------------------------7/12/2021
(null, 'A', 'P29', 1, '2021-12-07'),
(null, 'B', 'P30', 1, '2021-12-07'),
(null, 'C', 'P31', 1, '2021-12-07'),
(null, 'A', 'P30', 2, '2021-12-07'),
(null, 'B', 'P29', 2, '2021-12-07'),
(null, 'C', 'P31', 2, '2021-12-07'),
(null, 'A', 'P31', 3, '2021-12-07'),
(null, 'B', 'P30', 3, '2021-12-07'),
(null, 'C', 'P29', 3, '2021-12-07'),
(null, 'A', 'P29', 4, '2021-12-07'),
(null, 'B', 'P31', 4, '2021-12-07'),
(null, 'C', 'P30', 4, '2021-12-07'),
(null, 'A', 'P29', 5, '2021-12-07'),
(null, 'B', 'P30', 5, '2021-12-07'),
(null, 'C', 'P31', 5, '2021-12-07'),



---------------------------------------------------8/12/2021
(null, 'A', 'P29', 1, '2021-12-08'),
(null, 'B', 'P30', 1, '2021-12-08'),
(null, 'C', 'P31', 1, '2021-12-08'),
(null, 'A', 'P30', 2, '2021-12-08'),
(null, 'B', 'P29', 2, '2021-12-08'),
(null, 'C', 'P31', 2, '2021-12-08'),
(null, 'A', 'P31', 3, '2021-12-08'),
(null, 'B', 'P30', 3, '2021-12-08'),
(null, 'C', 'P29', 3, '2021-12-08'),
(null, 'A', 'P29', 4, '2021-12-08'),
(null, 'B', 'P31', 4, '2021-12-08'),
(null, 'C', 'P30', 4, '2021-12-08'),
(null, 'A', 'P29', 5, '2021-12-08'),
(null, 'B', 'P30', 5, '2021-12-08'),
(null, 'C', 'P31', 5, '2021-12-08'),



---------------------------------------------------9/12/2021
(null, 'A', 'P29', 1, '2021-12-09'),
(null, 'B', 'P30', 1, '2021-12-09'),
(null, 'C', 'P31', 1, '2021-12-09'),
(null, 'A', 'P30', 2, '2021-12-09'),
(null, 'B', 'P29', 2, '2021-12-09'),
(null, 'C', 'P31', 2, '2021-12-09'),
(null, 'A', 'P31', 3, '2021-12-09'),
(null, 'B', 'P30', 3, '2021-12-09'),
(null, 'C', 'P29', 3, '2021-12-09'),
(null, 'A', 'P29', 4, '2021-12-09'),
(null, 'B', 'P31', 4, '2021-12-09'),
(null, 'C', 'P30', 4, '2021-12-09'),
(null, 'A', 'P29', 5, '2021-12-09'),
(null, 'B', 'P30', 5, '2021-12-09'),
(null, 'C', 'P31', 5, '2021-12-09'),



---------------------------------------------------10/12/2021
(null, 'A', 'P29', 1, '2021-12-10'),
(null, 'B', 'P32', 1, '2021-12-10'),
(null, 'C', 'P33', 1, '2021-12-10'),
(null, 'A', 'P33', 2, '2021-12-10'),
(null, 'B', 'P29', 2, '2021-12-10'),
(null, 'C', 'P32', 2, '2021-12-10'),
(null, 'A', 'P32', 3, '2021-12-10'),
(null, 'B', 'P29', 3, '2021-12-10'),
(null, 'C', 'P33', 3, '2021-12-10'),
(null, 'A', 'P29', 4, '2021-12-10'),
(null, 'B', 'P31', 4, '2021-12-10'),
(null, 'C', 'P32', 4, '2021-12-10'),
(null, 'A', 'P30', 5, '2021-12-10'),
(null, 'B', 'P33', 5, '2021-12-10'),
(null, 'C', 'P32', 5, '2021-12-10'),




---------------------------------------------------11/12/2021
(null, 'A', 'P29', 1, '2021-12-11'),
(null, 'B', 'P32', 1, '2021-12-11'),
(null, 'C', 'P33', 1, '2021-12-11'),
(null, 'A', 'P33', 2, '2021-12-11'),
(null, 'B', 'P29', 2, '2021-12-11'),
(null, 'C', 'P32', 2, '2021-12-11'),
(null, 'A', 'P32', 3, '2021-12-11'),
(null, 'B', 'P29', 3, '2021-12-11'),
(null, 'C', 'P33', 3, '2021-12-11'),
(null, 'A', 'P29', 4, '2021-12-11'),
(null, 'B', 'P31', 4, '2021-12-11'),
(null, 'C', 'P32', 4, '2021-12-11'),
(null, 'A', 'P30', 5, '2021-12-11'),
(null, 'B', 'P33', 5, '2021-12-11'),
(null, 'C', 'P32', 5, '2021-12-11'),



---------------------------------------------------12/12/2021
(null, 'A', 'P34', 1, '2021-12-12'),
(null, 'B', 'P32', 1, '2021-12-12'),
(null, 'C', 'P33', 1, '2021-12-12'),
(null, 'A', 'P32', 2, '2021-12-12'),
(null, 'B', 'P34', 2, '2021-12-12'),
(null, 'C', 'P33', 2, '2021-12-12'),
(null, 'A', 'P33', 3, '2021-12-12'),
(null, 'B', 'P34', 3, '2021-12-12'),
(null, 'C', 'P32', 3, '2021-12-12'),
(null, 'A', 'P33', 4, '2021-12-12'),
(null, 'B', 'P32', 4, '2021-12-12'),
(null, 'C', 'P34', 4, '2021-12-12'),
(null, 'A', 'P34', 5, '2021-12-12'),
(null, 'B', 'P32', 5, '2021-12-12'),
(null, 'C', 'P33', 5, '2021-12-12'),




---------------------------------------------------13/12/2021
(null, 'A', 'P34', 1, '2021-12-13'),
(null, 'B', 'P35', 1, '2021-12-13'),
(null, 'C', 'P36', 1, '2021-12-13'),
(null, 'A', 'P36', 2, '2021-12-13'),
(null, 'B', 'P35', 2, '2021-12-13'),
(null, 'C', 'P34', 2, '2021-12-13'),
(null, 'A', 'P35', 3, '2021-12-13'),
(null, 'B', 'P34', 3, '2021-12-13'),
(null, 'C', 'P36', 3, '2021-12-13'),
(null, 'A', 'P36', 4, '2021-12-13'),
(null, 'B', 'P35', 4, '2021-12-13'),
(null, 'C', 'P34', 4, '2021-12-13'),
(null, 'A', 'P36', 5, '2021-12-13'),
(null, 'B', 'P34', 5, '2021-12-13'),
(null, 'C', 'P33', 5, '2021-12-13'),



---------------------------------------------------14/12/2021
(null, 'A', 'P34', 1, '2021-12-14'),
(null, 'B', 'P35', 1, '2021-12-14'),
(null, 'C', 'P36', 1, '2021-12-14'),
(null, 'A', 'P36', 2, '2021-12-14'),
(null, 'B', 'P35', 2, '2021-12-14'),
(null, 'C', 'P34', 2, '2021-12-14'),
(null, 'A', 'P35', 3, '2021-12-14'),
(null, 'B', 'P34', 3, '2021-12-14'),
(null, 'C', 'P36', 3, '2021-12-14'),
(null, 'A', 'P36', 4, '2021-12-14'),
(null, 'B', 'P35', 4, '2021-12-14'),
(null, 'C', 'P34', 4, '2021-12-14'),
(null, 'A', 'P36', 5, '2021-12-14'),
(null, 'B', 'P34', 5, '2021-12-14'),
(null, 'C', 'P33', 5, '2021-12-14'),

---------------------------------------------------15/12/2021
(null, 'A', 'P34', 1, '2021-12-15'),
(null, 'B', 'P35', 1, '2021-12-15'),
(null, 'C', 'P36', 1, '2021-12-15'),
(null, 'A', 'P36', 2, '2021-12-15'),
(null, 'B', 'P35', 2, '2021-12-15'),
(null, 'C', 'P34', 2, '2021-12-15'),
(null, 'A', 'P35', 3, '2021-12-15'),
(null, 'B', 'P34', 3, '2021-12-15'),
(null, 'C', 'P36', 3, '2021-12-15'),
(null, 'A', 'P36', 4, '2021-12-15'),
(null, 'B', 'P35', 4, '2021-12-15'),
(null, 'C', 'P34', 4, '2021-12-15'),
(null, 'A', 'P36', 5, '2021-12-15'),
(null, 'B', 'P34', 5, '2021-12-15'),
(null, 'C', 'P33', 5, '2021-12-15'),

---------------------------------------------------16/12/2021
(null, 'A', 'P34', 1, '2021-12-16'),
(null, 'B', 'P35', 1, '2021-12-16'),
(null, 'C', 'P36', 1, '2021-12-16'),
(null, 'A', 'P36', 2, '2021-12-16'),
(null, 'B', 'P35', 2, '2021-12-16'),
(null, 'C', 'P34', 2, '2021-12-16'),
(null, 'A', 'P35', 3, '2021-12-16'),
(null, 'B', 'P34', 3, '2021-12-16'),
(null, 'C', 'P36', 3, '2021-12-16'),
(null, 'A', 'P36', 4, '2021-12-16'),
(null, 'B', 'P35', 4, '2021-12-16'),
(null, 'C', 'P34', 4, '2021-12-16'),
(null, 'A', 'P36', 5, '2021-12-16'),
(null, 'B', 'P34', 5, '2021-12-16'),
(null, 'C', 'P33', 5, '2021-12-16'),

---------------------------------------------------17/12/2021
(null, 'A', 'P34', 1, '2021-12-17'),
(null, 'B', 'P35', 1, '2021-12-17'),
(null, 'C', 'P36', 1, '2021-12-17'),
(null, 'A', 'P36', 2, '2021-12-17'),
(null, 'B', 'P35', 2, '2021-12-17'),
(null, 'C', 'P34', 2, '2021-12-17'),
(null, 'A', 'P35', 3, '2021-12-17'),
(null, 'B', 'P34', 3, '2021-12-17'),
(null, 'C', 'P36', 3, '2021-12-17'),
(null, 'A', 'P36', 4, '2021-12-17'),
(null, 'B', 'P35', 4, '2021-12-17'),
(null, 'C', 'P34', 4, '2021-12-17'),
(null, 'A', 'P36', 5, '2021-12-17'),
(null, 'B', 'P34', 5, '2021-12-17'),
(null, 'C', 'P33', 5, '2021-12-17')
go


-------------------------------------------------------------------------------- XEM LICH CHIEU PHIM
create proc sp_LichPhim
	@tenPhim nvarchar(100)
as
begin
	select Phim.TenPhim, PhongChieuID, SuatChieuID, NgayChieu from ChiTietChieuPhim
	inner join Phim on Phim.ID = ChiTietChieuPhim.PhimID  
	where PhimID = (select ID from Phim where TenPhim = @tenPhim and ( CAST(getdate() AS date) <= NgayHetHan and NgayChieu >= CAST(getdate() AS date)) ) 
	order by NgayChieu
end;



exec sp_LichPhim @tenPhim = N'Call Me By Your Name';
go
--------------------------------------------------------------------------------------
drop proc sp_LichPhim;
go



---------------------------------------------------------- TINH DOANH THU

create function F_tongtien()
returnS table
as
return (
	
	select count(Ve.id) as 'VeID', PhimID, DonGia, (count(Ve.id) * DonGia) as 'Giatien', TenPhim from ChiTietChieuPhim
	inner join Ve on Ve.id = ChiTietChieuPhim.VeID
	inner join LoaiVe on LoaiVe.ID = Ve.LoaiVeID
	inner join Phim on Phim.ID = ChiTietChieuPhim.PhimID
	group by PhimID,  DonGia, TenPhim

)
GO

DROP FUNCTION F_tongtien
GO

select top 10 PhimID, SUM(Giatien) as N'Tổng Tiền', TenPhim from F_tongtien() group by PhimID,TenPhim order by SUM(Giatien) desc
go




-- -----------------------  HIEN THI HINH ANH, TEN PHIM VA THOI LUONG 4 NUT SUAT CHIEU CUA PHIM ------------------
CREATE PROC SP_fillMovie (@date date)
as
begin
	SELECT TenPhim, ThoiLuong, NgayChieu
	FROM ChiTietChieuPhim
	INNER JOIN Phim ON Phim.ID = ChiTietChieuPhim.PhimID
	INNER JOIN SuatChieu ON SuatChieu.ID = ChiTietChieuPhim.SuatChieuID
	WHERE NgayChieu = @date
	GROUP BY TenPhim, ThoiLuong, NgayChieu
end
go

DROP PROC SP_fillMovie
GO

EXEC SP_fillMovie @date = '2021-12-11'


SELECT TenPhim, ThoiLuong, NgayChieu
	FROM ChiTietChieuPhim
	INNER JOIN Phim ON Phim.ID = ChiTietChieuPhim.PhimID
	INNER JOIN SuatChieu ON SuatChieu.ID = ChiTietChieuPhim.SuatChieuID
	WHERE NgayChieu = '2021-12-15'
	GROUP BY TenPhim, ThoiLuong, NgayChieu


-- --------------------------------- 

