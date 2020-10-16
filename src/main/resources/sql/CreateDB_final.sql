-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema eshop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema eshop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `eshop` DEFAULT CHARACTER SET utf8 ;
USE `eshop` ;

-- -----------------------------------------------------
-- Table `eshop`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `registration_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lang` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eshop`.`order_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`order_statuses` (
  `id` INT NOT NULL,
  `order_status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eshop`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `total_price` DOUBLE NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_status_id` INT NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_orders_users1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_orders_order_statuses1_idx` (`order_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `eshop`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_order_statuses1`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `eshop`.`order_statuses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eshop`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(45) NOT NULL,
  `producer` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NULL,
  `description` VARCHAR(1024) NULL,
  `active` TINYINT NULL DEFAULT 1,
  `added_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `img_url` VARCHAR(256) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eshop`.`orders_has_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`orders_has_products` (
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `product_price` DOUBLE NULL,
  `amount` INT NULL,
  `total_price` DOUBLE NULL,
  PRIMARY KEY (`order_id`, `product_id`),
  INDEX `fk_orders_has_products1_products1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_orders_has_products1_orders1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_has_products1_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `eshop`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_has_products1_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `eshop`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eshop`.`monitor_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`monitor_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `diagonal` DOUBLE NULL,
  `panel_type` VARCHAR(45) NULL,
  `brightness` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_monitor_products_products1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_monitor_products_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `eshop`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eshop`.`keyboard_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`keyboard_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `connection_type` VARCHAR(45) NULL,
  `mechanical` TINYINT NULL DEFAULT 0,
  `light_color` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_keyboard_products_products1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_keyboard_products_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `eshop`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eshop`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`cart` (
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `amount` INT NULL,
  PRIMARY KEY (`user_id`, `product_id`),
  INDEX `fk_table1_users1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_table1_products1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_table1_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `eshop`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_table1_products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `eshop`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `eshop`;

DELIMITER $$
USE `eshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `eshop`.`orders_has_products_BEFORE_INSERT` BEFORE INSERT ON `orders_has_products` FOR EACH ROW
BEGIN
 declare found_price double;
 select price into found_price from products where id = new.product_id;
 set new.product_price = found_price;
 set new.total_price = (found_price * new.amount); 
END$$

USE `eshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `eshop`.`orders_has_products_AFTER_INSERT` AFTER INSERT ON `orders_has_products` FOR EACH ROW
BEGIN
declare total_price_counting double;
select sum(total_price) into total_price_counting from orders_has_products where order_id = new.order_id;
update orders set total_price = total_price_counting where id = new.order_id;
END$$

USE `eshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `eshop`.`orders_has_products_BEFORE_UPDATE` BEFORE UPDATE ON `orders_has_products` FOR EACH ROW
BEGIN
set new.total_price = (old.product_price * new.amount);
END$$

USE `eshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `eshop`.`orders_has_products_AFTER_UPDATE` AFTER UPDATE ON `orders_has_products` FOR EACH ROW
BEGIN
declare total_price_counting double;
select sum(total_price) into total_price_counting from orders_has_products where order_id = new.order_id;
update orders set total_price = total_price_counting where id = new.order_id;
END$$

USE `eshop`$$
CREATE DEFINER = CURRENT_USER TRIGGER `eshop`.`orders_has_products_AFTER_DELETE` AFTER DELETE ON `orders_has_products` FOR EACH ROW
BEGIN
declare total_price_counting double;
select sum(total_price) into total_price_counting from orders_has_products where order_id = old.order_id;
update orders set total_price = total_price_counting where id = old.order_id;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
