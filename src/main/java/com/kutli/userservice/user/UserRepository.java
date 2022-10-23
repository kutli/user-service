package com.kutli.userservice.user;

import com.kutli.userservice.user.model.PostUser;
import com.kutli.userservice.user.model.User;
import javax.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(PostUser postUser);

    Page<User> findAll(@Nullable Specification<User> spec, Pageable pageable);
}
