-- -----------------------------------------------------
-- Schema currency_rate
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `currency_rate`;

-- -----------------------------------------------------
-- Schema currency_rate
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `currency_rate` DEFAULT CHARACTER SET utf8;
USE `currency_rate`;

-- -----------------------------------------------------
-- Table `currency_rate`.`currency_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`currency_type`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`currency_type`
(
  `id`            INT         NOT NULL AUTO_INCREMENT,
  `currency_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `currency_rate`.`currency_select`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`currency_select`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`currency_select`
(
  `id`              INT         NOT NULL AUTO_INCREMENT,
  `currency_select` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `currency_rate`.`bank`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`bank`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`bank`
(
  `id`        INT         NOT NULL,
  `bank_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `currency_rate`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`city`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`city`
(
  `id`   INT         NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `currency_rate`.`bank_branch`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`bank_branch`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`bank_branch`
(
  `id`        INT          NOT NULL AUTO_INCREMENT,
  `name`      VARCHAR(200) NOT NULL,
  `address`   VARCHAR(200) NOT NULL,
  `work_time` DATETIME     NOT NULL,
  `bank_id`   INT          NOT NULL,
  `city_id`   INT          NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bank_branch_bank_idx` (`bank_id` ASC) VISIBLE,
  INDEX `fk_bank_branch_city1_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `fk_bank_branch_bank`
    FOREIGN KEY (`bank_id`)
      REFERENCES `currency_rate`.`bank` (`id`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_bank_branch_city`
    FOREIGN KEY (`city_id`)
      REFERENCES `currency_rate`.`city` (`id`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `currency_rate`.`currency_value`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`currency_value`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`currency_value`
(
  `id`                 INT    NOT NULL AUTO_INCREMENT,
  `currencyValue`      DOUBLE NOT NULL,
  `bank_branch_id`     INT    NOT NULL,
  `currency_type_id`   INT    NOT NULL,
  `currency_select_id` INT    NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_currency_value_bank_branch1_idx` (`bank_branch_id` ASC) VISIBLE,
  INDEX `fk_currency_value_currency_select1_idx` (`currency_select_id` ASC) VISIBLE,
  INDEX `fk_currency_value_currency_type_idx` (`currency_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_currency_value_bank_branch`
    FOREIGN KEY (`bank_branch_id`)
      REFERENCES `currency_rate`.`bank_branch` (`id`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_currency_value_currency_type`
    FOREIGN KEY (`currency_type_id`)
      REFERENCES `currency_rate`.`currency_type` (`id`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_currency_value_currency_select`
    FOREIGN KEY (`currency_select_id`)
      REFERENCES `currency_rate`.`currency_select` (`id`)
      ON DELETE CASCADE
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB;