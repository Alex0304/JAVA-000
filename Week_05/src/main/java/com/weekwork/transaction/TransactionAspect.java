package com.weekwork.transaction;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;


@Component
@Aspect
public class TransactionAspect {
    

    @Around("@annotation(mt)")
    public Object invokeTransaction(ProceedingJoinPoint pjp, MyTransactional mt){
        Object proceed;
        Connection connection = null;
        try {
            proceed = pjp.proceed();
            connection = TransationUtils.get();
            if(null != connection) {
                connection.commit();
            }
        }catch (Throwable e){
           connection = TransationUtils.get();
            if(null != connection){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        }finally {
            if(null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            TransationUtils.remove();//将本地线程变量的连接释放掉
        }
        return proceed;
    }
}
