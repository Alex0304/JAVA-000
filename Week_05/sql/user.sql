CREATE TABLE t_user
(
    id int PRIMARY KEY COMMENT '主键id' AUTO_INCREMENT,
    name varchar(20) COMMENT '用户名',
    password varchar(20) COMMENT '用户密码'
);
ALTER TABLE t_user COMMENT = '用户表';

create table `weekwork-test`.t_product
(
  id     int auto_increment
  comment '主键'
    primary key,
  name   varchar(50)   null
  comment '商品名称',
  `desc` varchar(500)  null
  comment '商品描述',
  price  double(11, 2) null
  comment '价格'
)
  comment '商品表';