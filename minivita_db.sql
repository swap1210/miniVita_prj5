-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 29, 2023 at 07:28 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


--
-- Database: `minivita_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `code` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `creditHours` int(11) NOT NULL,
  `department` varchar(30) NOT NULL,
  `facultyId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `credentials`
--

CREATE TABLE `credentials` (
  `user_id` int(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `type` varchar(10) NOT NULL DEFAULT 'STAFF'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `credentials`
--

INSERT INTO `credentials` (`user_id`, `username`, `password`, `type`) VALUES
(1, 's', 'p', 'STAFF'),
(2, 'u2', 'p', 'STAFF');

-- --------------------------------------------------------

--
-- Table structure for table `Funding`
--

CREATE TABLE `Funding` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `amount` int(11) NOT NULL,
  `faculty_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `funding_entity`
--

CREATE TABLE `funding_entity` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `establishedIn` int(11) NOT NULL,
  `funding_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `minivita`
--

CREATE TABLE `minivita` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `year` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `publication`
--

CREATE TABLE `publication` (
  `id` int(11) NOT NULL,
  `title` varchar(30) NOT NULL,
  `content` varchar(30) NOT NULL,
  `year` int(11) NOT NULL,
  `faculty_Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `Publisher`
--

CREATE TABLE `Publisher` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `pub_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `course_minivita` (`facultyId`);

--
-- Indexes for table `credentials`
--
ALTER TABLE `credentials`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username_unique` (`username`);

--
-- Indexes for table `Funding`
--
ALTER TABLE `Funding`
  ADD PRIMARY KEY (`id`),
  ADD KEY `funding_faculty` (`faculty_Id`);

--
-- Indexes for table `funding_entity`
--
ALTER TABLE `funding_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `funding_fe` (`funding_Id`);

--
-- Indexes for table `minivita`
--
ALTER TABLE `minivita`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `publication`
--
ALTER TABLE `publication`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pub_fac` (`faculty_Id`);

--
-- Indexes for table `Publisher`
--
ALTER TABLE `Publisher`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pub_publr` (`pub_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `credentials`
--
ALTER TABLE `credentials`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `course_minivita` FOREIGN KEY (`facultyId`) REFERENCES `minivita` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `Funding`
--
ALTER TABLE `Funding`
  ADD CONSTRAINT `funding_faculty` FOREIGN KEY (`faculty_Id`) REFERENCES `minivita` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `funding_entity`
--
ALTER TABLE `funding_entity`
  ADD CONSTRAINT `funding_fe` FOREIGN KEY (`funding_Id`) REFERENCES `Funding` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `publication`
--
ALTER TABLE `publication`
  ADD CONSTRAINT `pub_fac` FOREIGN KEY (`faculty_Id`) REFERENCES `minivita` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Publisher`
--
ALTER TABLE `Publisher`
  ADD CONSTRAINT `pub_publr` FOREIGN KEY (`pub_id`) REFERENCES `publication` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;
