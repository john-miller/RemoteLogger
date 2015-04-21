package com.miller.remotelogger.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.miller.remotelogger.Application;
import com.miller.remotelogger.controller.impl.UserControllerImpl;
import com.miller.remotelogger.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class, User.class})
public class UserControllerTests {
	
	@Test
	public void controllerLoads()
	{
		UserController controller = new UserControllerImpl();
		assertNotNull(controller);
	}
	
}
