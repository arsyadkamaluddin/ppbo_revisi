-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 02 Jul 2024 pada 01.11
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `disyfa`
--

DELIMITER $$
--
-- Prosedur
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `BookOut` (IN `input_id` CHAR(4))   BEGIN
    DECLARE user_id CHAR(16);
    DECLARE book_nomor INT;
    DECLARE dateIn DATE;
    DECLARE dateOut DATE;
    DECLARE total INT;

    -- Mendapatkan nama berdasarkan id dari tabel user
    SELECT username INTO user_id FROM datauser WHERE userId = input_id;

    -- Memeriksa apakah ada data booking yang sesuai
    IF (SELECT COUNT(*) FROM databooking WHERE userId = user_id) > 0 THEN
        -- Mendapatkan nomor kamar dari tabel booking
        SELECT nomorKamar,harga,checkIn,checkOut INTO book_nomor,total,dateIn,dateOut FROM databooking WHERE userId = user_id LIMIT 1;

        -- Memasukkan data ke tabel riwayat
        INSERT INTO riwayat VALUES (book_nomor, total,input_id,dateIn,dateOut);

        -- Menghapus data dari tabel booking
        DELETE FROM databooking WHERE userId = user_id AND nomorKamar = book_nomor LIMIT 1;
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_available_rooms` (IN `chIn` DATE, IN `chOut` DATE, IN `bed` INT, IN `acCo` INT(5))   BEGIN
    SELECT k.nomorKamar,k.ranjang,k.ac,k.harga
    FROM dataKamar k
    LEFT JOIN dataBooking b
    ON k.nomorKamar = b.nomorKamar
    WHERE (b.nomorKamar IS NULL OR 
           ((chIn < b.checkIn AND chOut<b.checkIn) OR (chIn > b.checkOut AND chOut>b.checkOut))
    )
    AND k.ranjang>=bed
    AND k.ac>=acCo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `update_stat` (IN `tanggal` DATE)   BEGIN
    UPDATE dataKamar
    SET status = 'Available';
    
    UPDATE dataKamar k
    SET k.status = 'Booked'
    WHERE k.nomorkamar IN (
        SELECT nomorKamar FROM databooking
    );
    UPDATE dataKamar k 
    SET k.status = 'Used'
    WHERE k.nomorKamar IN(
        SELECT nomorKamar FROM databooking b
        WHERE tanggal BETWEEN b.checkIn AND b.checkOut
    );
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `databooking`
--

CREATE TABLE `databooking` (
  `nomorKamar` char(3) NOT NULL,
  `userId` char(16) NOT NULL,
  `checkIn` date NOT NULL,
  `checkOut` date NOT NULL,
  `harga` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `databooking`
--

INSERT INTO `databooking` (`nomorKamar`, `userId`, `checkIn`, `checkOut`, `harga`) VALUES
('104', '3201123456789012', '2024-06-25', '2024-06-27', 1200000),
('108', '3202123456789013', '2024-06-26', '2024-06-29', 2100000),
('108', '3372081840283301', '2024-06-30', '2024-07-09', 6300000),
('208', '1234567890123456', '2024-06-27', '2024-06-28', 700000),
('209', '3203123456789014', '2024-06-27', '2024-06-30', 1200000),
('306', '3204123456789015', '2024-06-28', '2024-07-01', 1350000);

--
-- Trigger `databooking`
--
DELIMITER $$
CREATE TRIGGER `hitung_total` BEFORE INSERT ON `databooking` FOR EACH ROW BEGIN
    DECLARE harga_kamar INT;
    DECLARE jumlah_hari INT;
    
    -- Mengambil harga kamar dari tabel kamar
    SELECT harga INTO harga_kamar FROM datakamar WHERE nomorKamar = NEW.nomorkamar;
    
    -- Menghitung jumlah hari
    SET jumlah_hari = DATEDIFF(NEW.checkOut, NEW.checkIn);
    
    -- Menghitung harga total
    SET NEW.harga = jumlah_hari * harga_kamar;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `datakamar`
--

CREATE TABLE `datakamar` (
  `id` int(11) NOT NULL,
  `nomorKamar` char(3) NOT NULL,
  `ranjang` int(11) NOT NULL,
  `ac` int(11) NOT NULL,
  `status` varchar(15) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `datakamar`
--

INSERT INTO `datakamar` (`id`, `nomorKamar`, `ranjang`, `ac`, `status`, `harga`) VALUES
(98, '103', 1, 1, 'Available', 250000),
(99, '104', 2, 1, 'Booked', 600000),
(100, '105', 1, 0, 'Available', 300000),
(101, '106', 2, 0, 'Available', 450000),
(102, '107', 1, 1, 'Available', 550000),
(103, '108', 2, 1, 'Used', 700000),
(104, '109', 1, 0, 'Available', 400000),
(106, '201', 1, 1, 'Available', 500000),
(107, '202', 2, 0, 'Available', 750000),
(108, '203', 1, 1, 'Available', 250000),
(109, '204', 2, 1, 'Available', 600000),
(110, '205', 1, 0, 'Available', 300000),
(111, '206', 2, 0, 'Available', 450000),
(112, '207', 1, 1, 'Available', 550000),
(113, '208', 2, 1, 'Booked', 700000),
(114, '209', 1, 0, 'Used', 400000),
(116, '301', 1, 1, 'Available', 500000),
(117, '302', 2, 0, 'Available', 750000),
(118, '303', 1, 1, 'Available', 250000),
(119, '304', 2, 1, 'Available', 600000),
(120, '305', 1, 0, 'Available', 300000),
(121, '306', 2, 0, 'Used', 450000),
(122, '307', 1, 1, 'Available', 550000),
(126, '401', 1, 1, 'Available', 500000),
(205, '308', 2, 1, 'Available', 500000),
(207, '309', 2, 1, 'Available', 5000000),
(209, '102', 1, 0, 'Available', 300000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `datauser`
--

CREATE TABLE `datauser` (
  `userId` char(6) NOT NULL,
  `name` varchar(50) NOT NULL,
  `username` char(16) NOT NULL,
  `password` varchar(15) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `datauser`
--

INSERT INTO `datauser` (`userId`, `name`, `username`, `password`, `role`) VALUES
('A001', '', 'super', 'admin', 'admin'),
('C001', 'John Die', '1234567890123456', '6281234567890', 'cust'),
('C002', 'Jack Lee', '2234567890123456', '6281234567800', 'cust'),
('C003', 'Jane Smith', '2345678901234567', '6281234567891', 'cust'),
('C004', 'Bob Brown', '4567890123456789', '6281234567893', 'cust'),
('C005', 'Charlie Davis', '5678901234567890', '6281234567894', 'cust'),
('C006', 'Frank Green', '7890123456789012', '6281234567896', 'cust'),
('C007', 'Kamaluddin Arsyad', '3372071849030301', '089636044268', 'cust'),
('C008', 'John Doe', '3201123456789012', '0812345678901', 'cust'),
('C009', 'Jane Smith', '3202123456789013', '0823456789012', 'cust'),
('C010', 'Alice Johnson', '3203123456789014', '0834567890123', 'cust'),
('C011', 'Bob Brown', '3204123456789015', '0845678901234', 'cust'),
('C012', 'Kamaluddin Arysad Fadllillah', '3371859304618839', '864393647893', 'cust'),
('U001', '', 'admin', 'hotel', 'user');

--
-- Trigger `datauser`
--
DELIMITER $$
CREATE TRIGGER `generate_customer_id` BEFORE INSERT ON `datauser` FOR EACH ROW BEGIN
    DECLARE jumlah_data INT;
    DECLARE id_baru CHAR(4);
    
    -- Menghitung jumlah data dalam tabel customer
    SELECT COUNT(*) - 1 INTO jumlah_data FROM datauser;
    
    -- Membentuk ID baru
    SET id_baru = CONCAT('C', LPAD(jumlah_data, 3, '0'));
    
    -- Mengatur nilai ID baru pada row yang akan diinsert
    SET NEW.userId = id_baru;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `riwayat`
--

CREATE TABLE `riwayat` (
  `nomorKamar` char(3) NOT NULL,
  `harga` int(11) NOT NULL,
  `userId` char(4) NOT NULL,
  `checkIn` date NOT NULL,
  `checkOut` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `riwayat`
--

INSERT INTO `riwayat` (`nomorKamar`, `harga`, `userId`, `checkIn`, `checkOut`) VALUES
('101', 500000, 'C001', '2024-06-01', '2024-06-03'),
('104', 600000, 'C012', '2024-06-29', '2024-06-30'),
('105', 3300000, 'C002', '2024-06-11', '2024-06-22'),
('107', 550000, 'C007', '2024-06-06', '2024-06-11'),
('108', 700000, 'C005', '2024-06-03', '2024-06-07'),
('201', 500000, 'C004', '2024-06-10', '2024-06-15'),
('203', 250000, 'C003', '2024-06-02', '2024-06-05'),
('205', 300000, 'C006', '2024-06-04', '2024-06-08');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `databooking`
--
ALTER TABLE `databooking`
  ADD PRIMARY KEY (`nomorKamar`,`userId`),
  ADD KEY `nomorKamar` (`nomorKamar`),
  ADD KEY `nik` (`userId`);

--
-- Indeks untuk tabel `datakamar`
--
ALTER TABLE `datakamar`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nomorKamar_2` (`nomorKamar`),
  ADD KEY `nomorKamar` (`nomorKamar`);

--
-- Indeks untuk tabel `datauser`
--
ALTER TABLE `datauser`
  ADD PRIMARY KEY (`userId`);

--
-- Indeks untuk tabel `riwayat`
--
ALTER TABLE `riwayat`
  ADD PRIMARY KEY (`nomorKamar`,`checkIn`),
  ADD KEY `userId` (`userId`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `datakamar`
--
ALTER TABLE `datakamar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=211;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `databooking`
--
ALTER TABLE `databooking`
  ADD CONSTRAINT `databooking_ibfk_1` FOREIGN KEY (`nomorKamar`) REFERENCES `datakamar` (`nomorKamar`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `riwayat`
--
ALTER TABLE `riwayat`
  ADD CONSTRAINT `riwayat_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `datauser` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
