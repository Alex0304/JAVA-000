package com.weekwork.dynamic.aop;


import com.weekwork.dynamic.datasource.GlobalSqlOpreateTypeHolder;
import com.weekwork.dynamic.datasource.SqlOpreateTypeEnum;
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
