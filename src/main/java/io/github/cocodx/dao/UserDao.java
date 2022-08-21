package io.github.cocodx.dao;

import io.github.cocodx.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User login(User user);
}
