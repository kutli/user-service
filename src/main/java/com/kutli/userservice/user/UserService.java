package com.kutli.userservice.user;

import com.kutli.userservice.user.model.PostUser;
import com.kutli.userservice.user.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User save(PostUser user);

    User addRole(Long userId, Long roleId);

    List<User> getAll();

    Page<User> getAllPageable(Pageable pageable);

    User getById(Long userId);
}
