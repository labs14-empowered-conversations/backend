package com.lambdaschool.tempEC.services;

        import com.lambdaschool.tempEC.models.User;

        import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUserById(long id);

    void delete(long id);

    User save(User user);

    User update(User user, long id);
}