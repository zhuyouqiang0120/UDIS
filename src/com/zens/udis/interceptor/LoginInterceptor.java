package com.zens.udis.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.zens.udis.entity.User;

public class LoginInterceptor implements Interceptor{

	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		String username = controller.getPara("username");
		String password = controller.getPara("password");
	System.out.println(username);	
		User user = new User();
		if( user.isExistUser(username, password) ){
			controller.setSessionAttr("loginUser", username);
			ai.invoke();
		}else{
			controller.setAttr("loginErr", "用户名或密码错误！");
			controller.renderJsp("login.jsp");
		}
	}
}
