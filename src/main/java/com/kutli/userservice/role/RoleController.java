package com.kutli.userservice.role;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleService roleService;

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<Role>> getAll() {
        final List<Role> role = roleService.findAll();
        return role.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(role);
    }
}
