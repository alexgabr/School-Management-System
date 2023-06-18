--
-- Copyright Alexandru Vrincianu
-- 2023
--

-- DO NOT CHANGE ANYTHING UNLESS YOU'RE ABLE TO CHANGE THE SOURCE CODE TOO!!!

CREATE DATABASE school;

USE school;
SET AUTOCOMMIT = OFF;

CREATE TABLE logsbook(
	log_id INT PRIMARY KEY AUTO_INCREMENT,
    info VARCHAR(150),
    date_time DATETIME NOT NULL
);

CREATE TABLE users(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    acc_type VARCHAR(50) NOT NULL,
    sign_up_date DATETIME
);

CREATE TABLE students(
	student_id INT,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    class_year VARCHAR(10) NOT NULL,
    global_average DECIMAL(2,2),
    admision_date DATE ,
    FOREIGN KEY(student_id) REFERENCES users(user_id)
);

CREATE TABLE teachers(
	teacher_id INT,
    first_name VARCHAR(80),
    last_name VARCHAR(80),
    subjects VARCHAR(200)
);

CREATE TABLE staff(
	staff_id INT,
    first_name VARCHAR(80),
    last_name VARCHAR(80),
    position VARCHAR(80)
);

CREATE TABLE teachers_salaries(
	id INT,
    salary_value DECIMAL(5,2)
);

CREATE TABLE staff_salaries(
	id INT,
    salary_value DECIMAL(5,2)
);

CREATE TABLE grades(
	id INT PRIMARY KEY AUTO_INCREMENT,
    grade INT NOT NULL,
    subject_ VARCHAR(80) NOT NULL,
    date_ DATE NOT NULL,
    student INT NOT NULL,
	FOREIGN KEY(student) REFERENCES students(student_id)
);

CREATE TABLE math(
	id INT NOT NULL,
    average INT,
    FOREIGN KEY(id) REFERENCES students(student_id)
);

CREATE TABLE physics(
	id INT NOT NULL,
    average INT,
    FOREIGN KEY(id) REFERENCES students(student_id)
);

CREATE TABLE chemistry(
	id INT,
    average INT,
    FOREIGN KEY(id) REFERENCES students(student_id)
);

COMMIT;