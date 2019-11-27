--DROP TABLE users IF EXISTS;

CREATE TABLE profile (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  email       VARCHAR(255) UNIQUE NOT NULL,
  password    VARCHAR(255) NOT NULL,
  balance     DECIMAL NOT NULL,
  created     DATE NOT NULL,
  enabled     BOOL DEFAULT FALSE,
  primary key (id)
);

CREATE TABLE activation_token (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  token       VARCHAR(255) UNIQUE NOT NULL,
  profile        TEXT,
  primary key (id)
);