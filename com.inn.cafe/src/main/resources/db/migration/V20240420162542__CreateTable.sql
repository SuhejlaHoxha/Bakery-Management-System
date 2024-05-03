CREATE TABLE if not exists user
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contactNumber VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    role VARCHAR(255)
);

 CREATE TABLE if not exists `category` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `name` varchar(255) DEFAULT NULL,
);

CREATE TABLE if not exists `product` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `category_fk` int NOT NULL,
  FOREIGN KEY (`category_fk`) REFERENCES `category` (`id`)
);