-- -----------------------------------------------------
-- Schema currency-rate
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `currency-rate` DEFAULT CHARACTER SET utf8 ;
USE `currency-rate` ;

-- -----------------------------------------------------
-- Table `currency-rate`.`currency`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`currency` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `currency_date` DATE NOT NULL,
  `usd` DOUBLE NOT NULL,
  `eur` DOUBLE NOT NULL,
  `rub` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;