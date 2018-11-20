package com.atguigu.crud.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//作者:lhh 创建时间:2018年10月28日 下午8:33:39 
public class AppWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppRootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppServletConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 添加过滤器
	 */
	@Override
	protected Filter[] getServletFilters() {
		// 设置字符编码,放在最前面
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		// 用来支持rest风格的请求
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		hiddenHttpMethodFilter.setMethodParam("/");
		//HttpPutFormContentFilter httpPutFormContentFilter=new HttpPutFormContentFilter();
		//httpPutFormContentFilter.setBeanName("/");
		return new Filter[] { characterEncodingFilter, hiddenHttpMethodFilter };
	}
	
}
