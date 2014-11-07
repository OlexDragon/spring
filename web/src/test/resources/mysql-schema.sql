SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema jk
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `jk` ;
CREATE SCHEMA IF NOT EXISTS `jk` DEFAULT CHARACTER SET utf8 ;
USE `jk` ;

-- -----------------------------------------------------
-- Table `jk`.`logins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`logins` ;

CREATE TABLE IF NOT EXISTS `jk`.`logins` (
  `loginID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(65) NOT NULL,
  `password` VARCHAR(65) NOT NULL,
  `permissions` INT(10) UNSIGNED NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_accessed` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`loginID`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`emails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`emails` ;

CREATE TABLE IF NOT EXISTS `jk`.`emails` (
  `emailID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `logins_loginID` INT(10) UNSIGNED NOT NULL,
  `email` VARCHAR(145) NOT NULL,
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` TIMESTAMP NULL,
  `status` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`emailID`, `logins_loginID`),
  INDEX `fk_id_users_idx` (`logins_loginID` ASC),
  CONSTRAINT `fk_id_users`
    FOREIGN KEY (`logins_loginID`)
    REFERENCES `jk`.`logins` (`loginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`titles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`titles` ;

CREATE TABLE IF NOT EXISTS `jk`.`titles` (
  `title_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title_name` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`title_id`),
  UNIQUE INDEX `title_name_UNIQUE` (`title_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`users` ;

CREATE TABLE IF NOT EXISTS `jk`.`users` (
  `logins_loginID` INT(10) UNSIGNED NOT NULL,
  `title_id` INT(10) UNSIGNED NULL,
  `first_name` VARCHAR(164) NOT NULL,
  `last_name` VARCHAR(164) NOT NULL,
  `birthday` DATE NOT NULL,
  `gender` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`logins_loginID`),
  INDEX `fk_users_titles1_idx` (`title_id` ASC),
  CONSTRAINT `fk_users_logins`
    FOREIGN KEY (`logins_loginID`)
    REFERENCES `jk`.`logins` (`loginID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_titles1`
    FOREIGN KEY (`title_id`)
    REFERENCES `jk`.`titles` (`title_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`countries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`countries` ;

CREATE TABLE IF NOT EXISTS `jk`.`countries` (
  `country_code` CHAR(3) NOT NULL,
  `country_name` CHAR(52) NOT NULL,
  `region_name` VARCHAR(145) NULL,
  PRIMARY KEY (`country_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`regions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`regions` ;

CREATE TABLE IF NOT EXISTS `jk`.`regions` (
  `region_code` VARCHAR(2) NOT NULL,
  `country_code` VARCHAR(3) NOT NULL,
  `region_name` VARCHAR(145) NOT NULL,
  PRIMARY KEY (`region_code`, `country_code`),
  INDEX `fk_regions_countries1_idx` (`country_code` ASC),
  CONSTRAINT `fk_regions_countries1`
    FOREIGN KEY (`country_code`)
    REFERENCES `jk`.`countries` (`country_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`addresses` ;

CREATE TABLE IF NOT EXISTS `jk`.`addresses` (
  `addsress_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `logins_loginID` INT UNSIGNED NOT NULL,
  `addsress` VARCHAR(245) NOT NULL,
  `city` VARCHAR(145) NOT NULL,
  `postal_code` VARCHAR(10) NOT NULL,
  `regions_code` VARCHAR(2) NULL,
  `country_code` VARCHAR(3) NOT NULL,
  `type` INT NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` INT NULL,
  `status_update_date` TIMESTAMP NULL,
  PRIMARY KEY (`addsress_id`),
  INDEX `fk_addsresses_countries1_idx` (`country_code` ASC),
  INDEX `fk_addresses_regions1_idx` (`regions_code` ASC, `country_code` ASC),
  INDEX `fk_addsresses_users1_idx` (`logins_loginID` ASC),
  CONSTRAINT `fk_addsresses_users1`
    FOREIGN KEY (`logins_loginID`)
    REFERENCES `jk`.`users` (`logins_loginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_addsresses_countries1`
    FOREIGN KEY (`country_code`)
    REFERENCES `jk`.`countries` (`country_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_addresses_regions1`
    FOREIGN KEY (`regions_code` , `country_code`)
    REFERENCES `jk`.`regions` (`region_code` , `country_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`social`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`social` ;

CREATE TABLE IF NOT EXISTS `jk`.`social` (
  `user_key` VARCHAR(45) NOT NULL,
  `username` VARCHAR(145) NULL,
  `first_name` VARCHAR(145) NULL,
  `last_name` VARCHAR(145) NULL,
  `image_url` VARCHAR(245) NULL,
  `profile_url` VARCHAR(245) NULL,
  `email` VARCHAR(145) NULL,
  PRIMARY KEY (`user_key`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`professional_skills`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`professional_skills` ;

CREATE TABLE IF NOT EXISTS `jk`.`professional_skills` (
  `logins_loginID` INT UNSIGNED NOT NULL,
  `professional_skill` VARCHAR(145) NOT NULL,
  PRIMARY KEY (`logins_loginID`, `professional_skill`),
  CONSTRAINT `fk_professional_skills_users1`
    FOREIGN KEY (`logins_loginID`)
    REFERENCES `jk`.`users` (`logins_loginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `jk`.`workplaces`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `jk`.`workplaces` ;

CREATE TABLE IF NOT EXISTS `jk`.`workplaces` (
  `logins_loginID` INT UNSIGNED NOT NULL,
  `workplace` VARCHAR(145) NOT NULL,
  PRIMARY KEY (`logins_loginID`, `workplace`),
  CONSTRAINT `fk_workplaces_users1`
    FOREIGN KEY (`logins_loginID`)
    REFERENCES `jk`.`users` (`logins_loginID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `jk`.`logins`
-- -----------------------------------------------------
START TRANSACTION;
USE `jk`;
INSERT INTO `jk`.`logins` (`loginID`, `username`, `password`, `permissions`, `created_date`, `last_accessed`) VALUES (1, 'AlexDragon', '$2a$10$Ngs18wBAxxMoBgHW2dikouwCgRExj6ENjOiVk0CjWODYMzJZoriq6', NULL, NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `jk`.`titles`
-- -----------------------------------------------------
START TRANSACTION;
USE `jk`;
INSERT INTO `jk`.`titles` (`title_id`, `title_name`) VALUES (3, 'Miss.');
INSERT INTO `jk`.`titles` (`title_id`, `title_name`) VALUES (1, 'Mr.');
INSERT INTO `jk`.`titles` (`title_id`, `title_name`) VALUES (2, 'Mrs.');
INSERT INTO `jk`.`titles` (`title_id`, `title_name`) VALUES (4, 'Ms.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `jk`.`countries`
-- -----------------------------------------------------
START TRANSACTION;
USE `jk`;
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ABW', 'Aruba', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('AFG', 'Afghanistan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('AGO', 'Angola', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('AIA', 'Anguilla', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ALB', 'Albania', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('AND', 'Andorra', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ANT', 'Netherlands Antilles', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ARE', 'United Arab Emirates', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ARG', 'Argentina', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ARM', 'Armenia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ASM', 'American Samoa', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ATA', 'Antarctica', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ATF', 'French Southern territories', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ATG', 'Antigua and Barbuda', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('AUS', 'Australia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('AUT', 'Austria', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('AZE', 'Azerbaijan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BDI', 'Burundi', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BEL', 'Belgium', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BEN', 'Benin', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BFA', 'Burkina Faso', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BGD', 'Bangladesh', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BGR', 'Bulgaria', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BHR', 'Bahrain', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BHS', 'Bahamas', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BIH', 'Bosnia and Herzegovina', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BLR', 'Belarus', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BLZ', 'Belize', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BMU', 'Bermuda', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BOL', 'Bolivia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BRA', 'Brazil', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BRB', 'Barbados', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BRN', 'Brunei', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BTN', 'Bhutan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BVT', 'Bouvet Island', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('BWA', 'Botswana', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CAF', 'Central African Republic', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CAN', 'Canada', 'Province/Territory');
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CCK', 'Cocos (Keeling) Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CHE', 'Switzerland', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CHL', 'Chile', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CHN', 'China', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CIV', 'CÃ´te dÂ’Ivoire', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CMR', 'Cameroon', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('COD', 'Congo, The Democratic Republic of the', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('COG', 'Congo', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('COK', 'Cook Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('COL', 'Colombia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('COM', 'Comoros', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CPV', 'Cape Verde', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CRI', 'Costa Rica', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CUB', 'Cuba', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CXR', 'Christmas Island', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CYM', 'Cayman Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CYP', 'Cyprus', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('CZE', 'Czech Republic', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('DEU', 'Germany', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('DJI', 'Djibouti', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('DMA', 'Dominica', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('DNK', 'Denmark', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('DOM', 'Dominican Republic', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('DZA', 'Algeria', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ECU', 'Ecuador', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('EGY', 'Egypt', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ERI', 'Eritrea', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ESH', 'Western Sahara', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ESP', 'Spain', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('EST', 'Estonia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ETH', 'Ethiopia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('FIN', 'Finland', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('FJI', 'Fiji Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('FLK', 'Falkland Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('FRA', 'France', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('FRO', 'Faroe Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('FSM', 'Micronesia, Federated States of', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GAB', 'Gabon', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GBR', 'United Kingdom', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GEO', 'Georgia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GHA', 'Ghana', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GIB', 'Gibraltar', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GIN', 'Guinea', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GLP', 'Guadeloupe', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GMB', 'Gambia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GNB', 'Guinea-Bissau', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GNQ', 'Equatorial Guinea', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GRC', 'Greece', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GRD', 'Grenada', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GRL', 'Greenland', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GTM', 'Guatemala', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GUF', 'French Guiana', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GUM', 'Guam', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('GUY', 'Guyana', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('HKG', 'Hong Kong', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('HMD', 'Heard Island and McDonald Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('HND', 'Honduras', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('HRV', 'Croatia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('HTI', 'Haiti', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('HUN', 'Hungary', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('IDN', 'Indonesia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('IND', 'India', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('IOT', 'British Indian Ocean Territory', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('IRL', 'Ireland', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('IRN', 'Iran', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('IRQ', 'Iraq', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ISL', 'Iceland', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ISR', 'Israel', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ITA', 'Italy', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('JAM', 'Jamaica', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('JOR', 'Jordan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('JPN', 'Japan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KAZ', 'Kazakstan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KEN', 'Kenya', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KGZ', 'Kyrgyzstan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KHM', 'Cambodia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KIR', 'Kiribati', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KNA', 'Saint Kitts and Nevis', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KOR', 'South Korea', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('KWT', 'Kuwait', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LAO', 'Laos', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LBN', 'Lebanon', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LBR', 'Liberia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LBY', 'Libyan Arab Jamahiriya', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LCA', 'Saint Lucia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LIE', 'Liechtenstein', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LKA', 'Sri Lanka', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LSO', 'Lesotho', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LTU', 'Lithuania', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LUX', 'Luxembourg', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('LVA', 'Latvia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MAC', 'Macao', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MAR', 'Morocco', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MCO', 'Monaco', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MDA', 'Moldova', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MDG', 'Madagascar', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MDV', 'Maldives', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MEX', 'Mexico', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MHL', 'Marshall Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MKD', 'Macedonia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MLI', 'Mali', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MLT', 'Malta', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MMR', 'Myanmar', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MNG', 'Mongolia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MNP', 'Northern Mariana Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MOZ', 'Mozambique', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MRT', 'Mauritania', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MSR', 'Montserrat', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MTQ', 'Martinique', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MUS', 'Mauritius', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MWI', 'Malawi', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MYS', 'Malaysia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('MYT', 'Mayotte', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NAM', 'Namibia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NCL', 'New Caledonia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NER', 'Niger', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NFK', 'Norfolk Island', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NGA', 'Nigeria', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NIC', 'Nicaragua', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NIU', 'Niue', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NLD', 'Netherlands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NOR', 'Norway', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NPL', 'Nepal', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NRU', 'Nauru', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('NZL', 'New Zealand', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('OMN', 'Oman', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PAK', 'Pakistan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PAN', 'Panama', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PCN', 'Pitcairn', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PER', 'Peru', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PHL', 'Philippines', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PLW', 'Palau', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PNG', 'Papua New Guinea', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('POL', 'Poland', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PRI', 'Puerto Rico', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PRK', 'North Korea', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PRT', 'Portugal', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PRY', 'Paraguay', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PSE', 'Palestine', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('PYF', 'French Polynesia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('QAT', 'Qatar', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('REU', 'RÃ©union', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ROM', 'Romania', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('RUS', 'Russian Federation', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('RWA', 'Rwanda', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SAU', 'Saudi Arabia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SDN', 'Sudan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SEN', 'Senegal', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SGP', 'Singapore', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SGS', 'South Georgia and the South Sandwich Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SHN', 'Saint Helena', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SJM', 'Svalbard and Jan Mayen', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SLB', 'Solomon Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SLE', 'Sierra Leone', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SLV', 'El Salvador', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SMR', 'San Marino', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SOM', 'Somalia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SPM', 'Saint Pierre and Miquelon', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('STP', 'Sao Tome and Principe', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SUR', 'Suriname', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SVK', 'Slovakia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SVN', 'Slovenia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SWE', 'Sweden', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SWZ', 'Swaziland', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SYC', 'Seychelles', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('SYR', 'Syria', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TCA', 'Turks and Caicos Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TCD', 'Chad', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TGO', 'Togo', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('THA', 'Thailand', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TJK', 'Tajikistan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TKL', 'Tokelau', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TKM', 'Turkmenistan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TMP', 'East Timor', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TON', 'Tonga', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TTO', 'Trinidad and Tobago', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TUN', 'Tunisia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TUR', 'Turkey', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TUV', 'Tuvalu', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TWN', 'Taiwan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('TZA', 'Tanzania', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('UGA', 'Uganda', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('UKR', 'Ukraine', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('UMI', 'United States Minor Outlying Islands', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('URY', 'Uruguay', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('USA', 'United States', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('UZB', 'Uzbekistan', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('VAT', 'Holy See (Vatican City State)', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('VCT', 'Saint Vincent and the Grenadines', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('VEN', 'Venezuela', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('VGB', 'Virgin Islands, British', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('VIR', 'Virgin Islands, U.S.', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('VNM', 'Vietnam', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('VUT', 'Vanuatu', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('WLF', 'Wallis and Futuna', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('WSM', 'Samoa', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('YEM', 'Yemen', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('YUG', 'Yugoslavia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ZAF', 'South Africa', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ZMB', 'Zambia', NULL);
INSERT INTO `jk`.`countries` (`country_code`, `country_name`, `region_name`) VALUES ('ZWE', 'Zimbabwe', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `jk`.`regions`
-- -----------------------------------------------------
START TRANSACTION;
USE `jk`;
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('ON', 'CAN', 'Ontario');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('QC', 'CAN', 'Quebec');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NS', 'CAN', 'Nova Scotia');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NB', 'CAN', 'New Brunswick');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MB', 'CAN', 'Manitoba');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('BC', 'CAN', 'British Columbia');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('PE', 'CAN', 'Prince Edward Island');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('SK', 'CAN', 'Saskatchewan');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('AB', 'CAN', 'Alberta');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NL', 'CAN', 'Newfoundland and Labrador');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NT', 'CAN', 'Northwest Territories');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('YT', 'CAN', 'Yukon');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NU', 'CAN', 'Nunavut');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('AK', 'USA', 'Alaska');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('AL', 'USA', 'Alabama');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('AR', 'USA', 'Arkansas');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('AZ', 'USA', 'Arizona');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('CA', 'USA', 'California');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('CO', 'USA', 'Colorado');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('CT', 'USA', 'Connecticut');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('DC', 'USA', 'District of Columbia');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('DE', 'USA', 'Delaware');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('FL', 'USA', 'Florida');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('GA', 'USA', 'Georgia');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('HI', 'USA', 'Hawaii');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('IA', 'USA', 'Iowa');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('ID', 'USA', 'Idaho');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('IL', 'USA', 'Illinois');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('IN', 'USA', 'Indiana');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('KS', 'USA', 'Kansas');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('KY', 'USA', 'Kentucky');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('LA', 'USA', 'Louisiana');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MA', 'USA', 'Massachusetts');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MD', 'USA', 'Maryland');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('ME', 'USA', 'Maine');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MI', 'USA', 'Michigan');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MN', 'USA', 'Minnesota');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MO', 'USA', 'Missouri');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MS', 'USA', 'Mississippi');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('MT', 'USA', 'Montana');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NC', 'USA', 'North Carolina');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('ND', 'USA', 'North Dakota');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NE', 'USA', 'Nebraska');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NH', 'USA', 'New Hampshire');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NJ', 'USA', 'New Jersey');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NM', 'USA', 'New Mexico');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NV', 'USA', 'Nevada');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('NY', 'USA', 'New York');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('OH', 'USA', 'Ohio');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('OK', 'USA', 'Oklahoma');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('OR', 'USA', 'Oregon');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('PA', 'USA', 'Pennsylvania');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('RI', 'USA', 'Rhode Island');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('SC', 'USA', 'South Carolina');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('SD', 'USA', 'South Dakota');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('TN', 'USA', 'Tennessee');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('TX', 'USA', 'Texas');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('UT', 'USA', 'Utah');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('VA', 'USA', 'Virginia');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('VT', 'USA', 'Vermont');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('WA', 'USA', 'Washington');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('WI', 'USA', 'Wisconsin');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('WV', 'USA', 'West Virginia');
INSERT INTO `jk`.`regions` (`region_code`, `country_code`, `region_name`) VALUES ('WY', 'USA', 'Wyoming');

COMMIT;

