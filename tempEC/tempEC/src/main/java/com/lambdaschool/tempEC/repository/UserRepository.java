package com.lambdaschool.tempEC.repository;

import com.lambdaschool.tempEC.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
