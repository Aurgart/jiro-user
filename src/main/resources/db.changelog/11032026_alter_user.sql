CREATE TYPE jiro_user.user_role as ENUM ('MANAGER','USER');

alter table jiro_user.user
add column role jiro_user.user_role default 'USER';