CREATE TABLE IF NOT EXISTS CAR_OPTION (
    car_option_id bigint auto_increment,
    category varchar(20),
    category_detail varchar(20),
    option_name varchar(20),
    option_detail varchar(255),
    price int,
    photo_url varchar(255),
    parent_option_id bigint,
    CONSTRAINT pk_option PRIMARY KEY (car_option_id),
    CONSTRAINT fk_option FOREIGN KEY (parent_option_id) REFERENCES CAR_OPTION(car_option_id)
);

CREATE TABLE IF NOT EXISTS MODEL_PHOTO (
    model_photo_id bigint auto_increment,
    content varchar(40),
    photo_svg_url varchar(255),
    photo_png_url varchar(255),
    car_option_id bigint,
    CONSTRAINT pk_model_photo PRIMARY KEY (model_photo_id),
    CONSTRAINT fk_model_photo FOREIGN KEY (car_option_id) REFERENCES CAR_OPTION(car_option_id)
);
