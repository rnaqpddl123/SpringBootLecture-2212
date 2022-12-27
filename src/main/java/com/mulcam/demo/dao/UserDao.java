package com.mulcam.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	
	@Update("UPDATE users SET uname=#{uname}, email=#{email} where uid=#{uid}")
	void update(User u);
	
//	@Delete("DELETE from users where uid=#{uid}")
//	void delete(String uid);
	
	@Update("UPDATE users SET isDeleted=1 where uid=#{uid}")
	void delete(String uid);
}
