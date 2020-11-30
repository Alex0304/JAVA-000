package com.weekwork;

import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Set;

public class MyNameConvert implements INameConvert {
    private final StrategyConfig strategyConfig;

    public MyNameConvert(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    @Override
    public String entityNameConvert(TableInfo tableInfo) {
        return NamingStrategy.capitalFirst(this.processName(tableInfo.getName(), this.strategyConfig.entity().getNaming(), this.strategyConfig.getTablePrefix()));
    }

    @Override
    public String propertyNameConvert(TableField field) {
        return this.processName(field.getName(), this.strategyConfig.entity().getNaming(), this.strategyConfig.getFieldPrefix());
    }

    private String processName(String name, NamingStrategy strategy, Set<String> prefix) {
        String propertyName;
        if (prefix.size() > 0) {
            if (strategy == NamingStrategy.underline_to_camel) {
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if (strategy == NamingStrategy.underline_to_camel) {
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            propertyName = name;
        }

        return propertyName;
    }
}


