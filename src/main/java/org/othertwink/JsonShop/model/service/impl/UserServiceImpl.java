package org.othertwink.JsonShop.model.service.impl;

import org.othertwink.JsonShop.model.entity.User;
import org.othertwink.JsonShop.model.repository.UserDAO;
import org.othertwink.JsonShop.model.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl (UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional // по умолчанию read commited. допустимо ли грязное чтение?
    public User createUser(String name, String email, String address) {
        try {
            User newUser = User.builder()
                    .name(name)
                    .email(email)
                    .address(address)
                    .build();
            return userDAO.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User with this email already exists", e);
        }
    }

    @Transactional
    public User deleteUser(Long id) {
        Optional<User> existingUser = userDAO.findById(id);

        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        userDAO.deleteById(id);
        return existingUser.get();
    }

    @Transactional
    public User updateUser(Long id, String name, String email) {
        Optional<User> existingUser = userDAO.findById(id);

        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }

        User userToUpdate = existingUser.get();

        if (!userToUpdate.getEmail().equals(email)) {
            Optional<User> emailOwner = userDAO.findByEmail(email);
            if (emailOwner.isPresent() && !emailOwner.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email " + email + " is already taken by another user");
            }
        }

        userToUpdate.setName(name);
        userToUpdate.setEmail(email);

        return userDAO.save(userToUpdate);
    }

}
