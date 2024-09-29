package org.othertwink.JsonShop.model.service;

import org.othertwink.JsonShop.model.entity.User;

public interface UserService {
    User createUser(String name, String address, String email);
    User deleteUser(Long id);
    User updateUser(User user);

}
