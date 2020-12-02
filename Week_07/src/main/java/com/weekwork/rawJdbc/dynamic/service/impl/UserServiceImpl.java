package com.weekwork.rawJdbc.dynamic.service.impl;


import com.weekwork.common.User;
import com.weekwork.rawJdbc.dynamic.aop.Read;
import com.weekwork.rawJdbc.dynamic.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    private static final String QUERY_SQL = "SELECT user_name 'userName' from t_user where user_id = ?";

    private static final String INSERT_SQL = "INSERT INTO t_user(user_name,pwd) values(?,?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Read
    public User queryUserById(int id) {
        return jdbcTemplate.queryForObject(QUERY_SQL, new Object[]{id}, User.class);
    }

    @Override
    public int saveUser(User user) {
        return jdbcTemplate.update(INSERT_SQL, new Object[]{user.getUserName(), user.getPwd()});
    }
}
