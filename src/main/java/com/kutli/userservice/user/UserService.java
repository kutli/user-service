package com.kutli.userservice.user;

import java.util.List;

public interface UserService {

    User save(User user);

    User addRole(Long userId, Long roleId);

    List<User> getAll();
}
