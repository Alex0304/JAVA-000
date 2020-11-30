package com.weekwork;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.mysql.jdbc.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MySqlGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        String projectPath = "D:/code/JAVA-000";
        DataSourceConfig.Builder db = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/gk_mall?useUnicode=true&serverTimezone=GMT&useSSL=true&characterEncoding=utf8","root","root");
        DataSourceConfig dsc = db.driver(Driver.class).build();
        AutoGenerator mpg =  new AutoGenerator(dsc);
        GlobalConfig.Builder gb = new GlobalConfig.Builder();
        GlobalConfig gbc = gb.author("chenhuan")
                .swagger2(true)
                .outputDir(projectPath + "/code-generator/src/main/java")
                .openDir(false).build();
        PackageConfig.Builder pb = new PackageConfig.Builder();
        PackageConfig pbc = pb.parent("com.weekwork.mall").entity("entity").mapper("mapper").controller("controller").service("service").serviceImpl("impl").build();
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "D:/code/JAVA-000/code-generator/src/main/resources/mapper/" + pbc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.addFileOutConfig(focList);
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        StrategyConfig strategy = builder.addFieldPrefix("f")
                .addTablePrefix("t_")
                .addInclude(scanner("表名，多个英文逗号分割").split(","))
                .entityBuilder().naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .superClass(BaseEntity.class)
                .addSuperEntityColumns("id")
                .booleanColumnRemoveIsPrefix(true)
                .lombok(true)
                .controllerBuilder().superClass(BaseController.class)
                .hyphenStyle(true).build();
        strategy.entityBuilder().nameConvert(new MyNameConvert(strategy));
        TemplateConfig.Builder tcb = new TemplateConfig.Builder().all();
        TemplateConfig tc = tcb.mapperXml(null).build();
        mpg.strategy(strategy)
                .injection(cfg)
                .global(gbc)
                .template(tc)
                .packageInfo(pbc)
                .engine(new FreemarkerTemplateEngine());
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.execute();
    }


    /*public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //String projectPath = System.getProperty("user.dir");
        String projectPath = "D:/code/JAVA-000";
        gc.setOutputDir(projectPath + "/code-generator/src/main/java");
        gc.setAuthor("chenhuan");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/gk_mall?useUnicode=true&serverTimezone=GMT&useSSL=true&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("com.weekwork.mall");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/code-generator/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.weekwork.mall.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setSuperControllerClass("com.weekwork.mall.common.BaseController");
        strategy.setInclude(scanner("表名"));
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        strategy.setFieldPrefix("f");
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }*/
}
