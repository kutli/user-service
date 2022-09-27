package com.kutli.userservice.role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long roleId);
}
