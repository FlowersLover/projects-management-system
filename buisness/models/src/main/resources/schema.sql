CREATE TABLE "employee"
(
    "id"       uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    firstname  varchar(50)  NOT NULL,
    "lastname" varchar(50)  NOT NULL,
    "middlename" varchar(50),
    "account"  varchar(50) UNIQUE,
    "status"   varchar(30)  NOT NULL,
    "position" varchar(50),
    "email"    varchar(150),
    "password" varchar(250) NOT NULL

);

insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Zelig', 'Egginson', null, null, null, 'ACTIVE', 'Analyst Programmer', 'H4o89Espd6p');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Kacey', 'Clowes', null, null, 'khamstead1', 'ACTIVE', 'Staff Scientist', 'IIkKuT');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Merrile', 'Duligall', 'Lemary', 'mlemary2@skype.com', 'mlemary2', 'ACTIVE', 'Executive Secretary',
        'cQv9DxP2o4H');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Merrile', 'Duligall', 'Lemary', 'mlemary2@skype.com', 'mlemary21', 'ACTIVE', 'Executive Secretary',
        'cQv9DxP2o4H');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Archy', 'Chimes', null, null, 'avenart3', 'ACTIVE', 'Administrative Assistant II', 'rD0K0KmTz');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Marcelle', 'Martinolli', null, null, 'mkitson4', 'ACTIVE', 'Registered Nurse', 'oxL6SDWG34B');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Roxanne', 'Lipman', 'Gianuzzi', 'rgianuzzi5@tumblr.com', 'rgianuzzi5', 'BLOCKED', 'Social Worker', 'f9cs2P3w');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Worth', 'Douty', 'Garlett', 'wgarlett6@msu.edu', null, 'BLOCKED', 'Software Engineer IV', 'ZJMDDQ4');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Jozef', 'Simpkiss', 'Boothman', 'jboothman7@webmd.com', 'jboothman7', 'BLOCKED', 'Senior Developer',
        'Ahk5z0hXWXc');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Kristopher', 'Banaszewski', 'Plues', 'kplues8@naver.com', 'kplues8', 'ACTIVE', 'Teacher', 'K2JfrdhMlCQ');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Kylynn', 'Cramp', 'La Vigne', 'klavigne9@weibo.com', 'klavigne9', 'ACTIVE', 'Data Coordinator', 'md2h6wWXoD');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Lambert', 'Elia', null, null, null, 'ACTIVE', 'Software Test Engineer II', 'H0xkamXymTPQ');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Henrieta', 'Nabarro', 'Suckling', 'hsucklingb@ask.com', 'hsucklingb', 'BLOCKED', 'Staff Accountant III',
        'Wyhpy9MfUV8n');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Abbey', 'Schuck', 'Blue', 'abluec@desdev.cn', null, 'ACTIVE', 'Assistant Manager', 'epb1X86A');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Shaine', 'Guilloneau', 'Piwall', 'spiwalld@yale.edu', 'spiwalld', 'BLOCKED', 'Information Systems Manager',
        'rJanSS');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Joana', 'Gilbert', 'Pennacci', 'jpennaccie@mtv.com', 'jpennaccie', 'ACTIVE', 'Financial Advisor',
        'cEVV6eqXXpEC');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Missie', 'Faughnan', null, null, 'msalamonf', 'BLOCKED', 'Software Engineer IV', 'VnIfZn9g');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Rolland', 'Whiteoak', null, null, 'rhalpineg', 'BLOCKED', 'Database Administrator III', 'Ld4gUV');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Constantia', 'Rosi', null, null, null, 'ACTIVE', 'Financial Advisor', 'b59uoCeG1qPR');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Gabriele', 'Marciek', 'Nanuccioi', 'gnanuccioii@feedburner.com', 'gnanuccioii', 'BLOCKED', 'Pharmacist',
        'AKxz0lIu');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Alexine', 'Neaverson', null, null, 'aclimsonj', 'ACTIVE', 'Associate Professor', 'gFlir4X');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Gaby', 'Woodfine', null, null, null, 'ACTIVE', 'Quality Engineer', 'Fe9ab8CESiuw');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Melba', 'Fairholm', 'Roz', 'mrozl@histats.com', 'mrozl', 'BLOCKED', 'Web Developer I', 'iFhaCDmvYELZ');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Sebastien', 'Barke', 'Agglione', 'sagglionem@google.co.uk', 'sagglionem', 'BLOCKED', 'Quality Engineer',
        'sSvrrO1EKu');
insert into employee (firstname, lastname, middlename, email, account, status, position, password)
values ('Rosalyn', 'Klasing', null, null, null, 'ACTIVE', 'VP Marketing', 'tKKT0WChIX');

CREATE TABLE "project"
(
    "id"             uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    "project_name"   varchar(50) NOT NULL,
    "description"    varchar(150),
    "project_status" varchar(20) NOT NULL
);

insert into project (project_name, description, project_status)
values ('pellentesque', null, 'TEST');
insert into project (project_name, description, project_status)
values ('dignissim',
        'sagittis sapien cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus etiam',
        'DEVELOP');
insert into project (project_name, description, project_status)
values ('donec', 'vel est donec odio justo sollicitudin ut', 'DRAFT');
insert into project (project_name, description, project_status)
values ('quisque', 'in leo maecenas pulvinar lobortis est phasellus sit amet erat nulla tempus vivamus in felis',
        'DEVELOP');

CREATE TABLE "team"
(
    "project" uuid,
    "member"  uuid,
    "role"    varchar(50) NOT NULL,
    PRIMARY KEY (project, member),
    FOREIGN KEY (project) REFERENCES project (id),
    FOREIGN KEY (member) REFERENCES employee (id)
);

insert into team (project, role, member)
values ((SELECT id FROM project WHERE project_name = 'pellentesque'), 'TESTER',
        (SELECT id FROM employee WHERE firstname = 'Zelig'));
insert into team (project, role, member)
values ((SELECT id FROM project WHERE project_name = 'pellentesque'), 'TEAMLEAD',
        (SELECT id FROM employee WHERE firstname = 'Kacey'));
insert into team (project, role, member)
values ((SELECT id FROM project WHERE project_name = 'pellentesque'), 'DEVELOPER',
        (SELECT id FROM employee WHERE account = 'mlemary21'));
insert into team (project, role, member)
values ((SELECT id FROM project WHERE project_name = 'pellentesque'), 'ANALYST',
        (SELECT id FROM employee WHERE firstname = 'Archy'));
insert into team (project, role, member)
values ((SELECT id FROM project WHERE project_name = 'pellentesque'), 'DEVELOPER',
        (SELECT id FROM employee WHERE firstname = 'Marcelle'));


CREATE TABLE "task"
(
    "id"                    uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    "task_name"             varchar(50) NOT NULL,
    "description"           varchar(150),
    "executor"              uuid,
    "hours_to_comlete_task" integer     NOT NULL,
    "author"                uuid        NOT NULL,
    "deadline"              timestamp   NOT NULL,
    "last_changed"          timestamp,
    "created_at"            timestamp        DEFAULT LOCALTIMESTAMP,
    "status"                varchar(20) NOT NULL,
    FOREIGN KEY (author) REFERENCES employee (id),
    FOREIGN KEY (executor) REFERENCES employee (id)
);

insert into task (author, task_name, description, hours_to_comlete_task, deadline, last_changed, status)
values ((SELECT id FROM employee WHERE firstname = 'Roxanne'), 'cursus', null, 84, '2024-03-19 20:37:13',
        '2024-01-05 22:11:51', 'NEW');
insert into task (author, task_name, description, hours_to_comlete_task, deadline, last_changed, status)
values ((SELECT id FROM employee WHERE firstname = 'Worth'), 'posuere',
        'massa id nisl venenatis lacinia aenean sit amet justo morbi ut odio cras mi pede malesuada', 7,
        '2023-09-28 17:38:48', '2022-09-13 22:01:20', 'CLOSED');
insert into task (author, task_name, description, hours_to_comlete_task, deadline, last_changed, status)
values ((SELECT id FROM employee WHERE firstname = 'Jozef'), 'ipsum',
        'vel nisl duis ac nibh fusce lacus purus aliquet at feugiat non pretium quis lectus suspendisse potenti', 11,
        '2023-02-25 01:44:06', '2023-12-31 19:32:32', 'NEW');

insert into task (author, executor, task_name, description, hours_to_comlete_task, deadline, last_changed, status)
values ((SELECT id FROM employee WHERE firstname = 'Rosalyn'), (SELECT id FROM employee WHERE firstname = 'Kacey'),
        'tristique',
        'id massa id nisl venenatis lacinia aenean sit amet justo morbi ut odio', 66, '2024-04-04 10:37:02',
        '2022-06-02 12:09:24', 'CLOSED');
insert into task (author, executor, task_name, description, hours_to_comlete_task, deadline, last_changed, status)
values ((SELECT id FROM employee WHERE firstname = 'Melba'), (SELECT id FROM employee WHERE firstname = 'Archy'),
        'nisl', null, 82, '2023-05-19 11:46:30',
        '2023-10-04 01:22:49', 'NEW');
insert into task (author, executor, task_name, description, hours_to_comlete_task, deadline, last_changed, status)
values ((SELECT id FROM employee WHERE firstname = 'Melba'), (SELECT id FROM employee WHERE firstname = 'Zelig'),
        'purus',
        'ante vivamus tortor duis mattis egestas metus aenean fermentum donec ut mauris eget massa tempor convallis nulla',
        68, '2023-12-15 00:40:50', '2023-08-24 21:04:22', 'CLOSED');


Select *
from project
where project_status = 'DEVELOP';
Select firstname, lastname, middlename, email
from employee;
SELECT * from project;
SELECT * from team;
SELECT * from employee;


