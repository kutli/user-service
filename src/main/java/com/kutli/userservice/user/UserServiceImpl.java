package com.kutli.userservice.user;

import com.kutli.userservice.customException.CustomException;
import com.kutli.userservice.customException.ErrorMessage;
import com.kutli.userservice.role.Role;
import com.kutli.userservice.role.RoleService;
import com.kutli.userservice.user.model.PostUser;
import com.kutli.userservice.user.model.User;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User save(PostUser postUser) {
        postUser.setPassword(passwordEncoder.encode(postUser.getPassword()));
        return userRepository.save(postUser);
    }

    @Override
    public User addRole(Long userId, Long roleId) {
        final Role role = roleService.findById(roleId);
        final User user = this.getById(userId);
        user.getRoles().add(role);
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Long userId) {
        log.info(String.valueOf(userId));
        return userRepository.findById(userId)
                .orElseThrow(() -> CustomException.builder()
                        .errorMessage(ErrorMessage.USER_NOT_FOUND)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .field("userId")
                        .value(String.valueOf(userId))
                        .build());
    }
}
