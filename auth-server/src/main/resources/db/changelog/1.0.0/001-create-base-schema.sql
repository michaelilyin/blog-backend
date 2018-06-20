--liquibase formatted sql

--changeset michaelilyin-auth:create-auth-tables
DROP TABLE IF EXISTS client_details;
CREATE TABLE client_details (
  app_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256) DEFAULT NULL,
  app_secret VARCHAR(256) DEFAULT NULL,
  scope VARCHAR(256) DEFAULT NULL,
  grant_types VARCHAR(256) DEFAULT NULL,
  redirect_url VARCHAR(256) DEFAULT NULL,
  authorities VARCHAR(256) DEFAULT NULL,
  access_token_validity INTEGER DEFAULT NULL,
  refresh_token_validity INTEGER DEFAULT NULL,
  additionalInformation VARCHAR(4096) DEFAULT NULL
);

DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  CONSTRAINT auth_username UNIQUE(username, authority)
);

DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE oauth_access_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BYTEA,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name VARCHAR(256) DEFAULT NULL,
  client_id VARCHAR(256) DEFAULT NULL,
  authentication BYTEA,
  refresh_token VARCHAR(256) DEFAULT NULL
);

DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256) DEFAULT NULL,
  client_secret VARCHAR(256) DEFAULT NULL,
  scope VARCHAR(256) DEFAULT NULL,
  authorized_grant_types VARCHAR(256) DEFAULT NULL,
  web_server_redirect_uri VARCHAR(256) DEFAULT NULL,
  authorities VARCHAR(256) DEFAULT NULL,
  access_token_validity INTEGER DEFAULT NULL,
  refresh_token_validity INTEGER DEFAULT NULL,
  additional_information VARCHAR(4096) DEFAULT NULL,
  autoapprove VARCHAR(256) DEFAULT NULL
);

DROP TABLE IF EXISTS oauth_code;
CREATE TABLE oauth_code (
  code VARCHAR(256) DEFAULT NULL,
  authentication BYTEA
);

DROP TABLE IF EXISTS oauth_refresh_token;
CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BYTEA,
  authentication BYTEA
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(50) NOT NULL,
  enabled BOOL NOT NULL
);
