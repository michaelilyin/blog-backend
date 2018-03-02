--liquibase formatted sql

--changeset michaelilyin:create-audit-table
CREATE TABLE bl_audit (
  id BIGSERIAL PRIMARY KEY,
  tag VARCHAR(255) NOT NULL,
  thread VARCHAR(255) NOT NULL,
  time TIMESTAMP NOT NULL,
  severity VARCHAR(255) NOT NULL,
  login VARCHAR(64) NOT NULL,
  message TEXT NOT NULL,
  trace JSONB
);