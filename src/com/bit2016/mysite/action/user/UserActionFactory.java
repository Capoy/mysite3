package com.bit2016.mysite.action.user;

import com.bit2016.mysite.action.main.MainAction;
import com.bit2016.web.Action;
import com.bit2016.web.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if( "joinform".equals( actionName ) ) {
			action = new JoinFormAction();
		} else if( "join".equals( actionName ) ){
			action = new JoinAction();
		} else if( "joinsuccess".equals( actionName ) ){
			action = new JoinSuccessAction();
		} else if( "loginform".equals( actionName ) ){
			action = new LoginFormAction();
		} else if( "login".equals( actionName ) ){
			action = new LoginAction();
		} else if( "logout".equals( actionName ) ){
			action = new LogoutAction();
		} else if( "modifyform".equals( actionName ) ){
			action = new ModifyFormAction();
		} else if( "modify".equals( actionName ) ){
			action = new ModifyAction();
		} else if( "chkemail".equals( actionName ) ){
			action = new APICheckEmailAction();
		} else {
			action = new MainAction();
		}
		
		return action;
	}

}
