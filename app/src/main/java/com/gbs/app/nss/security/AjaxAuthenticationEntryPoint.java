package com.gbs.app.nss.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * 로그인되지 않은 사용자의 처리
 */
public class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public AjaxAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        // ajax로 호출된 경우 403에러코드로 리턴하며
        if (isAjax) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Ajax REquest Denied (Session Expired)");
        } else {
        	// 페이지가 변경된 경우에는 302 -> loginFormUrl(200)으로 처리한다.
            super.commence(request, response, authException);
        }
    }
}