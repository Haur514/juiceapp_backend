CREATE TABLE history (
    id serial primary key,
    name varchar(20),
    item varchar(20),
    price integer,
    date varchar(50)
);

SELECT * FROM history;