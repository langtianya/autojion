package com.wangzhe.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.wangzhe.common.Util;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 权限拦截器,根据用户的不同指定其能执行的方法不同
 * <!--struts 拦截器实现-->
   <package name="basePackage"  extends="struts-default">  
         <interceptors>    
         <!-- 配置权限拦截器 -->
             <interceptor name="permission" class="com.wangzhe.interceptor.PermissionInterceptor">    
                <param name="includeMethods">deleteUser,deleteAccount,deleteRecord</param>  
                <param name="excludeMethods">query*</param>    
             </interceptor>
             
			<interceptor-stack name="permissionStack">
				<interceptor-ref name="permission"/>
                <interceptor-ref name="defaultStack"/>
            </interceptor-stack>   
        </interceptors>   
        <default-interceptor-ref name="permissionStack"></default-interceptor-ref>
   </package>
 * @author ocq
 *
 */
public class PermissionInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -5360035516489852006L;

	/**拦截每一个action请求
	 * @see com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		System.out.println("进入MyMethodInterceptor方法权限拦截器!!!!!!!!!!!!!");

		// 获取当前action的类
		// final Class objClass=invocation.getAction().getClass();

		// 获取当前用户session
		Map<String, Object> session = invocation.getInvocationContext().getSession();

		// 从session获取用户等信息
		// User user = (User) session.get("login_");
		// String name=user.getUName();
		// 判断当前用户权限，是否可以操作，struts.xml配置的方法，如果有权限就通过
		//(Integer) ServletActionContext.getRequest().getSession().getAttribute("UAuth")==1
		 if (Util.isHavePermission()) {
			System.out.println("有权限，并且请求成功");
			// 表示通过验证，可以执行该action
			return invocation.invoke();
		}
		// Object actionObj=objClass.newInstance();
		// objClass.getDeclaredField("opMsg").set(actionObj,
		// "对不起，您的权限不够，无法进行该操作！");
		// objClass.getDeclaredMethod("setOpMsg",
		// String.class).invoke(actionObj, "对不起，您的权限不够，无法进行该操作！");
		invocation.getInvocationContext().put("msg", "对不起，您的权限不够，无法进行该操作！");
		session.put("msg", "对不起，您的权限不够，无法进行该操作！");
		System.out.println("权限不够被拒绝");
		return "error";
	}

}
