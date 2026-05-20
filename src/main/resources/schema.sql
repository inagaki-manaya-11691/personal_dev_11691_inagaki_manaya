-- 各種テーブル削除
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS foods;
DROP TABLE IF EXISTS losts;
DROP TABLE IF EXISTS buys;

-- categories（カテゴリー）
CREATE TABLE categories (
id SERIAL PRIMARY KEY,
name VARCHAR(20)
);

-- users（ユーザー）
CREATE TABLE users (
id SERIAL PRIMARY KEY,
email VARCHAR(255),
name VARCHAR(20),
password VARCHAR(50)
);

-- foods(食材)
CREATE TABLE foods (
id SERIAL PRIMARY KEY,
category_id INTEGER,
users_id INTEGER,
foods_name VARCHAR(20),
limits BOOLEAN,
createAt TIMESTAMP,
quantity INTEGER,
limits_date DATE
);

--losts(消費)
CREATE TABLE losts(
id SERIAL PRIMARY KEY,
foods_id INTEGER,
users_id INTEGER,
lostquantity INTEGER,
lostdate TIMESTAMP
);

--buys(買い物)
CREATE TABLE buys(
id SERIAL PRIMARY KEY,
foods_id INTEGER,
users_id INTEGER,
buyquantity INTEGER,
buydate TIMESTAMP,
memo TEXT,
buyAt TIMESTAMP
);
