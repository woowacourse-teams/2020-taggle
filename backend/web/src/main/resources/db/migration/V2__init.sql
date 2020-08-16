ALTER TABLE `bookmark`
    ADD
        (
        `description` varchar(255) DEFAULT NULL,
        `title` varchar(255) DEFAULT NULL,
        `image` varchar(255) DEFAULT NULL
        );