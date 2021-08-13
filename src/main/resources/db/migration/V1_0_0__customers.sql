CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE customers (
  id BIGSERIAL PRIMARY KEY,
  uuid UUID DEFAULT uuid_generate_v4() UNIQUE NOT NULL,
  name VARCHAR (255) NOT NULL,
  email VARCHAR (255) UNIQUE NOT NULL,
  birthdate DATE NOT NULL,
  cpf VARCHAR (14) UNIQUE NOT NULL,
  gender VARCHAR(32) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    state VARCHAR(2) NOT NULL,
    city VARCHAR(255) NOT NULL,
    neighborhood VARCHAR (255) NOT NULL,
    zipCode VARCHAR (9) NOT NULL,
    street VARCHAR (255) NOT NULL,
    number INTEGER,
    additionalInformation VARCHAR (512),
    main BOOLEAN DEFAULT false NOT NULL,
    customer BIGINT NOT NULL,
    FOREIGN KEY (customer) REFERENCES customers(id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

