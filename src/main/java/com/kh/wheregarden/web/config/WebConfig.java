//package com.kh.wheregarden.web.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.kh.wheregarden.web.interceptor.LoginCheckInterceptor;
//
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer{
//
////  기본함수 형식
////	@Override
////	public void addInterceptors(InterceptorRegistry registry) {
////		registry.addInterceptor(new MeasuringInterceptor())
////						.order(1)
////						.addPathPatterns("/**");
////						
////	}
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LoginCheckInterceptor())
//				.order(1)
//				.addPathPatterns("/**")
//				.excludePathPatterns(
//						"/",
//						"/login/**",
//						"/logout",
////						"/board/**",
//						"/members/join",
//						"/help/**",
//						"/css/**",
//						"/js/**",
//						"/img/**",
//						"/webjars/**",
//						"/error/**"
//				);
//	}
//}
