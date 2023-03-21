-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `company_valuation_by_capital_cost` DEFAULT CHARACTER SET utf8 ;
USE `company_valuation_by_capital_cost` ;

-- -----------------------------------------------------
-- Table `company_valuation_by_capital_cost`.`segments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_valuation_by_capital_cost`.`segments` (
  `segment_id` VARCHAR(36) NOT NULL,
  `segment_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`segment_id`),
  UNIQUE INDEX `category_name_UNIQUE` (`segment_name` ASC) VISIBLE,
  UNIQUE INDEX `category_id_UNIQUE` (`segment_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `company_valuation_by_capital_cost`.`companymarkstatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_valuation_by_capital_cost`.`companymarkstatus` (
  `status_id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`status_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `status_id_UNIQUE` (`status_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `company_valuation_by_capital_cost`.`companies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_valuation_by_capital_cost`.`companies` (
  `company_id` VARCHAR(36) NOT NULL,
  `segment_id` VARCHAR(36) NOT NULL,
  `mark_status_id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `succor` DOUBLE NOT NULL COMMENT 'выручка',
  `income_tax` DOUBLE NOT NULL COMMENT 'расходы по налогу на прибыль',
  `financial_income` DOUBLE NOT NULL,
  `depriciation` DOUBLE NOT NULL,
  `ebitda` DOUBLE NOT NULL COMMENT 'мультипликатор',
  PRIMARY KEY (`company_id`),
  INDEX `category_id_idx` (`segment_id` ASC) VISIBLE,
  INDEX `mark_status_id_idx` (`mark_status_id` ASC) VISIBLE,
  CONSTRAINT `segment_id`
    FOREIGN KEY (`segment_id`)
    REFERENCES `company_valuation_by_capital_cost`.`segments` (`segment_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `mark_status_id`
    FOREIGN KEY (`mark_status_id`)
    REFERENCES `company_valuation_by_capital_cost`.`companymarkstatus` (`status_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `company_valuation_by_capital_cost`.`userstatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_valuation_by_capital_cost`.`userstatus` (
  `user_status_id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_status_id`),
  UNIQUE INDEX `user_status_id_UNIQUE` (`user_status_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `company_valuation_by_capital_cost`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_valuation_by_capital_cost`.`users` (
  `user_id` VARCHAR(36) NOT NULL,
  `user_status_id` VARCHAR(36) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `user_status_id_idx` (`user_status_id` ASC) VISIBLE,
  CONSTRAINT `user_status_id`
    FOREIGN KEY (`user_status_id`)
    REFERENCES `company_valuation_by_capital_cost`.`userstatus` (`user_status_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



INSERT INTO `company_valuation_by_capital_cost`.`userstatus` (`user_status_id`, `name`) VALUES ('1', 'admin');
INSERT INTO `company_valuation_by_capital_cost`.`userstatus` (`user_status_id`, `name`) VALUES ('2', 'user');
INSERT INTO `company_valuation_by_capital_cost`.`userstatus` (`user_status_id`, `name`) VALUES ('3', 'valuer');


INSERT INTO `company_valuation_by_capital_cost`.`companymarkstatus` (`status_id`, `name`) VALUES ('1', 'Выгодно для инвестиций');
INSERT INTO `company_valuation_by_capital_cost`.`companymarkstatus` (`status_id`, `name`) VALUES ('2', 'Невыгодно для инвестиций');
INSERT INTO `company_valuation_by_capital_cost`.`companymarkstatus` (`status_id`, `name`) VALUES ('3', 'Затруднился оценить');
INSERT INTO `company_valuation_by_capital_cost`.`companymarkstatus` (`status_id`, `name`) VALUES ('4', 'Не оцененно');
