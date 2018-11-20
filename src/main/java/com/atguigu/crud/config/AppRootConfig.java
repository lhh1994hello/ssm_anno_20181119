package com.atguigu.crud.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;

//作者:lhh 创建时间:2018年10月28日 下午8:19:30
/**
 * 注解配置类
 * 
 * @author lhh
 *
 */
@Configuration
@PropertySource("classpath:configs.properties")
@EnableTransactionManagement
@ComponentScan(value = "com.atguigu.crud", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) })
// @MapperScan("com.atguigu.crud.dao")
public class AppRootConfig {
	@Bean
	public PropertiesConfig getPropertiesConfig() {
		return new PropertiesConfig();
	}

	// 配置数据源
	@Bean
	public DruidDataSource getDataSource(@Value("${spring.datasource.url}") String url,
			@Value("${spring.datasource.driver}") String driverClassName,
			@Value("${spring.datasource.user}") String user, @Value("${spring.datasource.password}") String password) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}

	// 配置和Mybatis的整合
	@Order
	@Bean("sqlSessionFactory")
	public SqlSessionFactoryBean getFactory(DataSource dataSource,
			@Value("${mybatis.type.alias.package}") String typeAliasPackage,
			@Value("${mybatis.mapper.location}") String mapperLocation) throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasPackage);// 实体类定义别名
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] mapperLocations = resolver.getResources(mapperLocation);
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		//分页插件
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { pageInterceptor() });
		return sqlSessionFactoryBean;
	}
	
	//分页插件定义
	@Bean
	public PageInterceptor pageInterceptor() {
		PageInterceptor interceptor = new PageInterceptor();
		Properties props = new Properties();
		props.setProperty("helperDialect", "mysql");
		props.setProperty("reasonable", "true");//设置合理化，
		props.setProperty("supportMethodsArguments", "mysql");
		props.setProperty("autoRuntimeDialect", "true");
		interceptor.setProperties(props);
		return interceptor;
	}
 

	// 配置扫描器，将mybatis接口的实现加入到IOC容器中
	@Bean
	public MapperScannerConfigurer getScanner(@Value("${spring.mapperScan.package}") String mapperScanPackage) {
		MapperScannerConfigurer scanner = new MapperScannerConfigurer();
		scanner.setBasePackage("com.atguigu.crud.dao");// 设置接口文件位置
		scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return scanner;
	}

	
	// 事务管理
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		return dataSourceTransactionManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		return propertySourcesPlaceholderConfigurer;
	}
	
}
