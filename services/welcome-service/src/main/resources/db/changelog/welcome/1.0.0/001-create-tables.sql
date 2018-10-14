--liquibase formatted sql

--changeset michaelilyin:create-table_common_info
CREATE TABLE common_info (
  key VARCHAR(255) PRIMARY KEY,
  type VARCHAR(32),
  content TEXT
);

--changeset michaelilyin:fill_common_info
INSERT INTO common_info VALUES ('applicationName', 'TEXT', 'Initial name');
INSERT INTO common_info VALUES ('primaryTitle', 'TEXT', 'Initial primary title');