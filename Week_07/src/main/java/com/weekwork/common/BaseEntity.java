package com.weekwork.common;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity<T> implements Serializable {

    private T id;

    private Date createTime;

    private Date updateTime;

    public T getId() {
        return id;
    }

    public BaseEntity<T> setId(T id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public BaseEntity<T> setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public BaseEntity<T> setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
