package org.othertwink.JsonShop.view;

import org.othertwink.JsonShop.model.entity.User;
import org.othertwink.JsonShop.model.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSummary {

    @Autowired
    private UserDAO userDAO;

    public List<User> getUserSummary() {
        List<User> users = userDAO.findAll();
        return users;
    }
}
