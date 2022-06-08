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
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES ('821e3c67-7f22-46af-978c-b6269cb15387', 'Alex', 'Alex Brown', '1993-07-14', '86213939059', '222182428', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'alex@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES ('d16c83fe-3a2e-42b6-97b4-503b203647f6', 'Maria', 'Maria Green','2001-01-14', '67709960065', '355144724', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'maria@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES ('3a2bf7b8-92fa-4a51-bee3-ae64a3c2286b', 'Client', 'Client LastName', '1998-12-14', '61406233080', '365829171', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'client@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES ('8903af19-36e2-44d9-b649-c3319f33be20', 'Operator', 'Operator LastName', '1986-01-14', '56335885093', '360774246', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'operator@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES ('5b79649c-f311-465f-b2d9-07355b56d08a', 'Administrator', 'Administrator LastName', '1980-03-14', '36825973010', '266166878', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'administrator@gmail.com', '54991200038', '54991200038', NOW(), NOW(), null);
INSERT INTO user (id, first_name, name, birth_date, cpf, rg, password, email, primary_telephone, secondary_telephone, created_at, verified_at, last_login_at) VALUES ('32de2852-e42f-4b14-97be-c60f2e9098f4', 'NotVerified', 'NotVerified LastName', '2005-08-14', '65623542000', '497619301', '$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq', 'notverified@gmail.com', '54991200038', '54991200038', NOW(), null, null);

/* ADDRESS */
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('37783d1e-f631-408f-8796-e4da82c275e0', '821e3c67-7f22-46af-978c-b6269cb15387', 'Home', true, 'Brockton Avenue', '65', 'Center', 'AP 2', 'Next to the happy market', 'Massapequa', 'New York', 'NY', '99700000', '54998204476', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('7e88c136-b11c-4879-920f-1193db6fef0f', 'd16c83fe-3a2e-42b6-97b4-503b203647f6', 'Home', true, 'Middle Country Road', '44', 'East', 'Next to the driving school ', 'Center', 'Middle Island', 'NY', '38658000', '54991204478', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('6353293a-d2b6-400f-997d-d6935032a52f', '3a2bf7b8-92fa-4a51-bee3-ae64a3c2286b', 'Home', true, 'Route 211 East', '5545', 'Center', 'In front of La Bodega ', 'Groton', 'Connecticut', 'CT', '20000000', '54991204433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('e9881540-bc9f-49b9-a827-eb4d5ff39887', '3a2bf7b8-92fa-4a51-bee3-ae64a3c2286b', 'Mothers home', false, 'Anawana Lake Road', '1111', 'New Village', 'Next to Bella Casa', 'Derby', 'New York', 'NY', '99700000', '54991204433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('bc9d79c6-9d51-4dbb-926c-00024e4cc456', '3a2bf7b8-92fa-4a51-bee3-ae64a3c2286b', 'My work', false, 'Commercial Parkway', '59', 'Center', 'Room 18', 'Blumenau Building', 'Manchester', 'Connecticut', 'CT', '99700000', '54999204433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('8c415152-a729-47a3-a50a-254e342a98a7', '8903af19-36e2-44d9-b649-c3319f33be20', 'Home', true, 'Gold Star Hwy', '112', 'Monte Negro', 'Próx bar do Bilu', 'Zumbi', 'East Windsor', 'MG', '20000000', '54991209433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('ce698b0f-6e11-4eff-9794-411b4067bb48', '5b79649c-f311-465f-b2d9-07355b56d08a', 'Home', true, 'Boston Post Road', '59', 'Center', 'Room 15', 'Blumenau Building ', 'Mildford', 'Connecticut', 'CT', '99700000', '54991604433', NOW());
INSERT INTO address (id, user_id, nick, main, address_line, number, neighborhood, complement, reference_point, city, state, uf, postal_code, telephone, created_at) VALUES ('0d467479-a939-4b04-a6d2-889a2f98d25f', '32de2852-e42f-4b14-97be-c60f2e9098f4', 'Work', true, 'Buckland Hills Dr', '555', 'Center', 'Room 1', 'David Building', 'Naugatuck', 'Connecticut', 'CT', '99700000', '54991208433', NOW());

/* ROLE */
INSERT INTO role (id, name, created_at) VALUES ('18aace1e-f36a-4d71-b4d1-124387d9b63a', 'CLIENT', NOW());
INSERT INTO role (id, name, created_at) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'OPERATOR', NOW());
INSERT INTO role (id, name, created_at) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'ADMIN', NOW());
INSERT INTO role (id, name, created_at) VALUES ('650861b5-a749-4ed1-964b-c72a1d4c5f0e', 'TEST', NOW());

/* PRIVILEGE */
INSERT INTO privilege (id, name, description, created_at) VALUES ('b7705487-51a1-4092-8b62-91dccd76a41a', 'CONSULT_CATEGORIES', 'Allow consult categories', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('91f550d9-548f-4d09-ac9c-1a95219033f7', 'EDIT_CATEGORIES', 'Allow edit categories', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('ab7fab73-0464-4f7c-bc18-069ff63a3dc9', 'CONSULT_USERS', 'Allow consult users', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('bafcfedf-8f1c-4f16-b474-351e347b13de', 'EDIT_USERS', 'Allow edit users', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('b7e8b3c9-d426-42f0-8594-5c46cd112aae', 'CONSULT_ADDRESSES', 'Allow consult addresses', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('a24dfd96-2b06-45d1-a4bb-f3a2ed9e3539', 'EDIT_ADDRESSES', 'Allow edit addresses', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('37f3ddf2-d8f1-4179-ac7d-7937f4501a0a', 'EDIT_FILE', 'Allow edit file', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('1b7e1912-d4f4-40d2-afa5-2dc214afefc2', 'CONSULT_PRODUCTS', 'Allow consult products', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('eb3c936f-e2a9-445c-866c-1df26bec56e3', 'EDIT_PRODUCTS', 'Allow edit products', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('c5ef538d-9fe8-459b-a9a8-1cbb71f1ce30', 'CONSULT_ROLES', 'Allow consult roles', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('516ad67c-6f76-44ae-b326-d9875d6f7536', 'EDIT_ROLES', 'Allow edit roles', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('f9b5be9a-56d1-47ad-b677-b88164922401', 'CONSULT_USER_ROLES', 'Allow consult user roles', NOW());
INSERT INTO privilege (id, name, description, created_at) VALUES ('585a242f-1058-476c-8656-eafe1fed5812', 'EDIT_USER_ROLES', 'Allow edit user roles', NOW());

/* ROLE_PRIVILEGE */
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('18aace1e-f36a-4d71-b4d1-124387d9b63a', 'b7705487-51a1-4092-8b62-91dccd76a41a');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'b7705487-51a1-4092-8b62-91dccd76a41a');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', '91f550d9-548f-4d09-ac9c-1a95219033f7');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'ab7fab73-0464-4f7c-bc18-069ff63a3dc9');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'bafcfedf-8f1c-4f16-b474-351e347b13de');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'b7e8b3c9-d426-42f0-8594-5c46cd112aae');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'a24dfd96-2b06-45d1-a4bb-f3a2ed9e3539');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', '37f3ddf2-d8f1-4179-ac7d-7937f4501a0a');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', '1b7e1912-d4f4-40d2-afa5-2dc214afefc2');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'eb3c936f-e2a9-445c-866c-1df26bec56e3');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'c5ef538d-9fe8-459b-a9a8-1cbb71f1ce30');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', '516ad67c-6f76-44ae-b326-d9875d6f7536');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', 'f9b5be9a-56d1-47ad-b677-b88164922401');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('eb1ffb79-5dfb-4b13-b615-eae094a06207', '585a242f-1058-476c-8656-eafe1fed5812');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'b7705487-51a1-4092-8b62-91dccd76a41a');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', '91f550d9-548f-4d09-ac9c-1a95219033f7');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'ab7fab73-0464-4f7c-bc18-069ff63a3dc9');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'bafcfedf-8f1c-4f16-b474-351e347b13de');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'b7e8b3c9-d426-42f0-8594-5c46cd112aae');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'a24dfd96-2b06-45d1-a4bb-f3a2ed9e3539');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', '37f3ddf2-d8f1-4179-ac7d-7937f4501a0a');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', '1b7e1912-d4f4-40d2-afa5-2dc214afefc2');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'eb3c936f-e2a9-445c-866c-1df26bec56e3');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'c5ef538d-9fe8-459b-a9a8-1cbb71f1ce30');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', '516ad67c-6f76-44ae-b326-d9875d6f7536');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', 'f9b5be9a-56d1-47ad-b677-b88164922401');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('5e0b121c-9f12-4fd3-a7e6-179b5007149a', '585a242f-1058-476c-8656-eafe1fed5812');
INSERT INTO role_privilege (role_id, privilege_id) VALUES ('650861b5-a749-4ed1-964b-c72a1d4c5f0e', 'b7705487-51a1-4092-8b62-91dccd76a41a');

/* USER_ROLE */
INSERT INTO user_role (user_id, role_id) VALUES ('821e3c67-7f22-46af-978c-b6269cb15387', 'eb1ffb79-5dfb-4b13-b615-eae094a06207');
INSERT INTO user_role (user_id, role_id) VALUES ('d16c83fe-3a2e-42b6-97b4-503b203647f6', '5e0b121c-9f12-4fd3-a7e6-179b5007149a');
INSERT INTO user_role (user_id, role_id) VALUES ('3a2bf7b8-92fa-4a51-bee3-ae64a3c2286b', '18aace1e-f36a-4d71-b4d1-124387d9b63a');
INSERT INTO user_role (user_id, role_id) VALUES ('8903af19-36e2-44d9-b649-c3319f33be20', 'eb1ffb79-5dfb-4b13-b615-eae094a06207');
INSERT INTO user_role (user_id, role_id) VALUES ('5b79649c-f311-465f-b2d9-07355b56d08a', '5e0b121c-9f12-4fd3-a7e6-179b5007149a');
INSERT INTO user_role (user_id, role_id) VALUES ('32de2852-e42f-4b14-97be-c60f2e9098f4', '18aace1e-f36a-4d71-b4d1-124387d9b63a');

/*-------------------------------------------*/

/* CATEGORY */
INSERT INTO category (id, name, created_At) VALUES ('753dad79-2a1f-4f5c-bbd1-317a53587518', 'Books', NOW());
INSERT INTO category (id, name, created_At) VALUES ('431d856e-caf2-4367-823a-924ce46b2e02', 'Eletronics', NOW());
INSERT INTO category (id, name, created_At) VALUES ('5c2b2b98-7b72-42dd-8add-9e97a2967e11', 'Computers', NOW());
INSERT INTO category (id, name, created_At) VALUES ('5227c10f-c81a-4885-b460-dbfee6dcc019', 'Only for tests', NOW());

/* PRODUCT */
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('ff9d39d5-717f-4714-9688-9e75797c0ec0', 'TVLG32BL', 'Smart TV', 2190.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/2-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('7c4125cc-8116-4f11-8fc3-f40a0775aec7', 'BKSA0100', 'The Lord of The Rings', 190.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('c8a0c055-030a-4e47-8aca-cf4634b98be5', 'NBAP14SI', 'Macbook Pro', 1250.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('f758d7cf-6005-4012-93fc-23afa45bf1ed', 'PCPO01BL', 'PC Gamer', 1200.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('e7310910-eb10-4694-854a-c95fcc7255eb', 'BKSA0200', 'Rails for Dummies', 100.99, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/5-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('c4c6609e-b45e-4821-af0c-a3f7bfd6f3a4', 'PCPOEXBK', 'PC Gamer Ex', 1350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('759c2a96-ea0f-4287-addb-cacd5e73b0bf', 'PCPOXXBK', 'PC Gamer X', 1350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/7-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('7d453673-4fb7-44d1-a2ce-2857a93f9018', 'PCPOAFBK', 'PC Gamer Alfa', 1850.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/8-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('0187e60f-451a-4fe7-8e0a-d9649e9a8751', 'PCPOTEBK', 'PC Gamer Tera', 1950.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/9-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('f778e30f-c329-4eae-aae1-bb98f52ffbc6', 'PCPOYYBK', 'PC Gamer Y', 1700.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/10-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('8e9e066d-46e5-480a-ab8c-f970436e2547', 'PCPONIBK', 'PC Gamer Nitro', 1450.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/11-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('231182ad-3108-464e-8e39-a45cc39585a9', 'PCPOCABK', 'PC Gamer Card', 1850.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/12-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('c82bd447-2a95-4cc6-84a3-a253871d8db0', 'PCPOPLBK', 'PC Gamer Plus', 1350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/13-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('dba88c27-9c04-41a8-be7c-804001fa58ec', 'PCPOHEBK', 'PC Gamer Hera', 2250.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/14-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('49c9067a-05a3-426e-82a7-f9b569f8638b', 'PCPOWEBK', 'PC Gamer Weed', 2200.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/15-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('66191403-8fb5-4878-bd16-2613ae8d9b03', 'PCPOMABK', 'PC Gamer Max', 2340.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/16-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('68c92865-ec05-4ad2-8ba2-38fd44ae21c0', 'PCPOTUBK', 'PC Gamer Turbo', 1280.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/17-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('40ec2be8-adc3-4d51-8477-25d85a7f02e6', 'PCPOHTBK', 'PC Gamer Hot', 1450.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/18-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('87fdcec4-f750-427e-854f-9ff077d77843', 'PCPOEZBK', 'PC Gamer Ez', 1750.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/19-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('9b8b9726-bda6-40d8-8ae8-f5f554c751ad', 'PCPOTRBK', 'PC Gamer Tr', 1650.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/20-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('761e1442-00b6-4d15-bf7d-a71dd17fca51', 'PCPOTXBK', 'PC Gamer Tx', 1680.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/21-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('568ce298-1b4c-4c21-bdd0-2caa9a009eed', 'PCPOERBK', 'PC Gamer Er', 1850.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/22-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('f933c036-53c3-4b1e-b2ff-2e9f36470d36', 'PCPOMIBK', 'PC Gamer Min', 2250.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/23-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('3fd5175e-b3c7-4e88-8684-8892e5d49145', 'PCPOBOBK', 'PC Gamer Boo', 2350.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/24-big.jpg', NOW());
INSERT INTO product (id, sku, name, price, description, img_url, created_at) VALUES ('638a3d4c-095f-48dc-ae7f-16ca3d3b4751', 'PCPOFOBK', 'PC Gamer Foo', 4170.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/25-big.jpg', NOW());

/* PRODUCT_CATEGORY */
INSERT INTO product_category (product_id, category_id) VALUES ('7c4125cc-8116-4f11-8fc3-f40a0775aec7', '431d856e-caf2-4367-823a-924ce46b2e02');
INSERT INTO product_category (product_id, category_id) VALUES ('ff9d39d5-717f-4714-9688-9e75797c0ec0', '753dad79-2a1f-4f5c-bbd1-317a53587518');
INSERT INTO product_category (product_id, category_id) VALUES ('ff9d39d5-717f-4714-9688-9e75797c0ec0', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('c8a0c055-030a-4e47-8aca-cf4634b98be5', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('c8a0c055-030a-4e47-8aca-cf4634b98be5', '431d856e-caf2-4367-823a-924ce46b2e02');
INSERT INTO product_category (product_id, category_id) VALUES ('f758d7cf-6005-4012-93fc-23afa45bf1ed', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('e7310910-eb10-4694-854a-c95fcc7255eb', '431d856e-caf2-4367-823a-924ce46b2e02');
INSERT INTO product_category (product_id, category_id) VALUES ('c4c6609e-b45e-4821-af0c-a3f7bfd6f3a4', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('759c2a96-ea0f-4287-addb-cacd5e73b0bf', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('7d453673-4fb7-44d1-a2ce-2857a93f9018', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('0187e60f-451a-4fe7-8e0a-d9649e9a8751', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('f778e30f-c329-4eae-aae1-bb98f52ffbc6', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('8e9e066d-46e5-480a-ab8c-f970436e2547', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('231182ad-3108-464e-8e39-a45cc39585a9', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('c82bd447-2a95-4cc6-84a3-a253871d8db0', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('dba88c27-9c04-41a8-be7c-804001fa58ec', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('49c9067a-05a3-426e-82a7-f9b569f8638b', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('66191403-8fb5-4878-bd16-2613ae8d9b03', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('68c92865-ec05-4ad2-8ba2-38fd44ae21c0', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('40ec2be8-adc3-4d51-8477-25d85a7f02e6', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('87fdcec4-f750-427e-854f-9ff077d77843', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('9b8b9726-bda6-40d8-8ae8-f5f554c751ad', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('761e1442-00b6-4d15-bf7d-a71dd17fca51', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('568ce298-1b4c-4c21-bdd0-2caa9a009eed', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('f933c036-53c3-4b1e-b2ff-2e9f36470d36', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('3fd5175e-b3c7-4e88-8684-8892e5d49145', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');
INSERT INTO product_category (product_id, category_id) VALUES ('638a3d4c-095f-48dc-ae7f-16ca3d3b4751', '5c2b2b98-7b72-42dd-8add-9e97a2967e11');