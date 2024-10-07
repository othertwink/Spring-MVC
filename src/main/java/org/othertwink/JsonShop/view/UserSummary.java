package org.othertwink.JsonShop.view;

import org.othertwink.JsonShop.model.entity.User;
import org.othertwink.JsonShop.model.repository.UserDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSummary {

    private final UserDAO userDAO;

    public UserSummary(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getUserSummary() {
        List<User> users = userDAO.findAll();
        return users;
    }
}
