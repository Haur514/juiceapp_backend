CREATE TABLE chat (
    id serial primary key,
    chat varchar(140),
    date timestamp with time zone default (now() at time zone 'jp')
);

SET SESSION timezone TO 'Asia/Tokyo';

SELECT * FROM chat;