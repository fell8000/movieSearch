create database mydb;
use mydb;
create table user (
	user_id int primary key,
    age int,
    gender char(5),
    occupation varchar(15),
    zipcode varchar(15)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table genres (
	genres_code int primary key,
    genres_name varchar(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table movie (
	movie_id int primary key,
    movie_title mediumtext,
    release_date date,
	videorelease_date date,
    IMDB_URL varchar(150),
	genres_names LONGTEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table data (
	movie_id int,
	user_id int,
    rating int,
    time_stamp varchar(30),
	PRIMARY KEY(movie_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



drop table genres;
drop table movie;
drop table user;
drop table data;

select * from genres;
select * from data;
SELECT COUNT(*) FROM data;
SELECT * FROM user;
SELECT COUNT(*) FROM genres;
SELECT COUNT(*) FROM movie;


