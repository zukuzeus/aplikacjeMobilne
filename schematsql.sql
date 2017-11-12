CREATE DATABASE aplikacjemobilnezukowskistage1;
CREATE USER apka WITH PASSWORD 'apka';

CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(40) NOT NULL PRIMARY KEY,
  password VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
  username VARCHAR(40) REFERENCES users (username),
  product  VARCHAR(100),
  store    VARCHAR(100),
  price    REAL,
  quantity INTEGER,
  PRIMARY KEY (username, product)
);


INSERT INTO users VALUES ('daniel', '12345');
INSERT INTO users VALUES ('Niko1@#$', 'BUŁKA');
INSERT INTO products VALUES ('daniel', 'kajzerka', 'TESCO', 14.89, 10);
INSERT INTO products VALUES ('Niko1@#$', 'kajzerka', 'TESCO', 14.89, 10);
INSERT INTO products VALUES ('daniel', 'boczek', 'Biedronka', 9, 1), ('daniel', 'jogurt', 'lidl', 3.58, 2);
UPDATE products
SET quantity = 10
WHERE product = 'kajzerka' AND username = 'daniel';

INSERT INTO / tablename/ VALUES (val1, val2 ....)

DELETE FROM users
WHERE username = 'user1';

