-- MySQL dump 10.13  Distrib 5.5.62, for Linux (x86_64)
--
-- Host: localhost    Database: TRASHBIN
-- ------------------------------------------------------
-- Server version	5.5.62

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `FEEDBACK`
--

DROP TABLE IF EXISTS `FEEDBACK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FEEDBACK` (
  `Feedbackid` int(11) NOT NULL AUTO_INCREMENT,
  `RequestorId` varchar(255) NOT NULL,
  `Rating` int(11) NOT NULL,
  `Comments` varchar(255) DEFAULT NULL,
  `Feedbackdate` date NOT NULL,
  `Comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Feedbackid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FEEDBACK`
--

LOCK TABLES `FEEDBACK` WRITE;
/*!40000 ALTER TABLE `FEEDBACK` DISABLE KEYS */;
/*!40000 ALTER TABLE `FEEDBACK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GARBAGE_COLLECTION`
--

DROP TABLE IF EXISTS `GARBAGE_COLLECTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GARBAGE_COLLECTION` (
  `Collectionid` int(11) NOT NULL AUTO_INCREMENT,
  `Helperid` varchar(255) NOT NULL,
  `Requestid` int(11) NOT NULL,
  `Comment` text,
  `Garbagecollectiondate` date NOT NULL,
  `Timeofday` int(11) NOT NULL,
  `GCCollecteddate` date DEFAULT NULL,
  `GCCollectedTimeOfDay` int(11) DEFAULT NULL,
  PRIMARY KEY (`Collectionid`),
  KEY `Requestid` (`Requestid`),
  KEY `Helperid` (`Helperid`),
  CONSTRAINT `GARBAGE_COLLECTION_ibfk_1` FOREIGN KEY (`Requestid`) REFERENCES `TRASHCALL_REQUESTS` (`Requestid`),
  CONSTRAINT `GARBAGE_COLLECTION_ibfk_2` FOREIGN KEY (`Helperid`) REFERENCES `HELPERS` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GARBAGE_COLLECTION`
--

LOCK TABLES `GARBAGE_COLLECTION` WRITE;
/*!40000 ALTER TABLE `GARBAGE_COLLECTION` DISABLE KEYS */;
INSERT INTO `GARBAGE_COLLECTION` VALUES (1,'missandei',4,NULL,'2019-09-07',1,NULL,NULL);
/*!40000 ALTER TABLE `GARBAGE_COLLECTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HELPERS`
--

DROP TABLE IF EXISTS `HELPERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HELPERS` (
  `Username` varchar(255) NOT NULL,
  `FirstName` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Mobile` varchar(255) NOT NULL,
  `Pwd` varchar(255) NOT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HELPERS`
--

LOCK TABLES `HELPERS` WRITE;
/*!40000 ALTER TABLE `HELPERS` DISABLE KEYS */;
INSERT INTO `HELPERS` VALUES ('hodor','Mr','Hodor','hodor@got.com','1234512345','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6'),('jaqenh','jaqen','H\'ghar','jaqen.h\'ghar@got.com','1234123412','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6'),('jorahm','Jorah','Mormont','jorah.mormont@got.com','1234561234','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6'),('missandei','Miss','Missandei','missandei@got.com','1231231234','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6');
/*!40000 ALTER TABLE `HELPERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TRASHCALL_REQUESTS`
--

DROP TABLE IF EXISTS `TRASHCALL_REQUESTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TRASHCALL_REQUESTS` (
  `Requestid` int(11) NOT NULL AUTO_INCREMENT,
  `RequestorId` varchar(255) DEFAULT NULL,
  `Requeststatus` int(11) NOT NULL DEFAULT '1',
  `Comment` text,
  `Requestdate` date NOT NULL,
  `Garbagecollectiondate` date NOT NULL,
  `Timeofday` int(11) NOT NULL,
  PRIMARY KEY (`Requestid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TRASHCALL_REQUESTS`
--

LOCK TABLES `TRASHCALL_REQUESTS` WRITE;
/*!40000 ALTER TABLE `TRASHCALL_REQUESTS` DISABLE KEYS */;
INSERT INTO `TRASHCALL_REQUESTS` VALUES (4,'johns',1,'collect garbage from Castle black','2019-07-21','2019-08-03',1),(5,'tyrionl',1,'collect garbage from Casterly Rock','2019-07-21','2019-09-07',2),(6,'johns',1,'collect garbage from Castle black','2019-07-24','2019-07-03',1),(7,'johns',1,'collect garbage from Castle black','2019-07-24','2019-07-04',1);
/*!40000 ALTER TABLE `TRASHCALL_REQUESTS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `UserName` varchar(255) NOT NULL,
  `FirstName` varchar(255) NOT NULL,
  `LastName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Mobile` varchar(255) NOT NULL,
  `Pwd` varchar(255) NOT NULL,
  `ApartmentNo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UserName`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
INSERT INTO `USERS` VALUES ('admin','Trashbin','Admin','trashbin.admin@trashbin.com','0000000000','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','13 B'),('benjens','Benjen','Stark','benjen.stark@got.com','6666666666','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','Castle Black'),('cerseil','Cersei','Lannister','cersei.lannister@got.com','3333333333','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','Casterly Rock'),('daeneryst','Daenerys','Targaryen','daenerys.targaryen@got.com','4444444444','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','Meereen'),('johns','John','Snow','john.snow@got.com','1111111111','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','Castle Black'),('ramseyb','Ramsey','Bolton','ramsey.bolton@got.com','5555555555','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','The Dreadfort'),('robert','Robert','Baratheon','robert.baratheon@got.com','5555555555','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','Storm\'s End'),('tyrionl','Tyrion','Lannister','tyrion.lannister@got.com','2222222222','ad321accaad28a291a0ca3c37ff5f94bd3f26cf6','Casterly Rock');
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-30 15:42:36
