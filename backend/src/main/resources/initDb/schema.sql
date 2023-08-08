CREATE TABLE IF NOT EXISTS CAR_OPTION
(
    car_option_id    bigint auto_increment,
    category         varchar(20),
    category_detail  varchar(20),
    option_name      varchar(20),
    option_detail    varchar(255),
    price            int,
    photo_url        varchar(255),
    parent_option_id bigint,
    CONSTRAINT pk_option PRIMARY KEY (car_option_id),
    CONSTRAINT fk_option FOREIGN KEY (parent_option_id) REFERENCES CAR_OPTION (car_option_id)
    );

CREATE TABLE IF NOT EXISTS MODEL_PHOTO
(
    model_photo_id bigint auto_increment,
    content        varchar(40),
    photo_svg_url  varchar(255),
    photo_png_url  varchar(255),
    car_option_id  bigint,
    CONSTRAINT pk_model_photo PRIMARY KEY (model_photo_id),
    CONSTRAINT fk_model_photo FOREIGN KEY (car_option_id) REFERENCES CAR_OPTION (car_option_id)
    );

CREATE TABLE IF NOT EXISTS USER_INFO
(
    user_id  bigint auto_increment,
    name     varchar(10),
    email    varchar(30),
    password varchar(16),
    CONSTRAINT pk_user_info PRIMARY KEY (user_id)
    );

CREATE TABLE IF NOT EXISTS CAR_ARCHIVING
(
    archiving_id bigint auto_increment,
    user_id      bigint,
    day_time     datetime,
    is_complete  boolean default false,
    is_alive     boolean default true,
    CONSTRAINT pk_car_archiving PRIMARY KEY (archiving_id),
    CONSTRAINT fk_car_archiving_user_info FOREIGN KEY (user_id) REFERENCES USER_INFO (user_id)
    );

CREATE TABLE IF NOT EXISTS MY_CAR
(
    my_car_id     bigint auto_increment,
    car_option_id bigint,
    archiving_id  bigint,
    CONSTRAINT pk_my_car PRIMARY KEY (my_car_id),
    CONSTRAINT fk_my_car_car_option FOREIGN KEY (car_option_id) REFERENCES CAR_OPTION (car_option_id),
    CONSTRAINT fk_my_car_car_archiving FOREIGN KEY (archiving_id) REFERENCES CAR_ARCHIVING (archiving_id)
);