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

public class AjaxDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter( "no" );
		String password = request.getParameter( "password" );
		
		GuestbookVo vo = new GuestbookVo();
		vo.setNo( Long.parseLong( no ) );
		vo.setPassword(password);
		
		GuestbookDao dao = new GuestbookDao();
		boolean result = dao.delete(vo);
		
		//response
		response.setContentType( "application/json; charset=utf-8" );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", result ? no : -1 );
		
		response.getWriter().println( JSONObject.fromObject(map).toString() );
		
	}

}
