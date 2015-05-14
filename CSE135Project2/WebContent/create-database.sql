DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS sales CASCADE;
DROP TABLE IF EXISTS states CASCADE;
DROP TABLE IF EXISTS cart_history CASCADE;

/**table 0: [entity] states**/
CREATE TABLE states (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL
);

INSERT INTO states (name) VALUES ('Alabama');
INSERT INTO states (name) VALUES ('Alaska');
INSERT INTO states (name) VALUES ('Arizona');
INSERT INTO states (name) VALUES ('Arkansas');
INSERT INTO states (name) VALUES ('California');
INSERT INTO states (name) VALUES ('Colorado');
INSERT INTO states (name) VALUES ('Connecticut');
INSERT INTO states (name) VALUES ('Delaware');
INSERT INTO states (name) VALUES ('Florida');
INSERT INTO states (name) VALUES ('Georgia');
INSERT INTO states (name) VALUES ('Hawaii');
INSERT INTO states (name) VALUES ('Idaho');
INSERT INTO states (name) VALUES ('Illinois');
INSERT INTO states (name) VALUES ('Indiana');
INSERT INTO states (name) VALUES ('Iowa');
INSERT INTO states (name) VALUES ('Kansas');
INSERT INTO states (name) VALUES ('Kentucky');
INSERT INTO states (name) VALUES ('Louisiana');
INSERT INTO states (name) VALUES ('Maine');
INSERT INTO states (name) VALUES ('Maryland');
INSERT INTO states (name) VALUES ('Massachusetts');
INSERT INTO states (name) VALUES ('Michigan');
INSERT INTO states (name) VALUES ('Minnesota');
INSERT INTO states (name) VALUES ('Mississippi');
INSERT INTO states (name) VALUES ('Missouri');
INSERT INTO states (name) VALUES ('Montana');
INSERT INTO states (name) VALUES ('Nebraska');
INSERT INTO states (name) VALUES ('Nevada');
INSERT INTO states (name) VALUES ('New Hampshire');
INSERT INTO states (name) VALUES ('New Jersey');
INSERT INTO states (name) VALUES ('New Mexico');
INSERT INTO states (name) VALUES ('New York');
INSERT INTO states (name) VALUES ('North Carolina');
INSERT INTO states (name) VALUES ('North Dakota');
INSERT INTO states (name) VALUES ('Ohio');
INSERT INTO states (name) VALUES ('Oklahoma');
INSERT INTO states (name) VALUES ('Oregon');
INSERT INTO states (name) VALUES ('Pennsylvania');
INSERT INTO states (name) VALUES ('Rhode Island');
INSERT INTO states (name) VALUES ('South Carolina');
INSERT INTO states (name) VALUES ('South Dakota');
INSERT INTO states (name) VALUES ('Tennessee');
INSERT INTO states (name) VALUES ('Texas');
INSERT INTO states (name) VALUES ('Utah');
INSERT INTO states (name) VALUES ('Vermont');
INSERT INTO states (name) VALUES ('Virginia');
INSERT INTO states (name) VALUES ('Washington');
INSERT INTO states (name) VALUES ('West Virginia');
INSERT INTO states (name) VALUES ('Wisconsin');
INSERT INTO states (name) VALUES ('Wyoming');


/**table 1: [entity] users**/
CREATE TABLE users (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE CHECK (name <> ''),
    role        TEXT NOT NULL,
    age         INTEGER NOT NULL,
    state       INTEGER REFERENCES states (id) NOT NULL
);
INSERT INTO users (name, role, age, state) VALUES('CSE','owner',35,3);
INSERT INTO users (name, role, age, state) VALUES('David','customer',33,12);
INSERT INTO users (name, role, age, state) VALUES('Floyd','customer',27,14);
INSERT INTO users (name, role, age, state) VALUES('James','customer',55,1);
INSERT INTO users (name, role, age, state) VALUES('Ross','customer',24,5);


/**table 2: [entity] category**/
CREATE TABLE categories (
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE CHECK (name <> ''),
    description TEXT NOT NULL
);
INSERT INTO categories (name, description) VALUES('Computers','A computer is a general purpose device that can be programmed to carry out a set of arithmetic or logical operations automatically. Since a sequence of operations can be readily changed, the computer can solve more than one kind of problem.');
INSERT INTO categories (name, description) VALUES('Cell Phones','A mobile phone (also known as a cellular phone, cell phone, and a hand phone) is a phone that can make and receive telephone calls over a radio link while moving around a wide geographic area. It does so by connecting to a cellular network provided by a mobile phone operator, allowing access to the public telephone network.');
INSERT INTO categories (name, description) VALUES('Cameras','A camera is an optical instrument that records images that can be stored directly, transmitted to another location, or both. These images may be still photographs or moving images such as videos or movies.');
INSERT INTO categories (name, description) VALUES('Video Games','A video game is an electronic game that involves human interaction with a user interface to generate visual feedback on a video device..');

/**table 3: [entity] product**/
CREATE TABLE products (
    id          SERIAL PRIMARY KEY,
    cid         INTEGER REFERENCES categories (id) NOT NULL,
    name        TEXT NOT NULL,
    SKU         TEXT NOT NULL UNIQUE,
    price       INTEGER NOT NULL
);
INSERT INTO products (cid, name, SKU, price) VALUES(1, 'Apple MacBook',     '103001',   1200); /**1**/
INSERT INTO products (cid, name, SKU, price) VALUES(1, 'HP Laptop',         '106044',   480);
INSERT INTO products (cid, name, SKU, price) VALUES(1, 'Dell Laptop',       '109023',   399);/**3**/
INSERT INTO products (cid, name, SKU, price) VALUES(2, 'Iphone 5s',         '200101',   709);
INSERT INTO products (cid, name, SKU, price) VALUES(2, 'Samsung Galaxy S4', '208809',   488);/**5**/
INSERT INTO products (cid, name, SKU, price) VALUES(2, 'LG Optimus g',       '209937',  375);
INSERT INTO products (cid, name, SKU, price) VALUES(3, 'Sony DSC-RX100M',   '301211',   689);/**7**/
INSERT INTO products (cid, name, SKU, price) VALUES(3, 'Canon EOS Rebel T3',     '304545',  449);
INSERT INTO products (cid, name, SKU, price) VALUES(3, 'Nikon D3100',       '308898',   520);
INSERT INTO products (cid, name, SKU, price) VALUES(4, 'Xbox 360',          '405065',   249);/**10**/
INSERT INTO products (cid, name, SKU, price) VALUES(4, 'Nintendo Wii U ',    '407033',  430);
INSERT INTO products (cid, name, SKU, price) VALUES(4, 'Nintendo Wii',      '408076',   232);

-- should be removed for project 2.
CREATE TABLE cart_history (
    id          SERIAL PRIMARY KEY,
    uid         INTEGER REFERENCES users (id) NOT NULL
);

CREATE TABLE sales (
    id          SERIAL PRIMARY KEY,
    uid         INTEGER REFERENCES users (id) NOT NULL,
    cart_id     INTEGER REFERENCES cart_history (id) NOT NULL, -- should be removed for project 2.
    pid         INTEGER REFERENCES products (id) NOT NULL,
    quantity    INTEGER NOT NULL,
    price       INTEGER NOT NULL
);
