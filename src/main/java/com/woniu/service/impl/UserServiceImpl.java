package com.woniu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woniu.dao.UserMapper;
import com.woniu.entity.User;
import com.woniu.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper mapper;
	
	@Override
	public void save(User user) {
		mapper.insertSelective(user);
	}

}
