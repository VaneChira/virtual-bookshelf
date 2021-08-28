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
ADD COLUMN `year` INT;

ALTER TABLE `book`
ADD COLUMN`language` VARCHAR(70);


DROP TABLE `book`;

ALTER TABLE `book`
DROP COLUMN `author`;

ALTER TABLE `user_book`
DROP COLUMN `user_rating`;

CREATE TABLE `book` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`book_title` VARCHAR(100),
	`description` VARCHAR(1000),
	`rating` float(11),
	`number_of_ratings` INT(11),
	`image_url` VARCHAR(255),
	`pages` INT(11),
	PRIMARY KEY (`id`)
);
TRUNCATE TABLE `user_book`; /*sterge datale din tabel*/
DROP TABLE `user_book`;
CREATE TABLE `user_book` (
	`user_id` INT(11),
	`book_id` INT(11),
     FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
	 FOREIGN KEY (`book_id`) REFERENCES `book`(`id`),
	`progress_pages` INT(11),
    `book_state` TINYINT,
	 PRIMARY KEY(user_id, book_id)
);

DROP TABLE `feedback`;
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


ALTER TABLE `user_book` DROP FOREIGN KEY `user_book_ibfk_2`;
ALTER TABLE `user_book` ADD CONSTRAINT `user_book_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE;


CREATE TABLE `genres`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(250),
    `description` VARCHAR(1000),
    PRIMARY KEY (`id`)
);

SELECT g.type FROM genres g
INNER JOIN genres_in_books gib
ON g.id = gib.genre_id
INNER JOIN book b
ON b.id = gib.book_id;


DROP TABLE `genres_in_books`;
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

CREATE TABLE `author`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(250),
    `description` VARCHAR(1000),
    PRIMARY KEY (`id`)
);

CREATE TABLE `books_author` (
	`author_id` INT(11),
	`book_id` INT(11),
     FOREIGN KEY (`author_id`) REFERENCES `author`(`id`),
	 FOREIGN KEY (`book_id`) REFERENCES `book`(`id`),
	 PRIMARY KEY(author_id, book_id)
);

SELECT book_id from user_book ub
INNER JOIN book b
ON ub.book_id = b.id
WHERE ub.book_state is null AND ub.user_id=1
LIMIT 4;
