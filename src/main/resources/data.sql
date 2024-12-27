drop table if exists ticket;
drop table if exists account;
drop table if exists userAccount;

create table account (
    accountid serial primary key,  
    -- accountid int primary key GENERATED ALWAYS AS IDENTITY,
    username varchar(255) not null unique,
    password varchar(255),
    occupation varchar(255)
);
create table ticket (
    ticketId serial primary key,
    -- messageid int primary key GENERATED ALWAYS AS IDENTITY,
    postedBy int,
    messageText varchar(255),
    resState varchar(255),
    amount int,
    foreign key (postedBy) references account(accountId)
);
create table userAccount (
    accountid serial primary key,  
    -- accountid int primary key GENERATED ALWAYS AS IDENTITY,
    username varchar(255) not null unique,
    password varchar(255)
);

-- ALTER SEQUENCE message_messageid_seq RESTART;
-- UPDATE message SET messageid = DEFAULT;

insert into account (username, password, occupation) values ('testuser1', 'password', 'employee');
insert into account (username, password, occupation) values ('testuser2', 'password', 'manager');
insert into account (username, password, occupation) values ('testuser3', 'password', 'employee');

-- insert into account (username, password) values ('testuser1', 'password');
insert into userAccount (username, password) values ('testuser1', 'password');
insert into ticket (postedBy, messageText, resState, amount) values (1,'test message 1','pending', 20);
insert into ticket (postedBy, messageText, resState, amount) values (1,'test message 2','pending', 40);
insert into ticket (postedBy, messageText, resState, amount) values (1,'test message 3','approved', 60);
