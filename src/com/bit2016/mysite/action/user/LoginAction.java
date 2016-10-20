package com.bit2016.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit2016.mysite.dao.UserDao;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter( "email" );
		String password = request.getParameter( "password" );
		
		UserDao dao = new UserDao();
		UserVo vo = dao.get(email, password);
		
		// 로그인 실패(passowrd 또는 email 이 틀림)
		if( vo == null ) {
			// 실패 처리는 리다이렉션
			// 또는 포워딩처리도 가능
			WebUtil.redirect(
				request, 
				response, 
				"/mysite3/user?a=loginform&result=fail");
			return; // 주의: redirect를 한 후 다음 코드가 실행 되지 않도록 함수 종료
		}
		
		// 로그인 성공 -> 인증처리
		// false or 빈 파라미터 -> jsessionid 와 연결된 session 객체가 없으면 null 리턴
		// true -> jsessionid 와 연결된 session 객체가 없으면 만들어서 리턴
		HttpSession session = request.getSession( true );
		session.setAttribute( "authUser", vo );
		
		WebUtil.redirect(request, response, "/mysite3/main" );
	}

}
