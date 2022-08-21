package io.github.cocodx.service.impl;

import io.github.cocodx.dao.UserDao;
import io.github.cocodx.entity.User;
import io.github.cocodx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author amazfit
 * @date 2022-08-22 上午6:19
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        return userDao.login(user);
    }
}
