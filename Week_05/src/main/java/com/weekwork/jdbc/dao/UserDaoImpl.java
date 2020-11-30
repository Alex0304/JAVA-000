package com.weekwork.jdbc.dao;


import cn.hutool.core.collection.CollUtil;
import com.weekwork.entity.User;
import com.weekwork.transaction.TransationUtils;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Service
public class UserDaoImpl implements UserDao {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.username}")
    private String username;

    @Autowired
    private HikariDataSource dataSource;

    private static final String INSERT_SQL = "insert into t_user(name,password) values(?,?)";

    private static final String QUERY_SQL = "select * from t_user where id = {0}";
    private static final String DELETE_SQL = "delete from t_user where id = ?";
    private static final String UPDATE_SQL = "update t_user set password = ? where id = ?";

    @Override
    public User selectUserById(Integer id) {
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
           // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            String sql = MessageFormat.format(QUERY_SQL,String.valueOf(id));
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery(sql);
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
    public int saveUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            return preparedStatement.executeUpdate();
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
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
           // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setString(1,String.valueOf(id));
            return preparedStatement.executeUpdate();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            if(null != preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public int updateUserById(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1,user.getPassword());
            preparedStatement.setString(2,String.valueOf(user.getId()));
            return preparedStatement.executeUpdate();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            if(null != preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    @Override
    public int updateUserInfo(User user) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            connection = TransationUtils.get();
            if(null == connection){
                connection = dataSource.getConnection();
                TransationUtils.put(connection);
            }
            //设置手动提交
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setString(1,user.getPassword());
            preparedStatement.setString(2,String.valueOf(user.getId()));
            result = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
}
