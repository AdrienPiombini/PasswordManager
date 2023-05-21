DROP database if exists passwordManager;
Create database passwordManager;
use passwordManager;

Create table password(
idPassword int auto_increment,
name varchar(255),
identifier varchar(255) not null,
password varchar(255) not null,
constraint pk_idPassword primary key (idPassword)
);
