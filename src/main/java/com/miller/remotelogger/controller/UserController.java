package com.miller.remotelogger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.miller.remotelogger.domain.User;
import com.miller.remotelogger.exceptions.DuplicateUserException;

@RestController
@RequestMapping(value="v1/users")
public interface UserController {

	@PreAuthorize("hasIpAddress('127.0.0.1')")
	@JsonView(User.ResponseView.class)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) throws DuplicateUserException;
	
}
