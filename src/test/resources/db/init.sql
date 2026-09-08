-- Schema + seed data for integration tests.
-- Loaded once into the Testcontainers MySQL instance via MySQLContainer.withInitScript(...).
-- The DDL mirrors db_export/dataexport.sql (the production schema); the data is a small,
-- deterministic fixture sized for the assertions in the test suite.

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `genres_in_books`;
DROP TABLE IF EXISTS `books_author`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user_book`;
DROP TABLE IF EXISTS `feedback`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `author` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_title` varchar(100) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `number_of_ratings` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `pages` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  `language` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `genres` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(250) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `books_author` (
  `author_id` int NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`author_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `books_author_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  CONSTRAINT `books_author_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `feedback` (
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `rating` int DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`user_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `genres_in_books` (
  `genre_id` int NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`genre_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `genres_in_books_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
  CONSTRAINT `genres_in_books_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_book` (
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `progress_pages` int DEFAULT NULL,
  `book_state` tinyint DEFAULT NULL,
  PRIMARY KEY (`user_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `user_book_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_book_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------------------------
-- Seed data
--
-- Kept intentionally small. The BookProgress / Feedback "create" tests assert
-- that saving a row for (user 1, book 1) increases the row count by one, so
-- there must be NO user_book or feedback row for (1, 1) in the fixture.
-- --------------------------------------------------------------------------

INSERT INTO `roles` (`id`, `name`) VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_ADMIN');

INSERT INTO `user` (`id`, `name`, `last_name`, `email`, `password`) VALUES
  (1, 'Vanessa', 'Chira', 'vanechira@gmail.com', 'password'),
  (2, 'Anne', 'Smith', 'anne@example.com', 'password');

INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES
  (1, 1, 2),
  (2, 2, 1);

INSERT INTO `genres` (`id`, `type`, `description`) VALUES
  (1, 'History', 'Books about historical events and figures.'),
  (2, 'Fiction', 'Narrative works invented by the author.'),
  (3, 'Fantasy', 'Fiction featuring magic and imagined worlds.');

INSERT INTO `book` (`id`, `book_title`, `description`, `rating`, `number_of_ratings`, `image_url`, `pages`, `year`, `language`) VALUES
  (1, 'Origin', 'Robert Langdon travels to Spain to witness a discovery.', 4, 3, NULL, 480, 2017, 'English'),
  (2, 'The Silk Roads', 'A new history of the world seen from the East.', 5, 2, NULL, 636, 2015, 'English'),
  (3, 'Mythos', 'A retelling of the Greek myths.', 5, 1, NULL, 416, 2017, 'English');

INSERT INTO `author` (`id`, `name`, `description`) VALUES
  (1, 'Dan Brown', 'Author of Origin and other thrillers.'),
  (2, 'Peter Frankopan', 'Historian and author of The Silk Roads.'),
  (3, 'Stephen Fry', 'Writer, actor, and author of Mythos.');

INSERT INTO `books_author` (`author_id`, `book_id`) VALUES
  (1, 1),
  (2, 2),
  (3, 3);

INSERT INTO `genres_in_books` (`genre_id`, `book_id`) VALUES
  (2, 1),
  (1, 2),
  (3, 3);

-- user_book: only rows that are NOT (1, 1) so the "add to wishlist / currently
-- reading" tests observe a clean insert for (user 1, book 1).
INSERT INTO `user_book` (`user_id`, `book_id`, `progress_pages`, `book_state`) VALUES
  (1, 2, 120, 2),
  (1, 3, 0, 1),
  (2, 1, NULL, 3);

-- feedback: only rows that are NOT (1, 1) for the same reason.
INSERT INTO `feedback` (`user_id`, `book_id`, `rating`, `comment`, `date`) VALUES
  (2, 1, 5, 'Loved it', '2021-08-29'),
  (1, 3, 4, 'Very good', '2021-08-21');

SET FOREIGN_KEY_CHECKS = 1;
