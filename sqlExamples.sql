SELECT max(deviceid)
FROM deviceidentificator
WHERE username = 'd';
SELECT *
FROM deviceidentificator;

SELECT *
FROM products
WHERE username = 'S' AND product = 'pomarancz';

INSERT INTO quantytiesperdevice VALUES ('S', 'marchew', 1, 3)
ON CONFLICT (username, product, deviceid)
  DO UPDATE SET quantity = 4;

-- UPDATE products SET quantity = 1 WHERE kind = 'Drama';

UPDATE products p
SET quantity = f.valsum
FROM
  (
    SELECT
      username,
      product,
      SUM(quantytiesperdevice.quantity) valsum
    FROM quantytiesperdevice
    GROUP BY username, product
  ) f
WHERE p.username = f.username AND p.product = f.product;


DELETE FROM quantytiesperdevice
WHERE username = 'S' AND deviceid = '1';


DELETE FROM products p
WHERE NOT EXISTS(
    SELECT 1
    FROM quantytiesperdevice qty
    WHERE qty.username = p.username AND qty.product = p.product
);

SELECT
  n1.username,
  n1.product,
  n1.store,
  n1.price,
  n2.otherDeviceQuantity
FROM (SELECT
        products.username,
        products.product,
        products.store,
        products.price
      FROM products) n1
  INNER JOIN (SELECT
                quantytiesperdevice.username,
                quantytiesperdevice.product,
                SUM(quantytiesperdevice.quantity) otherDeviceQuantity
              FROM quantytiesperdevice
              WHERE quantytiesperdevice.deviceid != 3
              GROUP BY username, product) n2
    ON (n2.username = n1.username AND n2.product = n1.product)
WHERE n1.username = 'S';