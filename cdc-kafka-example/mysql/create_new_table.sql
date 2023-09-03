create table users
(
    `user_id`    bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `created_at` datetime     NOT NULL,
    `updated_at` datetime     NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;