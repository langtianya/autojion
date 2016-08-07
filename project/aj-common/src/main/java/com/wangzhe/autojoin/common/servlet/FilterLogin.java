
package com.wangzhe.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wangzhe.common.Util;

/**
 * 自定义Filter进行权限控制，cookies登录
 */
public class FilterLogin implements Filter {

	private static final String ADMIN_SESSION_NAME = "__xx.servlet.ADMIN_SESSION_NAME__";
	private static final String USER_SESSION_NAME = "__xx.servlet.USER_SESSION_NAME__";

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = ((HttpServletRequest) servletRequest);

		// 获取请求相关信息
		String requestURI = request.getRequestURI();
		System.out.println("请求getRequestURI:=" + requestURI);// 请求uri
		System.out.println("请求getRequestURL:=" + request.getRequestURL());// 请求url
		System.out.println("请求remoteHost:=" + request.getRemoteHost());// 请求F
		System.out.println("getRemoteAddr:=" + request.getRemoteAddr());// 请求远端地址

		// 获取session
		HttpSession session = request.getSession();

		String cookisEnUserName = null;
		String cookisEnAccountName = null;
		// 获取客户端Cookie
		Cookie[] cookies = request.getCookies();

		// 查找cookies中的用户登录信息
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (ADMIN_SESSION_NAME.equals(cookies[i].getName())) {
				cookisEnUserName = cookies[i].getValue();
				// System.out.println("cookies管理员名：=" +cookies[i].getValue());
			}
			if (USER_SESSION_NAME.equals(cookies[i].getName())) {
				cookisEnAccountName = cookies[i].getValue();
			}
			// 管理员和用户的信息都找到时就不再找了
			if (cookisEnAccountName != null && cookisEnUserName != null) {
				break;
			}
		}

		//编码转换
		/*if (cookisEnAccountName != null) {
			cookisEnAccountName = new String(cookisEnAccountName.getBytes(), "ISO-8859-1");
		}
		if (cookisEnUserName != null) {
			cookisEnUserName = new String(cookisEnUserName.getBytes(), "ISO-8859-1");
		}*/

		String sesionEnUserName = (String) session.getAttribute(ADMIN_SESSION_NAME);
		// 编码抓换
		// if(sesionEnUserName!=null)
		// sesionEnUserName=new String(sesionEnUserName.getBytes(),"UTF-8");

		String sesionEnAccountName = (String) session.getAttribute(USER_SESSION_NAME);
		// 编码抓换
		// if (sesionEnAccountName != null)
		// sesionEnAccountName = new String(sesionEnAccountName.getBytes(),
		// "UTF-8");

		// 获取session中的用户对象
		// Account account = (Account) session.getAttribute("aLogin_");
		// User user = (User) session.getAttribute("login_");

		// 判断权限
		if (requestURI != null && isAllow(requestURI)) {
			// System.out.println("不需要登录就可以访问的URL：" + requestURI);
			filterChain.doFilter(servletRequest, servletResponse);
//			判断cookis的用户信息是否登录
		} else if (sesionEnAccountName != null && cookisEnAccountName != null
				&& cookisEnAccountName.equals(sesionEnAccountName)) {
			// System.out.println("进入了 会员");
			filterChain.doFilter(servletRequest, servletResponse);
		}
		// else if(user == null){
		// HttpServletResponse response = (HttpServletResponse) servletResponse;
		// new AccountAction().doLoginFail(response);
		// }
		else if (sesionEnUserName != null && cookisEnUserName != null && cookisEnUserName.equals(sesionEnUserName)) {
			// System.out.println("进入了管理员");
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			// System.out.println("管理员或者用户登录失败");
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			// 清除session
			session.removeAttribute(ADMIN_SESSION_NAME);
			session.removeAttribute(USER_SESSION_NAME);
			// session.removeAttribute("login_");

			// 设置cookie过期
			for (int i = 0; cookies != null && i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
			if (cookisEnAccountName != null) {
				Util.writeDataToClient("登录失败");
			} else
				// 重定向客户端到登录页面
				response.sendRedirect("login");
		}
		System.out.println("do filter finish");
	}

	/**
	 * 不需要校验就可以访问
	 * 
	 * @param requestURI
	 * @return
	 */
	private boolean isAllow(String requestURI) {
		if (requestURI.endsWith("/")) {
			requestURI = requestURI.substring(0, requestURI.length() - 1);
		}
		// 以下字符串结尾的url不需要校验，这只是一个简单过滤，没有扩展性，需要另外设计
		String requestUrl = requestURI.substring(requestURI.lastIndexOf("/") + 1);
		return requestUrl.equals("accountLoginA") || requestUrl.equals("login") || requestUrl.equals("add")
				|| requestUrl.equals("loginCheck") || requestUrl.equals("addAccountA") || requestUrl.equals("register")
				|| requestUrl.endsWith("registerOut") || requestUrl.equals("addUser") || requestUrl.equals("verifyCode")
				|| requestURI.contains("/js/") || requestURI.contains("/css/") || requestURI.contains("/images/")
				|| requestURI.contains("/img/");

	}

	public void destroy() {

	}
}
