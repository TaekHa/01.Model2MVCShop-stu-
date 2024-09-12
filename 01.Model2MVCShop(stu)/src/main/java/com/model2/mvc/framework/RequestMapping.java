package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	//private 생성자
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			//resources의 데이터를 Class -> ClassLoader(클래스를 로드해주는 추상Class) -> inputStream으로 받아서 저장.
			in = getClass().getClassLoader().getResourceAsStream(resources);
			
			//line으로 읽어서 '='를 기준으로 K, V를 properties에 넣어줌.
			properties = new Properties();
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){
		//생성자가 private. resources를 넣어서 인스턴스 생성하는 method.
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	public Action getAction(String path){
		//Action -> 모든 Action 클래스의 상위 클래스
		//key를 넣어서 value를 받기
		Action action = map.get(path);
		
		//없으면 만들어준다
		if(action == null){
			//properties에 들어있는 value 추출
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				//class를 사용하기위해...
				Class c = Class.forName(className);
				//뭐가 들어올지 모르니까...
				Object obj = c.newInstance();
				// DataType 체크
				if(obj instanceof Action){
					//map에 K, V 설정
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}