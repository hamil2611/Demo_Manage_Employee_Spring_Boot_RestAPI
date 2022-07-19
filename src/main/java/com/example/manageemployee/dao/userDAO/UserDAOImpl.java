package com.example.manageemployee.dao.userDAO;

import com.example.manageemployee.entity.User;
import com.example.manageemployee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {
    @Autowired
    UserRepository userRepository;

    @Override
    public void newUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void editUser(int id) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public List<User> getUser(String username) {
        List<User> listUser = userRepository.findAllByUsername(username);
        listUser.forEach(i-> System.out.println(i.getFullname()));
        return listUser;
    }
}
