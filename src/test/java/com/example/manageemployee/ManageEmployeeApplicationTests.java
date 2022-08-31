package com.example.manageemployee;

import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.repository.UserRepository;
import com.example.manageemployee.service.userservice.UserService;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class ManageEmployeeApplicationTests {
    @Mock
    UserService userService;
    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    UserRepository userRepository;
    @Test
    void contextLoads() throws Exception {
        List<User> userList = new ArrayList<>();

        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_EMPLOYEE");

        User user1 = new User();
        user1.setId(1);
        user1.setFullname("Dinh An");
        user1.setRole(role);
        userList.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setFullname("Dinh An");
        user2.setRole(role);
        userList.add(user2);

        User user3 = new User();
        user3.setId(3);
        user3.setFullname("Dinh An");
        user3.setRole(role);
        userList.add(user3);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userRepository.findAllEmployee();
        Assert.assertEquals(2,userServiceImpl.findAllEmployee().size());

    }
    
}
