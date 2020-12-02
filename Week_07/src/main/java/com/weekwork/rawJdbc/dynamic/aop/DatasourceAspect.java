package com.weekwork.rawJdbc.dynamic.aop;


import com.weekwork.rawJdbc.dynamic.datasource.GlobalSqlOpreateTypeHolder;
import com.weekwork.rawJdbc.dynamic.datasource.SqlOpreateTypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DatasourceAspect {

    @Around("@annotation(read)")
    public Object chooseDatasource(ProceedingJoinPoint pjp, Read read) throws Throwable {
        GlobalSqlOpreateTypeHolder.put(SqlOpreateTypeEnum.READ);
        Object proceed;
        try {
            proceed = pjp.proceed();
        } finally {
            GlobalSqlOpreateTypeHolder.clear();
        }
        return proceed;
    }
}
