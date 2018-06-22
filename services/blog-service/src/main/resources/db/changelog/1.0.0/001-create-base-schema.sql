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

--changeset michaelilyin:create-audit-settings
CREATE TABLE bl_audit_settings (
  id BIGSERIAL PRIMARY KEY,
  tag VARCHAR(255),
  login VARCHAR(64),
  severity VARCHAR(255),
  trace BOOLEAN
);

CREATE INDEX bl_audit_scan_index ON bl_audit_settings (tag, login);

--changeset michaelilyin:create-audit-level
CREATE TABLE bl_audit_level (
  id VARCHAR(8) PRIMARY KEY,
  priority INTEGER NOT NULL
);

--changeset michaelilyin:fill-audit-level-data
INSERT INTO bl_audit_level (id, priority)
    VALUES
      ('ERROR', 10),
      ('INFO', 20),
      ('DEBUG', 30);
