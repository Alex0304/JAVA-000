CREATE TABLE t_user
(
    id int PRIMARY KEY COMMENT '主键id' AUTO_INCREMENT,
    name varchar(20) COMMENT '用户名',
    password varchar(20) COMMENT '用户密码'
);
ALTER TABLE t_user COMMENT = '用户表';