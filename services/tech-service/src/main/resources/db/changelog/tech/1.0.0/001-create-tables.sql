--liquibase formatted sql

--changeset michaelilyin:create-tables
CREATE TABLE technologies (
  id BIGSERIAL PRIMARY KEY
)