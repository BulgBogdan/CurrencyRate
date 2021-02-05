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
-- Table `currency_rate`.`currency_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`currency_type`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`currency_type`
(
  `id`            INT         NOT NULL AUTO_INCREMENT,
  `currency_name` VARCHAR(45) NOT NULL,
  `FK_bank`       INT         NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_currency_type_bank1_idx` (`FK_bank` ASC) VISIBLE,
  CONSTRAINT `fk_bank`
    FOREIGN KEY (`FK_bank`)
      REFERENCES `currency_rate`.`bank` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
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
  `currency_date`   DATE        NOT NULL,
  `FK_currencyType` INT         NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_currency_selected_currency_type1_idx` (`FK_currencyType` ASC) VISIBLE,
  CONSTRAINT `fk_Type`
    FOREIGN KEY (`FK_currencyType`)
      REFERENCES `currency_rate`.`currency_type` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `currency_rate`.`currency_value`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `currency_rate`.`currency_value`;

CREATE TABLE IF NOT EXISTS `currency_rate`.`currency_value`
(
  `id`                INT    NOT NULL AUTO_INCREMENT,
  `currency_value`    DOUBLE NOT NULL,
  `FK_currencySelect` INT    NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_currency_value_currency_selected1_idx` (`FK_currencySelect` ASC) VISIBLE,
  CONSTRAINT `fk_select`
    FOREIGN KEY (`FK_currencySelect`)
      REFERENCES `currency_rate`.`currency_select` (`id`)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
  ENGINE = InnoDB;