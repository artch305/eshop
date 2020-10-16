use eshop;

-- insert data into order_statuses
insert into order_statuses (id, order_status) values (1,"registered");
insert into order_statuses (id, order_status) values (2,"paid");
insert into order_statuses (id, order_status) values (3,"canceled");

-- insert data into users
insert into users (login, email, password, status, role, lang) values ("artch", "artch@gmail.com", "artchpass", "active", "administrator", "en");
insert into users (login, email, password, status, role, lang) values ("ferian", "ferian@ukr.net", "ferianpass", "active", "customer", "ru");
insert into users (login, email, password, status, role, lang) values ("mefist", "mefist@gmail.com", "mefistpass", "active", "customer", "ru");

-- insert data into products
insert into products (category, producer, name, price, img_url) values ("monitors", "Samsung", "S22R350FHN", 109.99, "img/monitors/Samsung S22R350FHN.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Logitech", "MK270", 58.36, "img/keyboards/Logitech MK270.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "Acer", "SB220Q", 142.99, "img/monitors/Acer SB220Q.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "LG", "24M47VQ", 99.99, "img/monitors/LG 24M47VQ.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Razer", "Huntsman Gaming", 89.99, "img/keyboards/Razer Huntsman Gaming.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "Samsung", "C24RG50F", 220.70, "img/monitors/Samsung C24RG50F.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "Samsung", "S24R350FHI", 123.60, "img/monitors/Samsung S24R350FHI.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "Xiaomi", "Mi Surface Display 34", 491.20, "img/monitors/Xiaomi Mi Surface Display 34.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "QUBE", "C27Q165", 324.31, "img/monitors/QUBE C27Q165.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "AOC", "24G2U", 303.85, "img/monitors/AOC 24G2U.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "LG", "27GL850-B", 546.60, "img/monitors/LG 27GL850-B.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "Acer", "Nitro VG240Y", 286.40, "img/monitors/Acer Nitro VG240Y.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "MSI", "Optix G241VC", 206.99, "img/monitors/MSI Optix G241VC.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "HyperX", "Alloy FPS Pro", 99.99, "img/keyboards/HyperX Alloy FPS Pro.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Hator", "Rockfall EVO TKL", 81.27, "img/keyboards/Hator Rockfall EVO TKL.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Logitech", "Corded Keyboard K280e", 28.27, "img/keyboards/Logitech Corded Keyboard K280e.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "A4 Tech", "KV-300H", 38.90, "img/keyboards/A4 Tech KV-300H.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Logitech", "Wireless Keyboard K360", 56.5, "img/keyboards/Logitech Wireless Keyboard K360.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Logitech", "Bluetooth Multi-Device Keyboard K480", 69.85, "img/keyboards/Logitech Bluetooth Multi-Device Keyboard K480.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Sven", "Standard 301", 5.99, "img/keyboards/Sven Standard 301.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Trust", "GXT 870", 67.1, "img/keyboards/Trust GXT 870.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "Mionix", "Zibal", 106, "img/keyboards/Mionix Zibal.jpg");
insert into products (category, producer, name, price, img_url) values ("monitors", "ASUS", "TUF Gaming VG27AQ", 549.99, "img/monitors/Asus TUF Gaming VG27AQ.jpg");
insert into products (category, producer, name, price, img_url) values ("keyboards", "HP", "OMEN Encoder", 103.69, "img/keyboards/HP OMEN Encoder.jpg");


-- insert data into monitor_products
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (1, 22, "IPS", 300);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (3, 21.5, "VA", 400);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (4, 24, "IPS", 350);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (6, 24, "VA", 250);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (7, 24, "IPS", 250);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (8, 34, "VA", 300);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (9, 27, "VA", 400);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (10, 24, "IPS", 250);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (11, 27, "IPS", 350);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (12, 24, "IPS", 250);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (13, 24, "VA", 250);
insert into monitor_products (product_id, diagonal, panel_type, brightness) values (23, 27, "IPS", 350);


-- insert data into keyboard_products
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (2, "PS/2", 0, "");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (5, "USB", 1, "RGB");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (14, "USB", 1, "");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (15, "USB", 0, "RGB");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (16, "USB", 0, "");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (17, "USB", 0, "RED");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (18, "Wireless", 0, "");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (19, "Bluetooth", 0, "");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (20, "PS/2", 0, "");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (21, "USB", 0, "WHITE");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (22, "USB", 1, "GREEN");
insert into keyboard_products (product_id, connection_type, mechanical, light_color) values (24, "USB", 1, "RED");


-- insert data into orders
insert into orders (user_id, order_status_id) values (2,1);
insert into orders (user_id, order_status_id) values (3,1);

-- insert data into orders_has_products
insert into orders_has_products (order_id, product_id, amount) values (1, 3, 2);
insert into orders_has_products (order_id, product_id, amount) values (1, 5, 3);
insert into orders_has_products (order_id, product_id, amount) values (2, 4, 1);
insert into orders_has_products (order_id, product_id, amount) values (2, 1, 3);
insert into orders_has_products (order_id, product_id, amount) values (2, 2, 5);


-- -------------------------------------------------------------------------------------
-- for testing triggers

-- update orders_has_products set amount = 1 where order_id = 1 and product_id = 5; 
-- delete from orders_has_products where order_id = 2 and product_id = 4;
-- drop database eshop


