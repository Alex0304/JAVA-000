package com.weekwork;


import cn.hutool.core.thread.threadlocal.NamedThreadLocal;
import com.weekwork.dynamic.SqlOpreateTypeEnum;

/**
 * 保存当前线程的sql操作类型
 */
public class GlobalSqlOpreateTypeHolder {

    private static final ThreadLocal<SqlOpreateTypeEnum> SQL_OPREATE_TYPE_ENUM_THREAD_LOCAL = new NamedThreadLocal<>("SQL_OPERATE_TYPE");

    public static void put(SqlOpreateTypeEnum sqlOpreateType) {
        SQL_OPREATE_TYPE_ENUM_THREAD_LOCAL.set(sqlOpreateType);
    }

    public static SqlOpreateTypeEnum get() {
        return SQL_OPREATE_TYPE_ENUM_THREAD_LOCAL.get();
    }

    public static void clear() {
        SQL_OPREATE_TYPE_ENUM_THREAD_LOCAL.remove();
    }
}
