ALTER TABLE `user`
    CHANGE COLUMN `picture` `picture` VARCHAR(1000) NULL DEFAULT NULL;

ALTER TABLE `bookmark`
    CHANGE COLUMN `url` `url`                 VARCHAR(1000) NULL DEFAULT NULL,
    CHANGE COLUMN `description` `description` VARCHAR(1000) NULL DEFAULT NULL,
    CHANGE COLUMN `title` `title`             VARCHAR(1000) NULL DEFAULT NULL,
    CHANGE COLUMN `image` `image`             VARCHAR(1000) NULL DEFAULT NULL;
