package com.mulcam.demo.service;

import java.util.List;

import com.mulcam.demo.entiry.User;

public interface UserService {

	List<User> getList();

	User get(String uid);
	
	void register(User u);

}
