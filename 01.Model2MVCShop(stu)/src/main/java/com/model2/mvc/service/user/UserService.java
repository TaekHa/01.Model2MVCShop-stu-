package com.model2.mvc.service.user;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.user.vo.User;


public interface UserService {
	
	public void addUser(User userVO) throws Exception;
	
	public User loginUser(User userVO) throws Exception;
	
	public User getUser(String userId) throws Exception;
	
	public HashMap<String, Object> getUserList(SearchVO searchVO) throws Exception;
	
	public void updateUser(User userVO) throws Exception;
	
	public boolean checkDuplication(String userId) throws Exception;
	
}