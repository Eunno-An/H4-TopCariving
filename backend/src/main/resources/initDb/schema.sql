CREATE TABLE IF NOT EXISTS CAR_OPTION
(
    car_option_id    bigint auto_increment,
    category         varchar(20),
    category_detail  varchar(25),
    option_name      varchar(20),
    option_detail    varchar(255),
    price            int,
    photo_url        varchar(255),
    parent_option_id bigint,
    CONSTRAINT pk_car_option PRIMARY KEY (car_option_id),
    CONSTRAINT fk_car_option_to_car_option FOREIGN KEY (parent_option_id) REFERENCES CAR_OPTION (car_option_id)
    );

CREATE TABLE IF NOT EXISTS MODEL_PHOTO
(
    model_photo_id bigint auto_increment,
    content        varchar(40),
    photo_svg_url  varchar(255),
    photo_png_url  varchar(255),
    car_option_id  bigint,
    CONSTRAINT pk_model_photo PRIMARY KEY (model_photo_id),
    CONSTRAINT fk_model_photo_to_car_option FOREIGN KEY (car_option_id) REFERENCES CAR_OPTION (car_option_id)
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
    CONSTRAINT fk_car_archiving_to_user_info FOREIGN KEY (user_id) REFERENCES USER_INFO (user_id)
    );

CREATE TABLE IF NOT EXISTS MY_CAR
(
    my_car_id     bigint auto_increment,
    car_option_id bigint,
    archiving_id  bigint,
    CONSTRAINT pk_my_car PRIMARY KEY (my_car_id),
    CONSTRAINT fk_my_car_to_car_option FOREIGN KEY (car_option_id) REFERENCES CAR_OPTION (car_option_id),
    CONSTRAINT fk_my_car_to_car_archiving FOREIGN KEY (archiving_id) REFERENCES CAR_ARCHIVING (archiving_id)
    );

CREATE TABLE IF NOT EXISTS CAR_REVIEW
(
    car_review_id bigint auto_increment,
    review        text,
    my_car_id     bigint,
    CONSTRAINT pk_car_review PRIMARY KEY (car_review_id),
    CONSTRAINT fk_car_review_to_my_car FOREIGN KEY (my_car_id) REFERENCES MY_CAR (my_car_id)
    );

CREATE TABLE IF NOT EXISTS TAG
(
    tag_id   bigint auto_increment,
    tag_text varchar(25),
    CONSTRAINT pk_tag PRIMARY KEY (tag_id)
    );

CREATE TABLE IF NOT EXISTS TAG_REVIEW
(
    tag_review_id bigint auto_increment,
    tag_id        bigint,
    my_car_id     bigint,
    CONSTRAINT pk_tag_review PRIMARY KEY (tag_review_id),
    CONSTRAINT fk_tag_review_to_tag FOREIGN KEY (tag_id) REFERENCES TAG (tag_id),
    CONSTRAINT fk_tag_review_to_my_car FOREIGN KEY (my_car_id) REFERENCES MY_CAR (my_car_id)
    );

CREATE TABLE IF NOT EXISTS CAR_OPTION_TAG
(
    car_option_tag_id bigint auto_increment,
    tag_id            bigint,
    car_option_id     bigint,
    CONSTRAINT pk_car_option_tag PRIMARY KEY (car_option_tag_id),
    CONSTRAINT fk_car_option_tag_tag FOREIGN KEY (tag_id) REFERENCES TAG (tag_id),
    CONSTRAINT fk_car_option_tag_to_car_option FOREIGN KEY (car_option_id) REFERENCES CAR_OPTION (car_option_id)
    );

CREATE TABLE IF NOT EXISTS ENGINE_DETAIL
(
    engine_detail_id bigint auto_increment,
    max_output       varchar(255),
    max_torque       varchar(255),
    car_option_id    bigint,
    CONSTRAINT pk_engine_detail PRIMARY KEY (engine_detail_id),
    CONSTRAINT fk_engine_detail_to_car_option FOREIGN KEY (car_option_id) REFERENCES CAR_OPTION (car_option_id)
    );

CREATE TABLE IF NOT EXISTS AUTH_INFO
(
    auth_info_id  bigint auto_increment,
    refresh_token varchar(255),
    expired_time  datetime,
    user_id       bigint,
    CONSTRAINT pk_auth_info PRIMARY KEY (auth_info_id),
    CONSTRAINT fk_auth_info_to_user_id FOREIGN KEY (user_id) REFERENCES USER_INFO (user_id)
    );

CREATE TABLE IF NOT EXISTS BOOKMARK
(
    bookmark_id  bigint auto_increment,
    user_id      bigint,
    archiving_id bigint,
    is_alive     boolean default true,
    CONSTRAINT pk_bookmark PRIMARY KEY (bookmark_id),
    CONSTRAINT fk_bookmark_to_user_info FOREIGN KEY (user_id) REFERENCES USER_INFO (user_id),
    CONSTRAINT fk_bookmark_to_archiving FOREIGN KEY (archiving_id) REFERENCES CAR_ARCHIVING (archiving_id)
    );