CREATE DATABASE  IF NOT EXISTS `bookstore`;
USE `bookstore`;


CREATE TABLE `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45),
	`last_name` VARCHAR(45),
	`email` VARCHAR(45),
    `password` VARCHAR(255),
	PRIMARY KEY (`id`)
);

CREATE TABLE `roles`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(45),
	PRIMARY KEY (`id`)
);

CREATE TABLE `user_role`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT,
	`role_id` INT,
	FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
    PRIMARY KEY (`id`)
);

SELECT * FROM book b WHERE CONCAT(b.book_title, b.author) LIKE '%Blood%';

ALTER TABLE `book`
DROP COLUMN `genre`;

ALTER TABLE `book`
ADD `price` FLOAT;


CREATE TABLE `book` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`book_title` VARCHAR(100),
	`author` VARCHAR(70),
	`description` VARCHAR(500),
	`rating` float(11),
	`number_of_ratings` INT(11),
	`image_url` VARCHAR(255),
	`pages` INT(11),
	`stock` INT(11),
	PRIMARY KEY (`id`)
);
TRUNCATE TABLE `user_book`; /*sterge datale din tabel*/
DROP TABLE `user_book`;
CREATE TABLE `user_book` (
	`user_id` INT(11),
	`book_id` INT(11),
     FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
	 FOREIGN KEY (`book_id`) REFERENCES `book`(`id`),
	`user_rating` float(11),
	`progress_pages` INT(11),
    `book_state` TINYINT,
	 PRIMARY KEY(user_id, book_id)
);

CREATE TABLE `feedback` (
	`user_id` INT,
	`book_id` INT,
     FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
	 FOREIGN KEY (`book_id`) REFERENCES `book`(`id`),
	`rating` FLOAT,
	`comment` VARCHAR(255),
    `date` DATE,
	 PRIMARY KEY(user_id, book_id)
);



CREATE TABLE `genres`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(250),
    PRIMARY KEY (`id`)
);

SELECT g.type FROM genres g
INNER JOIN genres_in_books gib
ON g.id = gib.genre_id
INNER JOIN book b
ON b.id = gib.book_id;

CREATE TABLE `genres_in_books` (
	`genre_id` INT(11),
	`book_id` INT(11),
     FOREIGN KEY (`genre_id`) REFERENCES `genres`(`id`),
	 FOREIGN KEY (`book_id`) REFERENCES `book`(`id`),
	 PRIMARY KEY(genre_id, book_id)
);


SELECT * from book b
INNER JOIN genres_in_books gib
ON b.id = gib.book_id
INNER JOIN genres g
ON gib.genre_id=g.id
WHERE g.type IN("History") AND b.id != 1
LIMIT 4;

