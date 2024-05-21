--liquibase formatted sql

--changeset smakrushin:1

CREATE TABLE `users_contact_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `chat_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_volunteer` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` text,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--changeset smakrushin:2

CREATE TABLE `animal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` float NOT NULL,
  `breed` varchar(255) DEFAULT NULL,
  `cat_or_dog` tinyint(4) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `avatar_id` bigint(20) DEFAULT NULL,
  `chat_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_27ih76jlji502gds1mwo5catc` (`avatar_id`),
  KEY `FK1y7svmg7d3byn8wvpseu6ho1c` (`user_id`),
  CONSTRAINT `FK1y7svmg7d3byn8wvpseu6ho1c` FOREIGN KEY (`user_id`) REFERENCES `users_contact_info` (`id`),
  CONSTRAINT `FKk7o8hp5ssbvvhqtl4d89pfs36` FOREIGN KEY (`avatar_id`) REFERENCES `avatar` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--changeset smakrushin:3

CREATE TABLE `avatar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` varbinary(32600) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `file_size` bigint(20) NOT NULL,
  `media_type` varchar(255) DEFAULT NULL,
  `animal_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h1ulr7m38uquxu31o1dstwabu` (`animal_id`),
  CONSTRAINT `FKafexqe689s3racw5pp2j1p6mr` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--changeset smakrushin:4

CREATE TABLE `pet_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` mediumblob,
  `message_pet` varchar(255) DEFAULT NULL,
  `user_chat_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;



