DROP VIEW IF EXISTS report_customers_by_uf;

ALTER TABLE customers
ADD version BIGINT;

ALTER TABLE addresses
ADD version BIGINT;