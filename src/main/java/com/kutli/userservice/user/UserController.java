package com.kutli.userservice.user;


import com.kutli.userservice.user.model.PostUser;
import com.kutli.userservice.user.model.User;
import com.kutli.userservice.util.MultiValueSearch;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final MultiValueSearch multiValueSearch;

    @PostConstruct
    public void UserController() {
        multiValueSearch.setFilterValidValues("name", "username");
    }

    @PostMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> create(@RequestBody PostUser postUser) {
        User user = userService.save(postUser);
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
    public ResponseEntity<Page<User>> getAllPageable(Pageable pageable, @RequestParam Map<String, String> filters) {
        multiValueSearch.filterValuesValidation(filters);
        final Page<User> users = userService.getAllPageable(pageable, filters);
        return users.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<User> findById(@PathVariable(value = "userId") Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }
}
