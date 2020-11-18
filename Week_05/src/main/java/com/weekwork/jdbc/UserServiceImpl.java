package com.weekwork.jdbc;


import cn.hutool.core.collection.CollUtil;
import com.weekwork.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.username}")
    private String username;

    private static final String INSERT_SQL = "insert into t_user(name,password) values(?,?)";

    private static final String QUERY_SQL = "select * from t_user where id = {0}";

    @Override
    public User selectUserById(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            String sql = MessageFormat.format(QUERY_SQL,String.valueOf(id));
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<User> userList = CollUtil.newArrayList();
            while (resultSet.next()){
                User user = new User();
                for (int  i = 1;i<=columnCount;i++){
                    if(metaData.getColumnName(i).equals("id")){
                        user.setId((Integer) resultSet.getObject(i));
                    }else if(metaData.getColumnName(i).equals("name")){
                        user.setName((String) resultSet.getObject(i));
                    }else if(metaData.getColumnName(i).equals("password")){
                        user.setPassword((String) resultSet.getObject(i));
                    }
                }
                userList.add(user);
            }
            return userList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            Optional.ofNullable(resultSet).ifPresent(r-> {
                try {
                    r.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            Optional.ofNullable(preparedStatement).ifPresent(p-> {
                try {
                    p.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            Optional.ofNullable(connection).ifPresent(c-> {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        return null;
    }

    @Override
    public void saveUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.execute();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            Optional.ofNullable(preparedStatement).ifPresent(p-> {
                try {
                    p.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            Optional.ofNullable(connection).ifPresent(c-> {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
