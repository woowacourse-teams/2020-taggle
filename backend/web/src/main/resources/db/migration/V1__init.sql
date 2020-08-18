CREATE TABLE `user`
(
    `user_id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `created_date`  datetime     DEFAULT NULL,
    `modified_date` datetime     DEFAULT NULL,
    `email`         varchar(255) NOT NULL,
    `nick_name`     varchar(255) NOT NULL,
    `phone_number`  varchar(255) DEFAULT NULL,
    `picture`       longtext NOT NULL,
    `role`          varchar(255) NOT NULL,
    `sign_out_date` datetime     DEFAULT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `bookmark`
(
    `bookmark_id` bigint(20)   NOT NULL AUTO_INCREMENT,
    `is_read`     bit(1)       NOT NULL,
    `url`         longtext NOT NULL,
    `user_id`     bigint(20) DEFAULT NULL,
    `description` longtext DEFAULT NULL,
    `title` varchar(255) DEFAULT NULL,
    `image` longtext DEFAULT NULL,
    PRIMARY KEY (`bookmark_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `category`
(
    `category_id` bigint(20)   NOT NULL AUTO_INCREMENT,
    `title`       varchar(255) NOT NULL,
    `user_id`     bigint(20) DEFAULT NULL,
    PRIMARY KEY (`category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `tag`
(
    `tag_id`               bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`                 varchar(255) NOT NULL,
    `category_id` bigint(20) DEFAULT NULL,
    `user_id`              bigint(20) DEFAULT NULL,
    PRIMARY KEY (`tag_id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `tag_bookmark`
(
    `tag_bookmark_id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `bookmark_id` bigint(20) DEFAULT NULL,
    `tag_id`           bigint(20) DEFAULT NULL,
    PRIMARY KEY (`tag_bookmark_id`),
    FOREIGN KEY (`bookmark_id`) REFERENCES `bookmark` (`bookmark_id`),
    FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
);