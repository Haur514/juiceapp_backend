CREATE TABLE history (
    id serial primary key,
    name varchar(20),
    item varchar(20),
    price integer,
    timestamp with time zone
);

SELECT * FROM history;