package com.bit2016.mysite.action.guestbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.mysite.dao.GuestbookDao;
import com.bit2016.mysite.vo.GuestbookVo;
import com.bit2016.web.Action;

import net.sf.json.JSONObject;

public class AjaxListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sPage = request.getParameter( "p" );
		if( "".equals(sPage) ) {
			sPage = "1";
		}
		int page = Integer.parseInt( sPage );

		GuestbookDao dao = new GuestbookDao();
		List<GuestbookVo> list = dao.getList( page );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", list );
		
		response.setContentType( "application/json; charset=utf-8" );
		
		JSONObject jsonObject = JSONObject.fromObject( map );
		response.getWriter().println( jsonObject.toString() );
	}

}
