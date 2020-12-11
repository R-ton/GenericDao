create user framework identified by framework;
grant dba to framework;
connect framework/framework;

SELECT table_name FROM user_tables;

create table Employee(
	id VARCHAR(10),
	idDepartment VARCHAR(10),
	name VARCHAR(50)
);

CREATE SEQUENCE dep_seq;

insert into department values (CONCAT('dep ',dep_seq.nextval),'Reseau');
insert into department values (CONCAT('dep ',dep_seq.nextval),'Developpement');
insert into department values (CONCAT('dep ',dep_seq.nextval),'Web');
insert into department values (CONCAT('dep ',dep_seq.nextval),'Finance');
insert into department values (CONCAT('dep ',dep_seq.nextval),'Gestion');
insert into department values (CONCAT('dep ',dep_seq.nextval),'Comptabilite');
insert into department values (CONCAT('dep ',dep_seq.nextval),'Tresorerie');

select * from departement  where rownum >=1 and rownum<=10 order by id;