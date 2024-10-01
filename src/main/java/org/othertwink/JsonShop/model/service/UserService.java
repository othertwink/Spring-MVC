package org.othertwink.JsonShop.model.service;

import org.othertwink.JsonShop.model.entity.User;

public interface UserService {
    User createUser(String name, String address, String email);
    User deleteUser(Long userId);
    User updateUser(Long id, String name, String email);

}
