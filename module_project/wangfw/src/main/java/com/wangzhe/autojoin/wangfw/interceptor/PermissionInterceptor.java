package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.Util;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * Ȩ��������,�����û��Ĳ�ָͬ������ִ�еķ�����ͬ
 * <!--struts ������ʵ��-->
   <package name="basePackage"  extends="struts-default">  
         <interceptors>    
         <!-- ����Ȩ�������� -->
             <interceptor name="permission" class="com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.interceptor.PermissionInterceptor">    
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

	/**����ÿһ��action����
	 * @see com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		System.out.println("����MyMethodInterceptor����Ȩ��������!!!!!!!!!!!!!");

		// ��ȡ��ǰaction����
		// final Class objClass=invocation.getAction().getClass();

		// ��ȡ��ǰ�û�session
		Map<String, Object> session = invocation.getInvocationContext().getSession();

		// ��session��ȡ�û�����Ϣ
		// User user = (User) session.get("login_");
		// String name=user.getUName();
		// �жϵ�ǰ�û�Ȩ�ޣ��Ƿ���Բ�����struts.xml���õķ����������Ȩ�޾�ͨ��
		//(Integer) ServletActionContext.getRequest().getSession().getAttribute("UAuth")==1
		 if (Util.isHavePermission()) {
			System.out.println("��Ȩ�ޣ���������ɹ�");
			// ��ʾͨ����֤������ִ�и�action
			return invocation.invoke();
		}
		// Object actionObj=objClass.newInstance();
		// objClass.getDeclaredField("opMsg").set(actionObj,
		// "�Բ�������Ȩ�޲������޷����иò�����");
		// objClass.getDeclaredMethod("setOpMsg",
		// String.class).invoke(actionObj, "�Բ�������Ȩ�޲������޷����иò�����");
		invocation.getInvocationContext().put("msg", "�Բ�������Ȩ�޲������޷����иò�����");
		session.put("msg", "�Բ�������Ȩ�޲������޷����иò�����");
		System.out.println("Ȩ�޲������ܾ�");
		return "error";
	}

}
