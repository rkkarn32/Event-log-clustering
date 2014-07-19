-- phpMyAdmin SQL Dump
-- version 2.11.2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 13, 2012 at 08:02 AM
-- Server version: 5.0.45
-- PHP Version: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `major_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `model`
--

CREATE TABLE `model` (
  `ID` int(11) NOT NULL auto_increment,
  `sentence` varchar(400) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `model`
--

INSERT INTO `model` (`ID`, `sentence`, `value`) VALUES
(1, ' ReDate   ReTime  bridge syslogd 1.4.1: restart.', 397358416),
(2, ' ReDate   ReTime  bridge syslog: syslogd startup succeeded', 402050696),
(3, ' ReDate   ReTime  bridge syslogd 1.4.1: restart.', 397358416),
(4, ' ReDate   ReTime  bridge syslog: syslogd startup succeeded', 402050696),
(5, ' ReDate   ReTime  bridge syslogd 1.4.1: restart.', 397358416),
(6, ' ReDate   ReTime  bridge syslog: syslogd startup succeeded', 402050696);
