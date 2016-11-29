/**
 * 
 */
package com.dashboard.poc.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dashboard.poc.dao.ILoginDao;
import com.dashboard.poc.exception.CustomException;
import com.dashboard.poc.mapper.LoginMapper;
import com.dashboard.poc.model.User;

/**
 * @author Pratyus
 *
 * 26-Aug-2016
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = CustomException.class)
public class LoginDaoImpl implements ILoginDao{

	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	User user;
	
	static Logger logger = Logger.getLogger(LoginDaoImpl.class.getName());
	
	/* (non-Javadoc)
	 * @see com.dashboard.poc.dao.ILoginDao#validateUser(com.dashboard.poc.model.User)
	 */
	@Override
	public User validateUser(User loginuser) throws CustomException {
		// TODO Auto-generated method stub
		try{
			user = loginMapper.validateUser(loginuser);
		}catch(Exception e){
			logger.error("Error in User Login");
			e.printStackTrace();
			throw new CustomException(e, "Error in User Login");
		}
		return user;
	}

	@Override
	public List<User> loadOperatorByUserName(String username) throws CustomException {
		// TODO Auto-generated method stub
		List<User> userList = null;
		try{
			//userList = loginMapper.loadOperatorByUserName(username);
		}catch(Exception e){
			logger.error("Error in User Login");
			e.printStackTrace();
			throw new CustomException(e, "Error in Loading user");
		}
		return userList;
	}
	
	/*
	 * public User validateUser(User loginuser) throws CustomException {
		// TODO Auto-generated method stub
		SqlSession session = sqlSessionFactory.openSession();
		List<User> listUser = null;
		try{
			listUser = session.selectList("user.validateUser");
			if(listUser != null && listUser.size() > 0){
				return listUser.get(0);
			}else{
				logger.error("Invalid User Credentials");
				throw new CustomException("Invalid User Credentials");
			}
			//return loginMapper.validateUser(loginuser);
		}catch(Exception e){
			logger.error("Error in User Login");
			e.printStackTrace();
			throw new CustomException(e, "Error in User Login");
		}
	}
	 */

}
