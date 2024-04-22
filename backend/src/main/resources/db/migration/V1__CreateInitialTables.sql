CREATE TABLE users
(
    id         int NOT NULL AUTO_INCREMENT,
    name       varchar(50),
    email       varchar(50),
    password       varchar(100),
    admin           bit DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE emails
(
    id         int NOT NULL AUTO_INCREMENT,
    email       varchar(100),
    PRIMARY KEY (id)
);

INSERT INTO users (`id`, `name`, `email`, `password`, `admin`) VALUES ('0', 'admin', 'admin@gmail.com', '$2a$10$nXEPSzgwmZ9Ph5IVrfcf.u8/323wFdOm6x1ni8ICuriKhKKFfQbn.', b'1');
