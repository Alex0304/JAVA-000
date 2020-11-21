package com.weekwork.proxy;

import cn.hutool.core.collection.CollUtil;

import javax.sql.DataSource;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * product mapper 接口的代理类
 */
public class ProductDaoProxy implements InvocationHandler {

    private DataSource dataSource;

    public ProductDaoProxy(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /**
         *1.从数据源处获取连接
         * 2.获取dao 接口对应方法上的sql语句
         * 3.执行sql
         */
        Connection connection = null;
        boolean queryList = false;
        ResultSet resultSet = null;
        Statement statement = null;
        ArrayList<Object> resultList = CollUtil.newArrayList();
        try {
            connection = dataSource.getConnection();
            MySelect annotation = method.getAnnotation(MySelect.class);
            Class<?> returnType = method.getReturnType();
            if (returnType .isAssignableFrom(List.class)) {
                Type genericReturnType = method.getGenericReturnType();
                String typeName = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0].getTypeName();
                returnType = Class.forName(typeName);
                queryList = true;
            }
            String sql = annotation.value();
            if(args.length>0){
                sql = MessageFormat.format(sql,args);
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Object o = returnType.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Field field = returnType.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(o, resultSet.getObject(i));
                }
                resultList.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != resultSet){
                resultSet.close();
            }
            if(null != statement){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        }
        return queryList ? resultList : resultList.get(0);
    }
}
