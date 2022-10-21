package com.kutli.userservice.user;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> create(@RequestBody User user) {
        user = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping(value = "/{userId}/role/{roleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> addRole(@PathVariable(value = "userId") Long userId,
                                        @PathVariable(value = "roleId") Long roleId) {
        final User user = userService.addRole(userId, roleId);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAll() {
        final List<User> users = userService.getAll();
        return users.isEmpty() ?
            ResponseEntity.noContent().build() :
                ResponseEntity.ok(users);
    }

    @GetMapping(value = "/pageable")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<User>> getAllPageable(Pageable pageable) {
        final Page<User> users = userService.getAllPageable(pageable);
        return users.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> findById(@PathVariable(value = "userId") Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }
}
