package com.mulcam.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mulcam.demo.entiry.User;

@Mapper
public interface UserDao {
	
	// list생성
	@Select("select * from users")
	List<User> getList();

	// 특정 유저 정보 불러오기
	@Select("select * from users where uid=#{uid}")
	User get(String uid);
	
	@Insert("insert into users values(#{uid}, #{pwd}, #{uname}, #{email} ,default, default)")
	void insert(User u);
}
