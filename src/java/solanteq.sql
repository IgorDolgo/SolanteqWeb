DROP TABLE IF EXISTS `Employee` ;

DROP TABLE IF EXISTS `Position` ;

CREATE  TABLE IF NOT EXISTS `Position` (
  `id` TINYINT NOT NULL AUTO_INCREMENT ,
  `Name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `Employee` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `Surname` VARCHAR(100) NOT NULL ,
  `Name` VARCHAR(100) NOT NULL ,
  `Patronymic` VARCHAR(100) NULL ,
  `DateOfBirth` DATE NOT NULL ,
  `Position` TINYINT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Employee_1_idx` (`Position` ASC) ,
  CONSTRAINT `fk_Employee_1`
    FOREIGN KEY (`Position` )
    REFERENCES `Position` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO position (Name) VALUES ('Tester');
INSERT INTO position (Name) VALUES ('Developer');
INSERT INTO position (Name) VALUES ('TeamLead');

INSERT INTO employee (Surname, Name, Patronymic, DateOfBirth, Position) VALUES ('Петров', 'Пётр', 'Петрович', '1950-01-31', 1);
INSERT INTO employee (Surname, Name, Patronymic, DateOfBirth, Position) VALUES ('Иванов', 'Иван', 'Иванович', '1960-05-20', 2);
INSERT INTO employee (Surname, Name, Patronymic, DateOfBirth, Position) VALUES ('Thompson', 'John', null, '1970-10-15', 3);
INSERT INTO employee (Surname, Name, Patronymic, DateOfBirth, Position) VALUES ('Фёдоров', 'Фёдр', 'Фёдорович', '1980-02-10', 2);
