package com.kutli.userservice.user;

import com.kutli.userservice.user.model.PostUser;
import com.kutli.userservice.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(PostUser postUser);
}
