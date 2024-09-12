package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources"); //메타데이터에서 initParam 사용
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		//URI 파싱
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		//contextPath 뒤를 사용
		String path = url.substring(contextPath.length());
		System.out.println(path);
		
		try{
			//String, Action이 들어있는 map에서 Value 추출
			Action action = mapper.getAction(path);
			//Application objectScope
			action.setServletContext(getServletContext());
			
			//결과 페이지로
			String resultPage=action.execute(request, response);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			//navigation
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}