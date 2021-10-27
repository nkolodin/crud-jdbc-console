drop table if exists university_groups, students, courses, student_courses
drop sequence if exists group_seq, student_seq, course_seq
create sequence group_seq start 1
create table university_groups(id bigint not null default (nextval('group_seq')),group_name varchar(10))							
create sequence student_seq start 1
create table students (student_id bigint not null default (nextval('student_seq')) ,groupd_id bigint,first_name varchar(20),last_name varchar(20), primary key(student_id))									
create sequence course_seq start 1
create table courses (course_id bigint not null default(nextval('course_seq')),course_name varchar(10),course_description varchar(35), primary key(course_id))
create table student_courses (student_id bigint, course_id bigint , constraint fk_course foreign key(course_id) references courses(course_id) on delete CASCADE, constraint fk_student foreign key(student_id) references students(student_id) on delete CASCADE)