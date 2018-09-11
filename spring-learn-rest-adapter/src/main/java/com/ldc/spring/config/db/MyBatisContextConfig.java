package com.ldc.spring.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.ldc.spring.core.mybatis.MyBatisEncryptInterceptor;
import com.ldc.spring.core.mybatis.MybatisForceMasterDbInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Properties;

/**
 * created by liudacheng on 2018/9/11.
 */
@Configuration
@EnableConfigurationProperties(DataSourceSettings.class)
public class MyBatisContextConfig {

    @Autowired
    DataSourceSettings dataSourceSettings;

    @Bean(name = "datasource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource datasource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(dataSourceSettings.getDriverClassName());
        druidDataSource.setUrl(dataSourceSettings.getUrl());
        druidDataSource.setUsername(dataSourceSettings.getUsername());
        druidDataSource.setPassword(dataSourceSettings.getPassword());
        druidDataSource.setInitialSize(dataSourceSettings.getInitialSize());
        druidDataSource.setMinIdle(dataSourceSettings.getMinIdle());
        druidDataSource.setMaxActive(dataSourceSettings.getMaxActive());
        druidDataSource.setMaxWait(dataSourceSettings.getMaxWait());
        druidDataSource.setTimeBetweenConnectErrorMillis(dataSourceSettings.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(dataSourceSettings.getMinEvictableIdleTimeMillis());
        druidDataSource.setTestWhileIdle(dataSourceSettings.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(dataSourceSettings.isTestOnBorrow());
        druidDataSource.setTestOnReturn(dataSourceSettings.isTestOnReturn());
        return druidDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

        sqlSessionFactory.setDataSource(datasource());
        Properties properties = new Properties();
        properties.setProperty("logImpl", "STDOUT_LOGGING");
        sqlSessionFactory.setConfigurationProperties(properties);
        setPagePlugin(sqlSessionFactory);

        sqlSessionFactory.setMapperLocations(applicationContext.getResources(dataSourceSettings.getMapperLocations()));
        sqlSessionFactory.setTypeAliasesPackage(dataSourceSettings.getTypeAliasesPackage());
        sqlSessionFactory.setConfiguration(configuration());
        return sqlSessionFactory.getObject();
    }

    private void setPagePlugin(SqlSessionFactoryBean sqlSessionFactory) {
        //配置分页插件
        MybatisForceMasterDbInterceptor mybatisForceMasterDbInterceptor = new MybatisForceMasterDbInterceptor();
        MyBatisEncryptInterceptor myBatisEncryptInterceptor = new MyBatisEncryptInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("helperDialect", "mysql");
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("params", "count=countSql");
//        properties.setProperty("rowBoundsWithCount", "true");
//        pageInterceptor.setProperties(properties);
        sqlSessionFactory.setPlugins(new Interceptor[]{mybatisForceMasterDbInterceptor,myBatisEncryptInterceptor});
    }

    @Bean
    public DataSourceTransactionManager mybatisTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(datasource());
        return dataSourceTransactionManager;
    }

    @Bean
    public org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(false);
        configuration.setLazyLoadingEnabled(true);
        configuration.setAggressiveLazyLoading(false);
        configuration.setMultipleResultSetsEnabled(true);
        configuration.setUseColumnLabel(true);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        configuration.setDefaultStatementTimeout(25000);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setCallSettersOnNulls(true);
        return configuration;
    }
}
