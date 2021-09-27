//package com.kh.wheregarden.web.interceptor;
//
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class LoginCheckInterceptor implements HandlerInterceptor{
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		
//		String redirectUrl = null;
//		
//		// request.getRequestURL() : 호출된 FULL 주소를 가져 온다.  : localhost:7070/home -> localhost:7070/home
//		// request.getRequestURI() : 도메인이후 하부 주소를 가져 온다. : localhost:7070/home -> /home
//		String requestURI = request.getRequestURI();
//		
//		//웹 서버로 요청 시, 요청에 사용된 QueryString을 리턴한다.
//		String queryString = URLEncoder.encode(request.getQueryString(),"UTF-8");
//		
//		if(queryString !=null ) {
//			StringBuffer str = new StringBuffer();
//			str.append(requestURI)
//				 .append("?")
//				 .append(queryString);
//			redirectUrl = str.toString();
//			}
//		else {
//			redirectUrl = requestURI;
//			}
//		
//		log.info("LoginCheckInterceptor.preHandle 실행:{}",redirectUrl);
//		
//		HttpSession session = request.getSession(false);
//		if(session == null || session.getAttribute("loginMember") == null) {
//			log.info("세션과 loginMember가 NULL이므로 로그인필요");
//			response.sendRedirect("/login/loginPage?redirectUrl=" +  redirectUrl);
//			return false;
//		}
//		return true;
//	}
//}
