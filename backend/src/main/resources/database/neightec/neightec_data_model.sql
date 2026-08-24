--liquibase formatted sql
--changeset nsu:1
--comment: neightect_data add users
CREATE SCHEMA IF NOT EXISTS neightec;

CREATE TABLE IF NOT EXISTS neightec.neightec_users (
	id                   uuid  NOT NULL  ,
	first_name           varchar(100)    ,
	last_name            varchar(100)    ,
	creation_date        timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	valid_start          timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	valid_end            timestamp DEFAULT null   ,
	CONSTRAINT pk_neightec_users PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS neightec.file_signature (
    id                      uuid  NOT NULL ,
 	hex_signature           bytea NOT NULL ,
 	iso_8859                varchar(100) NOT NULL ,
 	extension               varchar(100) NOT NULL ,
 	description             varchar ,
 	CONSTRAINT pk_file_signature PRIMARY KEY ( id )
);

--liquibase formatted sql
--changeset nsu:2
--comment: add new table for file types
DROP TABLE IF EXISTS neightec.file_types;

CREATE TABLE neightec.file_types
(
    id                   uuid  NOT NULL  ,
    name	        VARCHAR(100),
    extension	    VARCHAR(100),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_valid	    boolean DEFAULT false,
    CONSTRAINT pk_file_types PRIMARY KEY ( id )
);

--liquibase formatted sql
--changeset nsu:3
--comment: add new table for guest
DROP TABLE IF EXISTS neightec.guest;

CREATE TABLE neightec.guest
(
    id                  uuid NOT NULL,
    full_name	        VARCHAR(100),
    attendance_status	VARCHAR(100),
    valid_start     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_guest PRIMARY KEY ( id )
);

--liquibase formatted sql
--changeset nsu:4
--comment: add new data models for transaction
DROP TABLE IF EXISTS neightec.wallet_transaction;
DROP TABLE IF EXISTS neightec.wallet_transaction_type;
DROP TABLE IF EXISTS neightec.wallet_transaction_currency;
DROP TABLE IF EXISTS neightec.wallet_transaction_category;

CREATE TABLE neightec.wallet_transaction_currency (
	id 						uuid  NOT NULL,
	currency 			VARCHAR(50),
	currency_code VARCHAR(50)
);

CREATE TABLE neightec.wallet_transaction_type (
	id 		uuid  NOT NULL PRIMARY KEY,
	name 	VARCHAR(50)
);

CREATE TABLE neightec.wallet_transaction_category (
	id 		uuid  NOT NULL,
	name 	VARCHAR(50),
	icon 	VARCHAR(50)
);

CREATE TABLE neightec.wallet_transaction (
	id uuid  NOT NULL,
	name VARCHAR(50),
	category_type VARCHAR(50),
	price NUMERIC(10,2),
	currency_type VARCHAR(50),
	timestamp TIMESTAMP DEFAULT null,
	wallet_transaction_type_id uuid NOT NULL REFERENCES neightec.wallet_transaction_type(id),
    CONSTRAINT pk_wallet_transaction PRIMARY KEY (id)
);

--liquibase formatted sql
--changeset nsu:5
--comment: add new data models for wallet session, wallet user and wallet session user to transaction
CREATE TABLE IF NOT EXISTS neightec.wallet_session (
    id       		uuid  NOT NULL,
    is_active		boolean DEFAULT false,
    start_session   TIMESTAMP DEFAULT null,
    end_session     TIMESTAMP DEFAULT null,
    CONSTRAINT pk_wallet_session PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS neightec.wallet_user (
    id 		    uuid NOT NULL,
    username 		    VARCHAR(125) DEFAULT null,
    wallet_session_id   uuid REFERENCES neightec.wallet_session(id),
    CONSTRAINT pk_wallet_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS neightec.wallet_session_to_transaction (
    id 		        uuid NOT NULL,
    wallet_session_id 		uuid NOT NULL REFERENCES neightec.wallet_session(id),
    wallet_transaction_id  uuid NOT NULL REFERENCES neightec.wallet_transaction(id),
    CONSTRAINT pk_wallet_session_to_transaction PRIMARY KEY (id)
);