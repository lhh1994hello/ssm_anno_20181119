package com.atguigu.crud.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

//作者:lhh 创建时间:2018年10月28日 下午8:28:18 
/**
 * 
 * @author lhh 相当于SpringMvc的配置文件
 */
@Configuration
@ComponentScan("com.atguigu.crud")
@PropertySource("classpath:configs.properties")
@EnableWebMvc
@ComponentScan(value = "com.atguigu.crud", includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {
				Controller.class }) }, useDefaultFilters = false)
public class AppServletConfig extends WebMvcConfigurerAdapter {
	@Value("${spring.web.view.prefix}")
	private String springWebViewPrefix;
	@Value("${spring.web.view.suffix}")
	private String springWebViewSuffix;

	@Value("${spring.web.static.handler}")
	private String springWebStaticHandler;
	@Value("${spring.web.static.resource}")
	private String springWebStaticResource;
	@Value("${spring.web.static.cache.period}")
	private Integer springWebStaticCachePeriod;

	// 配置视图解析器
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		super.configureViewResolvers(registry);
		registry.jsp(springWebViewPrefix, springWebViewSuffix);
	}

	// 设置错误统一跳转的视图
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		Properties properties = new Properties();
		properties.getProperty("java.lang.Exception", "error");
		simpleMappingExceptionResolver.setExceptionMappings(properties);
		return simpleMappingExceptionResolver;
	}

	// 添加静态资源
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(springWebStaticHandler).addResourceLocations(springWebStaticResource)
				.setCachePeriod(springWebStaticCachePeriod);
	}

	// 添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
	}

	// 配置静态资源的处理
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		super.configureDefaultServletHandling(configurer);
	}
}
