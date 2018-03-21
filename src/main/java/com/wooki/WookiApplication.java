package com.wooki;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableRedisHttpSession
@EnableScheduling    // 定时任务
@EnableCaching
public class WookiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WookiApplication.class);
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(WookiApplication.class).web(true).run(args);
	}

//	/**
//	 * 自定义异常页
//	 */
//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//		return (container -> {
//			//System.out.println("出错");
//			ErrorPage error401Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
//			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//			container.addErrorPages(error401Page, error404Page, error500Page);
//		});
//	}
}
