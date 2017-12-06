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