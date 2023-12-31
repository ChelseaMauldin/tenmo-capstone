BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfer (
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	user_sending int NOT NULL,
	user_receiving int NOT NULL,
	amount numeric(13, 2) NOT NULL,
	status varchar(15) NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfer_tenmo_user_sending FOREIGN KEY (user_sending) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_transfer_tenmo_user_receiving FOREIGN KEY (user_receiving) REFERENCES tenmo_user (user_id)
);

INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('ann', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy'),
       ('jim', 'jim'),
       ('joe', 'joe'),
       ('sue', 'sue');

INSERT INTO account(user_id, balance)
VALUES (1001, 1000.00),
       (1002, 1000.00),
       (1003, 1000.00),
       (1004, 1000.00),
       (1005, 500.00);

INSERT INTO transfer (user_sending, user_receiving, amount, status)
VALUES (1001, 1002, 325.00, 'Approved'),
       (1002, 1002, 400.00, 'Denied'),
       (1003, 1004, 0.00, 'Denied'),
       (1004, 1005, -325.00, 'Denied'),
       (1005, 1004, 600.00, 'Denied');

COMMIT;