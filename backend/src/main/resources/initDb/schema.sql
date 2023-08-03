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
