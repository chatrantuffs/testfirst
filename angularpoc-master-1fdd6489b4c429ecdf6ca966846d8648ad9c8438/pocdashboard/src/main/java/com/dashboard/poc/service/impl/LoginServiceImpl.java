/**
 * 
 */
package com.dashboard.poc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.poc.dao.ILoginDao;
import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.facade.UserFacade;
import com.dashboard.poc.model.User;
import com.dashboard.poc.service.ILoginService;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Service
public class LoginServiceImpl implements ILoginService{

	@Autowired
	private ILoginDao loginDao;

	@Autowired
	UserFacade userFacade;
	
	static Logger logger = Logger.getLogger(LoginServiceImpl.class.getName());

	@Override
	public User validateUser(final String username, final String password) throws CustomException{
		User user = loginDao.validateUser(userFacade.updateUser(username, password));
		return user != null ? userFacade.validUser(user) : null;
	}

	@Override
	public List<User> loadOperatorByUserName(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
