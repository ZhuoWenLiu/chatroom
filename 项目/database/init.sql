CREATE DATABASE `db_1_18` CHARSET utf8mb4;

USE `db_1_18`;

CREATE TABLE `db_1_18`.`users` (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(60) NOT NULL,
  `nickname` VARCHAR(60) NOT NULL,
  `password` CHAR(60) NOT NULL,
  `logout_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));

CREATE TABLE `db_1_18`.`messages` (
  `mid` INT NOT NULL AUTO_INCREMENT,
  `uid` INT NOT NULL,
  `content` TEXT NOT NULL,
  `published_at` DATETIME NOT NULL,
  PRIMARY KEY (`mid`));