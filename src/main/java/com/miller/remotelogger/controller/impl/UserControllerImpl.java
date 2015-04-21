package com.miller.remotelogger.controller.impl;

import org.springframework.web.bind.annotation.RestController;

import com.miller.remotelogger.controller.UserController;
import com.miller.remotelogger.domain.User;
import com.miller.remotelogger.exceptions.DuplicateUserException;

@RestController
public class UserControllerImpl implements UserController {

	@Override
	public User addUser(User user) throws DuplicateUserException {
		// TODO Auto-generated method stub
		return null;
	}

}
