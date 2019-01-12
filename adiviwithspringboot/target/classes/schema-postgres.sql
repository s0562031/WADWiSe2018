DROP TABLE IF EXISTS users;
CREATE TABLE users(id integer PRIMARY KEY, lastname VARCHAR(128), firstname VARCHAR(128), password VARCHAR(128), isadmin boolean);
