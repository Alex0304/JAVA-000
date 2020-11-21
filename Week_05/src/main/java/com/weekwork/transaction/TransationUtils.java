package com.weekwork.transaction;

import cn.hutool.core.thread.threadlocal.NamedThreadLocal;

import java.sql.Connection;

public class TransationUtils {

    private static final ThreadLocal<Connection> CONN = new NamedThreadLocal("DB_CONNECTION");

    public static void put(Connection connection){
        CONN.set(connection);
    }

    public static void remove(){
        CONN.remove();
    }

    public static Connection get(){
        return CONN.get();
    }
}
