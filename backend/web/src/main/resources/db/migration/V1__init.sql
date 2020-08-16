CREATE TABLE `user`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `created_date`  datetime     DEFAULT NULL,
    `modified_date` datetime     DEFAULT NULL,
    `email`         varchar(255) NOT NULL,
    `nick_name`     varchar(255) NOT NULL,
    `phone_number`  varchar(255) DEFAULT NULL,
    `picture`       varchar(255) NOT NULL,
    `role`          varchar(255) NOT NULL,
    `sign_out_date` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `bookmark`
(
    `bookmark_id` bigint(20)   NOT NULL AUTO_INCREMENT,
    `is_read`     bit(1)       NOT NULL,
    `url`         varchar(255) NOT NULL,
    `user_id`     bigint(20) DEFAULT NULL,
    PRIMARY KEY (`bookmark_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `category`
(
    `category_id` bigint(20)   NOT NULL AUTO_INCREMENT,
    `title`       varchar(255) NOT NULL,
    `user_id`     bigint(20) DEFAULT NULL,
    PRIMARY KEY (`category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `tag`
(
    `tag_id`               bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`                 varchar(255) NOT NULL,
    `category_category_id` bigint(20) DEFAULT NULL,
    `user_id`              bigint(20) DEFAULT NULL,
    PRIMARY KEY (`tag_id`),
    FOREIGN KEY (`category_category_id`) REFERENCES `category` (`category_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `tag_bookmark`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `bookmark_bookmark_id` bigint(20) DEFAULT NULL,
    `tag_tag_id`           bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`bookmark_bookmark_id`) REFERENCES `bookmark` (`bookmark_id`),
    FOREIGN KEY (`tag_tag_id`) REFERENCES `tag` (`tag_id`)
);