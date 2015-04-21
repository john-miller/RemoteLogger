INSERT INTO ROLE (authority) VALUES ('ADMIN');
INSERT INTO ROLE (authority) VALUES ('ADD');
INSERT INTO ROLE (authority) VALUES ('READ');
INSERT INTO users (username, password, account_expired, account_locked, credentials_expired, account_enabled) VALUES ('user', '$2a$10$789li9JEpPIpqQFExSRT1OR076SU//TBYVxnAli2VBTQ7Dp/UlM6O', false, false, false, false);
INSERT INTO users_user_roles (users_id, user_roles_id) VALUES ((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM role WHERE authority = 'ADMIN'));


-- ROLES STATEMENT USED IN SECURITY CONFIGURE
SELECT username, authority FROM users_user_roles JOIN users on users_id = users.id JOIN role ON user_roles_id = role.id where username = ?;