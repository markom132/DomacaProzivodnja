CREATE
DATABASE IF NOT EXISTS domace;

USE
domace;

CREATE TABLE IF NOT EXISTS category
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    category_name
    VARCHAR
(
    30
) NOT NULL,
    description TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS product
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    product_name
    VARCHAR
(
    100
) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL
(
    10,
    2
) NOT NULL,
    image LONGBLOB,
    category_id BIGINT,
    FOREIGN KEY
(
    category_id
) REFERENCES category
(
    id
)
    );

CREATE TABLE IF NOT EXISTS orders
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    order_date
    DATETIME
    NOT
    NULL,
    total_price
    DECIMAL
(
    10,
    2
) NOT NULL,
    order_status ENUM
(
    'PENDING',
    'SHIPPED',
    'DELIVERED'
) NOT NULL
    );

CREATE TABLE IF NOT EXISTS order_item
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    product_id
    BIGINT,
    quantity
    INT
    NOT
    NULL,
    price
    DECIMAL
(
    10,
    2
) NOT NULL,
    orders_id BIGINT,
    FOREIGN KEY
(
    product_id
) REFERENCES product
(
    id
),
    FOREIGN KEY
(
    orders_id
) REFERENCES orders
(
    id
)
    );

USE
domace;  -- Uveri se da koristiš ispravnu bazu podataka

INSERT INTO category (category_name, description)
VALUES ('Hrana', 'Proizvodi koji spadaju u kategoriju hrane'),
       ('Piće', 'Proizvodi koji spadaju u kategoriju pića'),
       ('Ručno rađeni proizvodi', 'Proizvodi ručne izrade'),
       ('Ulje', 'Proizvodi koji spadaju u kategoriju ulja');

USE
domace;  -- Uveri se da koristiš ispravnu bazu podataka

INSERT INTO product (product_name, description, price, image, category_id)
VALUES ('Med sa planine', 'Prirodni med sa planinskih cvetova', 15.99, NULL, 1),
       ('Domaći džem od šljiva', 'Tradicionalni domaći džem od šljiva', 7.50, NULL, 2),
       ('Handmade keramičke šolje', 'Unikatne ručno pravljene keramičke šolje', 25.00, NULL, 3),
       ('Organsko maslinovo ulje', '100% organsko maslinovo ulje iz Dalmacije', 30.99, NULL, 4);

INSERT INTO orders (order_date, total_price, order_status)
VALUES ('2024-08-26 14:00:00', 22.00, 'PENDING'),
       ('2024-08-20 14:30:00', 19.00, 'DELIVERED');

INSERT INTO order_item (product_id, quantity, price, orders_id)
VALUES (1, 2, 10.00, 1),
       (4, 1, 10.22, 2);
