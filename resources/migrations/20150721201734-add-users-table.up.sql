CREATE TABLE users
(id VARCHAR(20) PRIMARY KEY,
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 email VARCHAR(30),
 admin BOOLEAN,
 last_login TIME,
 is_active BOOLEAN,
 pass VARCHAR(100));

CREATE TABLE guestbook
(id INTEGER PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(30),
 message VARCHAR(200),
 timestamp TIMESTAMP);
