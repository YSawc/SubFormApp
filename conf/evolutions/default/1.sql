# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table follow (
  id                            integer not null,
  follow_id                     integer,
  be_followed_id                integer,
  constraint pk_follow primary key (id)
);
create sequence follow_seq;

create table good (
  id                            integer not null,
  users_id                      integer,
  constraint uq_good_users_id unique (users_id),
  constraint pk_good primary key (id)
);
create sequence good_seq;

create table tweet (
  id                            integer not null,
  mutter                        varchar(255),
  user_id                       integer,
  constraint pk_tweet primary key (id)
);
create sequence tweet_seq;

create table user (
  id                            integer not null,
  name                          varchar(255),
  user_id                       varchar(255),
  password                      varchar(255),
  email                         varchar(255),
  follow_id                     integer,
  constraint uq_user_user_id unique (user_id),
  constraint pk_user primary key (id)
);
create sequence user_seq;

alter table good add constraint fk_good_users_id foreign key (users_id) references user (id) on delete restrict on update restrict;

alter table tweet add constraint fk_tweet_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_tweet_user_id on tweet (user_id);

alter table user add constraint fk_user_follow_id foreign key (follow_id) references follow (id) on delete restrict on update restrict;
create index ix_user_follow_id on user (follow_id);


# --- !Downs

alter table good drop constraint if exists fk_good_users_id;

alter table tweet drop constraint if exists fk_tweet_user_id;
drop index if exists ix_tweet_user_id;

alter table user drop constraint if exists fk_user_follow_id;
drop index if exists ix_user_follow_id;

drop table if exists follow;
drop sequence if exists follow_seq;

drop table if exists good;
drop sequence if exists good_seq;

drop table if exists tweet;
drop sequence if exists tweet_seq;

drop table if exists user;
drop sequence if exists user_seq;

