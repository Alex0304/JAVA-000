package com.weekwork.common;


import com.weekwork.dynamic.aop.Read;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

//@Service
public class UserServiceImpl implements UserService {
    private static final String QUERY_SQL = "SELECT username from t_user where id = ?";

    private static final String INSERT_SQL = "INSERT INTO t_user(username,password) values(?,?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Read
    public User queryUserById(int id) {
        return jdbcTemplate.queryForObject(QUERY_SQL, new Object[]{1}, User.class);
    }

    @Override
    public int saveUser(User user) {
        return jdbcTemplate.update(INSERT_SQL, new Object[]{user.getUserName(), user.getPwd()});
    }
}
