--liquibase formatted sql
--changeset nsu:1
--comment: neightect_data add users
CREATE SCHEMA IF NOT EXISTS neight;

CREATE TABLE IF NOT EXISTS neight.neightec_users (
	id                   uuid  NOT NULL  ,
	first_name           varchar(100)    ,
	last_name            varchar(100)    ,
	creation_date        timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	valid_start          timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
	valid_end            timestamp DEFAULT null   ,
	CONSTRAINT pk_neightec_users PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS neight.file_signature (
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
DROP TABLE IF EXISTS neight.file_types;

CREATE TABLE neight.file_types
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
DROP TABLE IF EXISTS neight.guest;

CREATE TABLE neight.guest
(
    id                  uuid NOT NULL,
    full_name	        VARCHAR(100),
    attendance_status	VARCHAR(100),
    valid_start     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_guest PRIMARY KEY ( id )
);