# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table follow (
  id                            integer auto_increment not null,
  follow_id                     integer,
  be_followed_id                integer,
  constraint pk_follow primary key (id)
);

create table good (
  id                            integer auto_increment not null,
  users_id                      integer,
  constraint uq_good_users_id unique (users_id),
  constraint pk_good primary key (id)
);

create table tweet (
  id                            integer auto_increment not null,
  mutter                        varchar(255),
  user_id                       integer,
  created_date                  datetime(6) not null,
  constraint pk_tweet primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  name                          varchar(255),
  user_id                       varchar(255),
  password                      varchar(255),
  email                         varchar(255),
  private_or                    tinyint(1) default 0,
  follow_id                     integer,
  constraint uq_user_user_id unique (user_id),
  constraint pk_user primary key (id)
);

alter table good add constraint fk_good_users_id foreign key (users_id) references user (id) on delete restrict on update restrict;

alter table tweet add constraint fk_tweet_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_tweet_user_id on tweet (user_id);

alter table user add constraint fk_user_follow_id foreign key (follow_id) references follow (id) on delete restrict on update restrict;
create index ix_user_follow_id on user (follow_id);


# --- !Downs

alter table good drop foreign key fk_good_users_id;

alter table tweet drop foreign key fk_tweet_user_id;
drop index ix_tweet_user_id on tweet;

alter table user drop foreign key fk_user_follow_id;
drop index ix_user_follow_id on user;

drop table if exists follow;

drop table if exists good;

drop table if exists tweet;

drop table if exists user;

