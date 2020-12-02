package com.weekwork.rawJdbc.dynamic.datasource;

import cn.hutool.core.map.MapUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

public class DynamicDatasource extends AbstractRoutingDataSource {


    private DataSource masterDatasource;

    private DataSource slaveDatasource;


    /**
     * 从threadlocal线程本地变量中读取当前请求类型，根据请求类型选择主库还是从库
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        if (SqlOpreateTypeEnum.READ.equals(GlobalSqlOpreateTypeHolder.get())) {
            return slaveDatasource;
        }
        return masterDatasource;
    }

    @Override
    public void afterPropertiesSet() {
        super.setDefaultTargetDataSource(masterDatasource);
        HashMap<Object, Object> dataSourceHashMap = MapUtil.newHashMap();
        dataSourceHashMap.put(SqlOpreateTypeEnum.WRITE.name(), masterDatasource);
        dataSourceHashMap.put(SqlOpreateTypeEnum.READ.name(), slaveDatasource);
        super.setTargetDataSources(dataSourceHashMap);
        super.afterPropertiesSet();
    }

    public DataSource getMasterDatasource() {
        return masterDatasource;
    }

    public DynamicDatasource setMasterDatasource(DataSource masterDatasource) {
        this.masterDatasource = masterDatasource;
        return this;
    }

    public DataSource getSlaveDatasource() {
        return slaveDatasource;
    }

    public DynamicDatasource setSlaveDatasource(DataSource slaveDatasource) {
        this.slaveDatasource = slaveDatasource;
        return this;
    }
}
