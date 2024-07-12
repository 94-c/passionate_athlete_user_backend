INSERT INTO roles (name) VALUES ('ADMIN'),('MANAGER'),('USER');
INSERT INTO branch_offices (name) VALUES ('기타');
INSERT INTO users
(height, weight, branch_id, id, birth_date, code, create_date, modified_date, name, password, phone_number, user_id, gender, status)
VALUES(174.0, 93.1, 1, 1, '941026', 'G-b8bc83', '2024/07/12 09:00:16', '2024/07/12 09:00:16', '관리자', '$2a$10$Yv2tSqMCt8Vpi/TMcTSGUutHXAbgJo5PQsubL.5worA2o9jSvE4hu', '01080778246', 'admin', 'MALE', 'ING');
INSERT INTO user_roles (role_id, user_id) VALUES (1, 1);
