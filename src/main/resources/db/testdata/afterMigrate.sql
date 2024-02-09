set FOREIGN_KEY_CHECKS = 0;

DELETE FROM user;
DELETE FROM address;
DELETE FROM role;
DELETE FROM privilege;
DELETE FROM role_privilege;
DELETE FROM user_role;
DELETE FROM category;
DELETE FROM product;
DELETE FROM product_category;

set foreign_key_checks = 1;

/* USER */
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES (1, 'Alex', 'Alex Brown', '1993-07-14', '86213939059', '222182428', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'alex@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES (2, 'Maria', 'Maria Green','2001-01-14', '67709960065', '355144724', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'maria@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES (3, 'Client', 'Client LastName', '1998-12-14', '61406233080', '365829171', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'client@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES (4, 'Operator', 'Operator LastName', '1986-01-14', '56335885093', '360774246', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'operator@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES (5, 'Administrator', 'Administrator LastName', '1980-03-14', '36825973010', '266166878', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'administrator@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES (6, 'NotVerified', 'NotVerified LastName', '2005-08-14', '65623542000', '497619301', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'notverified@gmail.com', '54991200038', '54991200038', NOW(), null, null);

/* ADDRESS */
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (1, 1, 'Home', true, 'Brockton Avenue', '65', 'Center', 'AP 2', 'Next to the happy market', 'Massapequa', 'New York', 'NY', '99700000', '54998204476', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (2, 2, 'Home', true, 'Middle Country Road', '44', 'East', 'Next to the driving school ', 'Center', 'Middle Island', 'NY', '38658000', '54991204478', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (3, 3, 'Home', true, 'Route 211 East', '5545', 'Center', 'In front of La Bodega ', 'Groton', 'Connecticut', 'CT', '20000000', '54991204433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (4, 3, 'Mothers home', false, 'Anawana Lake Road', '1111', 'New Village', 'Next to Bella Casa', 'Derby', 'New York', 'NY', '99700000', '54991204433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (5, 3, 'My work', false, 'Commercial Parkway', '59', 'Center', 'Room 18', 'Blumenau Building', 'Manchester', 'Connecticut', 'CT', '99700000', '54999204433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (6, 4, 'Home', true, 'Gold Star Hwy', '112', 'Monte Negro', 'Próx bar do Bilu', 'Zumbi', 'East Windsor', 'MG', '20000000', '54991209433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (7, 5, 'Home', true, 'Boston Post Road', '59', 'Center', 'Room 15', 'Blumenau Building ', 'Mildford', 'Connecticut', 'CT', '99700000', '54991604433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES (8, 6, 'Work', true, 'Buckland Hills Dr', '555', 'Center', 'Room 1', 'David Building', 'Naugatuck', 'Connecticut', 'CT', '99700000', '54991208433', NOW());

/* ROLE */
INSERT INTO role (id, name, created_at) VALUES (1, 'CLIENT', NOW());
INSERT INTO role (id, name, created_at) VALUES (2, 'OPERATOR', NOW());
INSERT INTO role (id, name, created_at) VALUES (3, 'ADMIN', NOW());
INSERT INTO role (id, name, created_at) VALUES (4, 'TEST', NOW());

/* PRIVILEGE */
INSERT INTO privilege (id, name, description, created_at) VALUES (1, 'CONSULT_CATEGORIES', 'Allow consult categories', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (2, 'EDIT_CATEGORIES', 'Allow edit categories', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (3, 'CONSULT_USERS', 'Allow consult users', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (4, 'EDIT_USERS', 'Allow edit users', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (5, 'CONSULT_ADDRESSES', 'Allow consult addresses', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (6, 'EDIT_ADDRESSES', 'Allow edit addresses', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (7, 'EDIT_FILE', 'Allow edit file', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (8, 'CONSULT_PRODUCTS', 'Allow consult products', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (9, 'EDIT_PRODUCTS', 'Allow edit products', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (10, 'CONSULT_ROLES', 'Allow consult roles', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (11, 'EDIT_ROLES', 'Allow edit roles', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (12, 'CONSULT_USER_ROLES', 'Allow consult user roles', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES (13, 'EDIT_USER_ROLES', 'Allow edit user roles', NOW());

/* ROLE_PRIVILEGE */
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 5);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 6);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 7);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 8);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 9);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 10);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 11);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 12);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 13);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 5);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 6);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 7);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 8);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 9);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 10);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 11);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 12);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 13);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (4, 1);

/* USER_ROLE */
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO user_role (user_id, role_id) VALUES (4, 2);
INSERT INTO user_role (user_id, role_id) VALUES (5, 3);
INSERT INTO user_role (user_id, role_id) VALUES (6, 1);

/*-------------------------------------------*/

/* CATEGORY */
INSERT INTO category (id, name, created_At) VALUES (1, 'Books', NOW());
INSERT INTO category (id, name, created_At) VALUES (2, 'Eletronics', NOW());
INSERT INTO category (id, name, created_At) VALUES (3, 'Computers', NOW());
INSERT INTO category (id, name, created_At) VALUES (4, 'Only for tests', NOW());

/* PRODUCT */
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (1, 'TVLG32BL', 'Smart TV', 2190.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (2, 'BKSA0100', 'The Lord of The Rings', 190.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (3, 'NBAP14SI', 'Macbook Pro', 1250.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (4, 'PCPO01BL', 'PC Gamer', 1200.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (5, 'BKSA0200', 'Rails for Dummies', 100.99, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/5-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (6, 'PCPOEXBK', 'PC Gamer Ex', 1350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (7, 'PCPOXXBK', 'PC Gamer X', 1350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/7-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (8, 'PCPOAFBK', 'PC Gamer Alfa', 1850.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/8-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (9, 'PCPOTEBK', 'PC Gamer Tera', 1950.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/9-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (10, 'PCPOYYBK', 'PC Gamer Y', 1700.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/10-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (11, 'PCPONIBK', 'PC Gamer Nitro', 1450.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/11-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (12, 'PCPOCABK', 'PC Gamer Card', 1850.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/12-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (13, 'PCPOPLBK', 'PC Gamer Plus', 1350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/13-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (14, 'PCPOHEBK', 'PC Gamer Hera', 2250.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/14-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (15, 'PCPOWEBK', 'PC Gamer Weed', 2200.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/15-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (16, 'PCPOMABK', 'PC Gamer Max', 2340.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/16-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (17, 'PCPOTUBK', 'PC Gamer Turbo', 1280.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/17-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (18, 'PCPOHTBK', 'PC Gamer Hot', 1450.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/18-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (19, 'PCPOEZBK', 'PC Gamer Ez', 1750.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/19-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (20, 'PCPOTRBK', 'PC Gamer Tr', 1650.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/20-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (21, 'PCPOTXBK', 'PC Gamer Tx', 1680.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/21-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (22, 'PCPOERBK', 'PC Gamer Er', 1850.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/22-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (23, 'PCPOMIBK', 'PC Gamer Min', 2250.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/23-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (24, 'PCPOBOBK', 'PC Gamer Boo', 2350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/24-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES (25, 'PCPOFOBK', 'PC Gamer Foo', 4170.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/25-big.jpg', NOW());

/* PRODUCT_CATEGORY */
INSERT INTO product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (1, 3);
INSERT INTO product_category (product_id, category_id) VALUES (3, 3);
INSERT INTO product_category (product_id, category_id) VALUES (3, 2);
INSERT INTO product_category (product_id, category_id) VALUES (4, 3);
INSERT INTO product_category (product_id, category_id) VALUES (5, 2);
INSERT INTO product_category (product_id, category_id) VALUES (6, 3);
INSERT INTO product_category (product_id, category_id) VALUES (7, 3);
INSERT INTO product_category (product_id, category_id) VALUES (8, 3);
INSERT INTO product_category (product_id, category_id) VALUES (9, 3);
INSERT INTO product_category (product_id, category_id) VALUES (10, 3);
INSERT INTO product_category (product_id, category_id) VALUES (11, 3);
INSERT INTO product_category (product_id, category_id) VALUES (12, 3);
INSERT INTO product_category (product_id, category_id) VALUES (13, 3);
INSERT INTO product_category (product_id, category_id) VALUES (14, 3);
INSERT INTO product_category (product_id, category_id) VALUES (15, 3);
INSERT INTO product_category (product_id, category_id) VALUES (16, 3);
INSERT INTO product_category (product_id, category_id) VALUES (17, 3);
INSERT INTO product_category (product_id, category_id) VALUES (18, 3);
INSERT INTO product_category (product_id, category_id) VALUES (19, 3);
INSERT INTO product_category (product_id, category_id) VALUES (20, 3);
INSERT INTO product_category (product_id, category_id) VALUES (21, 3);
INSERT INTO product_category (product_id, category_id) VALUES (22, 3);
INSERT INTO product_category (product_id, category_id) VALUES (23, 3);
INSERT INTO product_category (product_id, category_id) VALUES (24, 3);
INSERT INTO product_category (product_id, category_id) VALUES (25, 3);