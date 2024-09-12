package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	//private ������
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			//resources�� �����͸� Class -> ClassLoader(Ŭ������ �ε����ִ� �߻�Class) -> inputStream���� �޾Ƽ� ����.
			in = getClass().getClassLoader().getResourceAsStream(resources);
			
			//line���� �о '='�� �������� K, V�� properties�� �־���.
			properties = new Properties();
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){
		//�����ڰ� private. resources�� �־ �ν��Ͻ� �����ϴ� method.
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	public Action getAction(String path){
		//Action -> ��� Action Ŭ������ ���� Ŭ����
		//key�� �־ value�� �ޱ�
		Action action = map.get(path);
		
		//������ ������ش�
		if(action == null){
			//properties�� ����ִ� value ����
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				//class�� ����ϱ�����...
				Class c = Class.forName(className);
				//���� ������ �𸣴ϱ�...
				Object obj = c.newInstance();
				// DataType üũ
				if(obj instanceof Action){
					//map�� K, V ����
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}