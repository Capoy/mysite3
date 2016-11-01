package com.bit2016.mysite.action.guestbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.mysite.dao.GuestbookDao;
import com.bit2016.mysite.vo.GuestbookVo;
import com.bit2016.web.Action;

import net.sf.json.JSONObject;

public class AjaxAddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter( "name" );
		String password = request.getParameter( "password" );
		String content = request.getParameter( "content" );
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setContent(content);
		vo.setPassword(password);
		
		GuestbookDao dao = new GuestbookDao();
		Long no = dao.insert(vo);
		GuestbookVo guestbookVo = dao.get(no);
		
		//response
		response.setContentType( "application/json; charset=utf-8" );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", guestbookVo );
		
		response.getWriter().println( JSONObject.fromObject(map).toString() );
	}

}
