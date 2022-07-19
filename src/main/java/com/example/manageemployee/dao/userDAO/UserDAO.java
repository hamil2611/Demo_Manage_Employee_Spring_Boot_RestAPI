package com.example.manageemployee.dao.userDAO;

import com.example.manageemployee.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDAO {
    public void newUser(User user);
    public void editUser(int id);
    public void deleteUser(int id);
    public List<User> getUser(String username);
}
