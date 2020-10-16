DROP TABLE IF EXISTS `orders_has_products` CASCADE;
DROP TABLE IF EXISTS `orders` CASCADE;
DROP TABLE IF EXISTS `users` CASCADE;
DROP TABLE IF EXISTS `products` CASCADE;
DROP TABLE IF EXISTS `monitor_products` CASCADE;
DROP TABLE IF EXISTS `keyboard_products` CASCADE;
DROP TABLE IF EXISTS `cart` CASCADE;
DROP TABLE IF EXISTS `order_statuses` CASCADE;

CREATE TABLE `users`
(
    `id`                INT         NOT NULL AUTO_INCREMENT,
    `login`             VARCHAR(45) NOT NULL UNIQUE,
    `email`             VARCHAR(45) NULL UNIQUE,
    `password`          VARCHAR(45) NOT NULL,
    `status`            VARCHAR(45),
    `role`              VARCHAR(45) ,
    `registration_date` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lang`              VARCHAR(45) ,
    PRIMARY KEY (`id`)
);

CREATE TABLE `order_statuses`
(
    `id`           INT         NOT NULL,
    `order_status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `orders`
(
    `id`               INT       NOT NULL AUTO_INCREMENT,
    `user_id`          INT       NOT NULL,
    `total_price`      DOUBLE    NULL,
    `create_date`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `order_status_id`  INT       NOT NULL,
    PRIMARY KEY (`id`, `user_id`),
    CONSTRAINT `fk_orders_users1`
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_orders_order_statuses1`
        FOREIGN KEY (`order_status_id`)
            REFERENCES `order_statuses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `products`
(
    `id`          INT           NOT NULL AUTO_INCREMENT,
    `category`    VARCHAR(45)   NOT NULL,
    `producer`    VARCHAR(45)   NOT NULL,
    `name`        VARCHAR(45)   NOT NULL,
    `price`       DOUBLE        NULL,
    `description` VARCHAR(1024) NULL,
    `active`      TINYINT       NULL     DEFAULT 1,
    `added_date`  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `img_url`     VARCHAR(256)  NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `orders_has_products`
(
    `order_id`      INT    NOT NULL,
    `product_id`    INT    NOT NULL,
    `product_price` DOUBLE NULL,
    `amount`        INT    NULL,
    `total_price`   DOUBLE NULL,
    PRIMARY KEY (`order_id`, `product_id`),
    CONSTRAINT `fk_orders_has_products1_orders1`
        FOREIGN KEY (`order_id`)
            REFERENCES `orders` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_orders_has_products1_products1`
        FOREIGN KEY (`product_id`)
            REFERENCES `products` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `monitor_products`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `product_id` INT         NOT NULL,
    `diagonal`   DOUBLE      NULL,
    `panel_type` VARCHAR(45) NULL,
    `brightness` INT         NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_monitor_products_products1`
        FOREIGN KEY (`product_id`)
            REFERENCES `products` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `keyboard_products`
(
    `id`              INT         NOT NULL AUTO_INCREMENT,
    `product_id`      INT         NOT NULL,
    `connection_type` VARCHAR(45) NULL,
    `mechanical`      TINYINT     NULL DEFAULT 0,
    `light_color`     VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_keyboard_products_products1`
        FOREIGN KEY (`product_id`)
            REFERENCES `products` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `cart`
(
    `user_id`    INT NOT NULL,
    `product_id` INT NOT NULL,
    `amount`     INT NULL,
    PRIMARY KEY (`user_id`, `product_id`),
    CONSTRAINT `fk_table1_users1`
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_table1_products1`
        FOREIGN KEY (`product_id`)
            REFERENCES `products` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
