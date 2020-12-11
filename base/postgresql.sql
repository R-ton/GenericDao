CREATE DATABASE frame;
CREATE USER framework with password '1234';
GRANT CONNECT ON DATABASE frame TO framework;
ALTER DATABASE frame OWNER TO framework;

psql -U framework -d frame

create table Department(
	id varchar(10),
	name varchar(50)
);


create table Employee(
	id varchar(10),
	name varchar(50),
	salary real,
	dep varchar(10)
);

create sequence EmployeeSequence start with 1;
insert into Employee values(CONCAT('dep ',nextval('EmployeeSequence')),
'Rakoto',2000,'1');
insert into Employee values(CONCAT('dep ',nextval('EmployeeSequence')),
'Rabe',6000,'1');

CREATE SEQUENCE IF NOT EXISTS dep_seq START 1; 

insert into department values (CONCAT('dep ',nextval('dep_seq')),'Reseau');
insert into department values (CONCAT('dep ',nextval('dep_seq')),'Developpement');
insert into department values (CONCAT('dep ',nextval('dep_seq')),'Web');
insert into department values (CONCAT('dep ',nextval('dep_seq')),'Finance');
insert into department values (CONCAT('dep ',nextval('dep_seq')),'Gestion');
insert into department values (CONCAT('dep ',nextval('dep_seq')),'Comptabilite');
insert into department values (CONCAT('dep ',nextval('dep_seq')),'Tresorerie');

SELECT id FROM tb_department where id ='dep 1' OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY group by id;