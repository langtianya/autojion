
package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.servlet;

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

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.Util;

/**
 * �Զ���Filter����Ȩ�޿��ƣ�cookies��¼
 */
public class FilterLogin implements Filter {

	private static final String ADMIN_SESSION_NAME = "__xx.servlet.ADMIN_SESSION_NAME__";
	private static final String USER_SESSION_NAME = "__xx.servlet.USER_SESSION_NAME__";

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = ((HttpServletRequest) servletRequest);

		// ��ȡ���������Ϣ
		String requestURI = request.getRequestURI();
		System.out.println("����getRequestURI:=" + requestURI);// ����uri
		System.out.println("����getRequestURL:=" + request.getRequestURL());// ����url
		System.out.println("����remoteHost:=" + request.getRemoteHost());// ����F
		System.out.println("getRemoteAddr:=" + request.getRemoteAddr());// ����Զ�˵�ַ

		// ��ȡsession
		HttpSession session = request.getSession();

		String cookisEnUserName = null;
		String cookisEnAccountName = null;
		// ��ȡ�ͻ���Cookie
		Cookie[] cookies = request.getCookies();

		// ����cookies�е��û���¼��Ϣ
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (ADMIN_SESSION_NAME.equals(cookies[i].getName())) {
				cookisEnUserName = cookies[i].getValue();
				// System.out.println("cookies����Ա����=" +cookies[i].getValue());
			}
			if (USER_SESSION_NAME.equals(cookies[i].getName())) {
				cookisEnAccountName = cookies[i].getValue();
			}
			// ����Ա���û�����Ϣ���ҵ�ʱ�Ͳ�������
			if (cookisEnAccountName != null && cookisEnUserName != null) {
				break;
			}
		}

		//����ת��
		/*if (cookisEnAccountName != null) {
			cookisEnAccountName = new String(cookisEnAccountName.getBytes(), "ISO-8859-1");
		}
		if (cookisEnUserName != null) {
			cookisEnUserName = new String(cookisEnUserName.getBytes(), "ISO-8859-1");
		}*/

		String sesionEnUserName = (String) session.getAttribute(ADMIN_SESSION_NAME);
		// ����ץ��
		// if(sesionEnUserName!=null)
		// sesionEnUserName=new String(sesionEnUserName.getBytes(),"UTF-8");

		String sesionEnAccountName = (String) session.getAttribute(USER_SESSION_NAME);
		// ����ץ��
		// if (sesionEnAccountName != null)
		// sesionEnAccountName = new String(sesionEnAccountName.getBytes(),
		// "UTF-8");

		// ��ȡsession�е��û�����
		// Account account = (Account) session.getAttribute("aLogin_");
		// User user = (User) session.getAttribute("login_");

		// �ж�Ȩ��
		if (requestURI != null && isAllow(requestURI)) {
			// System.out.println("����Ҫ��¼�Ϳ��Է��ʵ�URL��" + requestURI);
			filterChain.doFilter(servletRequest, servletResponse);
//			�ж�cookis���û���Ϣ�Ƿ��¼
		} else if (sesionEnAccountName != null && cookisEnAccountName != null
				&& cookisEnAccountName.equals(sesionEnAccountName)) {
			// System.out.println("������ ��Ա");
			filterChain.doFilter(servletRequest, servletResponse);
		}
		// else if(user == null){
		// HttpServletResponse response = (HttpServletResponse) servletResponse;
		// new AccountAction().doLoginFail(response);
		// }
		else if (sesionEnUserName != null && cookisEnUserName != null && cookisEnUserName.equals(sesionEnUserName)) {
			// System.out.println("�����˹���Ա");
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			// System.out.println("����Ա�����û���¼ʧ��");
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			// ���session
			session.removeAttribute(ADMIN_SESSION_NAME);
			session.removeAttribute(USER_SESSION_NAME);
			// session.removeAttribute("login_");

			// ����cookie����
			for (int i = 0; cookies != null && i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
			if (cookisEnAccountName != null) {
				Util.writeDataToClient("��¼ʧ��");
			} else
				// �ض���ͻ��˵���¼ҳ��
				response.sendRedirect("login");
		}
		System.out.println("do filter finish");
	}

	/**
	 * ����ҪУ��Ϳ��Է���
	 * 
	 * @param requestURI
	 * @return
	 */
	private boolean isAllow(String requestURI) {
		if (requestURI.endsWith("/")) {
			requestURI = requestURI.substring(0, requestURI.length() - 1);
		}
		// �����ַ�����β��url����ҪУ�飬��ֻ��һ���򵥹��ˣ�û����չ�ԣ���Ҫ�������
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
